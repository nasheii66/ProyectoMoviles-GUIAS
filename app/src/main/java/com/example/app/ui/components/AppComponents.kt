package com.example.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.app.model.ItemModel
import com.example.app.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Elemento auxiliar para representar cada opción de menú en BottomBar y Drawer.
 */
data class MenuItem(
    val titulo: String,
    val screen: Screen,
    val icono: ImageVector
)

val menuItemsPrincipales = listOf(
    MenuItem("Inicio", Screen.Home, Icons.Default.Home),
    MenuItem("Perfil", Screen.Profile, Icons.Default.Person),
    MenuItem("Ajustes", Screen.Settings, Icons.Default.Settings)
)

/**
 * Barra Superior (TopAppBar) según la Sección 4 (Guía 8).
 * Incluye título y navigationIcon para desplegar el menú lateral o regresar.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    titulo: String,
    mostrarBotonAtras: Boolean = false,
    onMenuClick: () -> Unit,
    onBackClick: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        },
        navigationIcon = {
            if (mostrarBotonAtras) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Regresar"
                    )
                }
            } else {
                IconButton(onClick = onMenuClick) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Abrir menú lateral"
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    )
}

/**
 * Barra de Navegación Inferior (NavigationBar + NavigationBarItem) según la Sección 4 (Guía 8).
 * Usa íconos dinámicos (Icons.Default.Home, Icons.Default.Person, Icons.Default.Settings)
 * y selección reactiva por ruta actual.
 */
@Composable
fun AppBottomNavigationBar(
    rutaActual: String?,
    onNavigate: (Screen) -> Unit
) {
    NavigationBar {
        menuItemsPrincipales.forEach { item ->
            val seleccionado = rutaActual == item.screen.route
            NavigationBarItem(
                selected = seleccionado,
                onClick = { onNavigate(item.screen) },
                icon = {
                    Icon(
                        imageVector = item.icono,
                        contentDescription = item.titulo
                    )
                },
                label = { Text(text = item.titulo) }
            )
        }
    }
}

/**
 * Contenido del Menú Lateral Deslizable (ModalDrawerSheet + NavigationDrawerItem)
 * controlado con rememberDrawerState y corrutinas (Sección 4 - Guía 8).
 */
@Composable
fun AppDrawerSheetContent(
    rutaActual: String?,
    drawerState: DrawerState,
    scope: CoroutineScope,
    onNavigate: (Screen) -> Unit
) {
    ModalDrawerSheet {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "AppProyecto_GrupoX",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Menú de Navegación MVVM",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        HorizontalDivider()
        Spacer(modifier = Modifier.height(12.dp))

        menuItemsPrincipales.forEach { item ->
            val seleccionado = rutaActual == item.screen.route
            NavigationDrawerItem(
                label = { Text(text = item.titulo) },
                selected = seleccionado,
                onClick = {
                    scope.launch { drawerState.close() }
                    onNavigate(item.screen)
                },
                icon = {
                    Icon(
                        imageVector = item.icono,
                        contentDescription = item.titulo
                    )
                },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )
        }
    }
}

/**
 * Tarjeta reutilizable para mostrar un ItemModel y navegar a su pantalla de detalle.
 */
@Composable
fun ItemCardReusable(
    item: ItemModel,
    onVerDetalleClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.titulo,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = item.categoria,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            Text(
                text = item.subtitulo,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { onVerDetalleClick(item.id) },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = "Ver Detalle")
            }
        }
    }
}
