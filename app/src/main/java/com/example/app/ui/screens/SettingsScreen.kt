package com.example.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app.navigation.Screen
import com.example.app.ui.theme.AppProyecto_GrupoXTheme
import com.example.app.viewmodel.MainViewModel

/**
 * Pantalla de Configuración (SettingsScreen - Ruta Screen.Settings).
 * Permite modificar estados reactivos en el ViewModel y navegar entre pantallas.
 */
@Composable
fun SettingsScreen(
    viewModel: MainViewModel = viewModel()
) {
    val notificacionesActivas by viewModel.notificacionesActivas.collectAsState()
    val modoCompactoForzado by viewModel.modoCompactoForzado.collectAsState()

    SettingsScreenContent(
        notificacionesActivas = notificacionesActivas,
        modoCompactoForzado = modoCompactoForzado,
        onToggleNotificaciones = { viewModel.alternarNotificaciones(it) },
        onToggleModoCompacto = { viewModel.alternarModoCompacto(it) },
        onNavigateHome = { viewModel.navigateTo(Screen.Home) },
        onNavigateBack = { viewModel.navigateBack() }
    )
}

@Composable
fun SettingsScreenContent(
    notificacionesActivas: Boolean = true,
    modoCompactoForzado: Boolean = false,
    onToggleNotificaciones: (Boolean) -> Unit = {},
    onToggleModoCompacto: (Boolean) -> Unit = {},
    onNavigateHome: () -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Configuración de la Aplicación",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Notificaciones de Eventos",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Habilitar avisos de navegación y estado",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
                Switch(
                    checked = notificacionesActivas,
                    onCheckedChange = onToggleNotificaciones
                )
            }
        }

        Card(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Forzar Vista Compacta",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Ignorar WindowSizeClass y usar HomeScreenCompacta",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
                Switch(
                    checked = modoCompactoForzado,
                    onCheckedChange = onToggleModoCompacto
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(onClick = onNavigateHome) {
                Text(text = "Ir a Inicio")
            }
            OutlinedButton(onClick = onNavigateBack) {
                Text(text = "Regresar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsScreen() {
    AppProyecto_GrupoXTheme {
        SettingsScreenContent()
    }
}
