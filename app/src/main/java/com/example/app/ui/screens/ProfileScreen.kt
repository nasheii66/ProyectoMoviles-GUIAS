package com.example.app.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
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
import com.example.app.model.UserProfile
import com.example.app.navigation.Screen
import com.example.app.repository.SampleRepository
import com.example.app.ui.theme.AppProyecto_GrupoXTheme
import com.example.app.viewmodel.MainViewModel

/**
 * Pantalla de Perfil de Usuario (ProfileScreen - Ruta Screen.Profile).
 * Aplica los layouts y elementos visuales de la Sección 4.
 */
@Composable
fun ProfileScreen(
    viewModel: MainViewModel = viewModel()
) {
    val perfil by viewModel.userProfile.collectAsState()

    ProfileScreenContent(
        perfil = perfil,
        onNavigateHome = { viewModel.navigateTo(Screen.Home) },
        onNavigateSettings = { viewModel.navigateTo(Screen.Settings) },
        onNavigateBack = { viewModel.navigateBack() }
    )
}

@Composable
fun ProfileScreenContent(
    perfil: UserProfile,
    onNavigateHome: () -> Unit = {},
    onNavigateSettings: () -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.recurso_imagen),
            contentDescription = "Avatar de perfil de usuario",
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = perfil.nombre,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Rol: ${perfil.rol}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Correo: ${perfil.correo}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Equipo: ${perfil.grupo}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
        ) {
            Button(onClick = onNavigateSettings) {
                Text(text = "Ir a Ajustes")
            }
            OutlinedButton(onClick = onNavigateHome) {
                Text(text = "Volver a Inicio")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(onClick = onNavigateBack) {
            Text(text = "Regresar (PopBackStack)")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen() {
    AppProyecto_GrupoXTheme {
        ProfileScreenContent(
            perfil = SampleRepository().obtenerPerfilUsuario()
        )
    }
}
