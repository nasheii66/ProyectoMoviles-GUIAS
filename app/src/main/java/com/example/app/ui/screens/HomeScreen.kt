package com.example.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app.R
import com.example.app.model.ItemModel
import com.example.app.navigation.Screen
import com.example.app.repository.SampleRepository
import com.example.app.ui.components.ItemCardReusable
import com.example.app.ui.theme.AppProyecto_GrupoXTheme
import com.example.app.ui.utils.obtenerWindowSizeClass
import com.example.app.viewmodel.MainViewModel

/**
 * Selector Dinámico con when (Sección 5 - Guía 9).
 * Evalúa el WindowSizeClass del dispositivo y renderiza la variante adecuada
 * (Compacta, Mediana o Expandida).
 */
@Composable
fun HomeScreenAdaptable(
    viewModel: MainViewModel = viewModel()
) {
    val windowSizeClass = obtenerWindowSizeClass()
    val items by viewModel.items.collectAsState()
    val modoCompactoForzado by viewModel.modoCompactoForzado.collectAsState()

    if (modoCompactoForzado) {
        HomeScreenCompacta(
            items = items,
            onNavigateTo = { screen -> viewModel.navigateTo(screen) }
        )
        return
    }

    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> HomeScreenCompacta(
            items = items,
            onNavigateTo = { screen -> viewModel.navigateTo(screen) }
        )
        WindowWidthSizeClass.Medium -> HomeScreenMediana(
            items = items,
            onNavigateTo = { screen -> viewModel.navigateTo(screen) }
        )
        WindowWidthSizeClass.Expanded -> HomeScreenExpandida(
            items = items,
            onNavigateTo = { screen -> viewModel.navigateTo(screen) }
        )
        else -> HomeScreenCompacta(
            items = items,
            onNavigateTo = { screen -> viewModel.navigateTo(screen) }
        )
    }
}

/**
 * Variante para pantallas verticales estándar (< 600dp de ancho) - Guía 9.
 * Implementa todos los elementos visuales obligatorios de la Sección 4:
 * - Column con Modifier.fillMaxSize() y alineaciones explícitas
 * - Text con MaterialTheme.typography.titleLarge y colorScheme.primary
 * - Image con painterResource(id = R.drawable.recurso_imagen), Modifier.fillMaxWidth().height(150.dp), ContentScale.Fit
 * - Spacer con Modifier.height(16.dp)
 * - Button con llamadas de acción a eventos del ViewModel
 */
@Composable
fun HomeScreenCompacta(
    items: List<ItemModel> = SampleRepository().obtenerItems(),
    onNavigateTo: (Screen) -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Vista Compacta (< 600dp)",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(id = R.drawable.recurso_imagen),
                    contentDescription = "Ilustración principal del proyecto",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { onNavigateTo(Screen.Profile) }
                    ) {
                        Text(text = "Ir a Perfil")
                    }
                    OutlinedButton(
                        onClick = { onNavigateTo(Screen.Settings) }
                    ) {
                        Text(text = "Ir a Ajustes")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        items(items, key = { it.id }) { item ->
            ItemCardReusable(
                item = item,
                onVerDetalleClick = { itemId ->
                    onNavigateTo(Screen.Detail(itemId = itemId))
                }
            )
        }
    }
}

/**
 * Variante para pantallas intermedias o plegables (600dp a 840dp de ancho) - Guía 9.
 * Organiza el encabezado a dos columnas y muestra las tarjetas en una cuadrícula de 2 columnas.
 */
@Composable
fun HomeScreenMediana(
    items: List<ItemModel> = SampleRepository().obtenerItems(),
    onNavigateTo: (Screen) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.recurso_imagen),
                    contentDescription = "Ilustración modo mediano",
                    modifier = Modifier
                        .weight(1f)
                        .height(150.dp),
                    contentScale = ContentScale.Fit
                )
                Column(
                    modifier = Modifier.weight(1.2f),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Vista Mediana (600dp - 840dp)",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Diseño optimizado para pantallas intermedias y dispositivos plegables.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(onClick = { onNavigateTo(Screen.Profile) }) {
                            Text(text = "Ver Perfil")
                        }
                        OutlinedButton(onClick = { onNavigateTo(Screen.Settings) }) {
                            Text(text = "Ajustes")
                        }
                    }
                }
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items, key = { it.id }) { item ->
                ItemCardReusable(
                    item = item,
                    onVerDetalleClick = { itemId ->
                        onNavigateTo(Screen.Detail(itemId = itemId))
                    }
                )
            }
        }
    }
}

/**
 * Variante para pantallas grandes o formato horizontal (> 840dp de ancho) - Guía 9.
 * Utiliza un diseño maestro-detalle en paralelo (Panel lateral fijo + Grid expandido).
 */
@Composable
fun HomeScreenExpandida(
    items: List<ItemModel> = SampleRepository().obtenerItems(),
    onNavigateTo: (Screen) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Panel izquierdo de resumen y acciones rápidas
        Card(
            modifier = Modifier
                .weight(0.38f)
                .fillMaxHeight(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Vista Expandida (> 840dp)",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(id = R.drawable.recurso_imagen),
                    contentDescription = "Ilustración modo expandido",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Distribución multipanel activa para tablets y pantallas horizontales amplias.",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = { onNavigateTo(Screen.Profile) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Abrir Perfil de Usuario")
                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(
                    onClick = { onNavigateTo(Screen.Settings) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Configuración del Sistema")
                }
            }
        }

        // Panel derecho con cuadrícula de módulos
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .weight(0.62f)
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items, key = { it.id }) { item ->
                ItemCardReusable(
                    item = item,
                    onVerDetalleClick = { itemId ->
                        onNavigateTo(Screen.Detail(itemId = itemId))
                    }
                )
            }
        }
    }
}

// =========================================================================
// Previsualizaciones Adaptables Específicas (Sección 4 y Sección 5 - Guía 9)
// =========================================================================

@Preview(name = "Compact", widthDp = 360, heightDp = 800, showBackground = true)
@Composable
fun PreviewHomeScreenCompacta() {
    AppProyecto_GrupoXTheme {
        HomeScreenCompacta()
    }
}

@Preview(name = "Medium", widthDp = 700, heightDp = 900, showBackground = true)
@Composable
fun PreviewHomeScreenMediana() {
    AppProyecto_GrupoXTheme {
        HomeScreenMediana()
    }
}

@Preview(name = "Expanded", widthDp = 1024, heightDp = 768, showBackground = true)
@Composable
fun PreviewHomeScreenExpandida() {
    AppProyecto_GrupoXTheme {
        HomeScreenExpandida()
    }
}
