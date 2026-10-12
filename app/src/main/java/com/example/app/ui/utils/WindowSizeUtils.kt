package com.example.app.ui.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

/**
 * Proveedor de compatibilidad para LocalActivity (como se referencia en la Guía 9)
 * permitiendo que `LocalActivity.current` funcione tanto en la Activity principal
 * como de forma segura en cualquier contexto.
 */
val LocalActivity = staticCompositionLocalOf<Activity?> { null }

/**
 * Función de extensión auxiliar para encontrar la Activity desde un Context en caso
 * de que LocalActivity.current no haya sido provisto explícitamente.
 */
private tailrec fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

/**
 * Utilidad Reutilizable (Sección 5 - WindowSizeUtils.kt & Guía 9).
 * Calcula la clase de tamaño de ventana (Compact, Medium, Expanded) para adaptar la UI.
 * Soporta tanto ejecución en dispositivo real (vía Activity) como @Preview en Android Studio.
 */
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun obtenerWindowSizeClass(): WindowSizeClass {
    val activity = LocalActivity.current ?: LocalContext.current.findActivity()
    if (activity != null) {
        return calculateWindowSizeClass(activity)
    }
    // Fallback seguro para @Preview en tiempo de diseño en Android Studio
    val configuration = LocalConfiguration.current
    val size = DpSize(configuration.screenWidthDp.dp, configuration.screenHeightDp.dp)
    return WindowSizeClass.calculateFromSize(size)
}
