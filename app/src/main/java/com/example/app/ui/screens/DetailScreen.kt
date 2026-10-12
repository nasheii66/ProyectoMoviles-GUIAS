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
import com.example.app.ui.theme.AppProyecto_GrupoXTheme
import com.example.app.viewmodel.MainViewModel

/**
 * Pantalla de Detalle Parametrizada (DetailScreen - Ruta Screen.Detail(itemId)).
 * Recibe el parámetro `itemId` desde el NavHost y obtiene la entidad del ViewModel.
 */
@Composable
fun DetailScreen(
    itemId: String,
    viewModel: MainViewModel = viewModel()
) {
    val item = viewModel.obtenerItemPorId(itemId)

    DetailScreenContent(
        item = item,
        onNavigateBack = { viewModel.navigateBack() },
        onNavigateHome = {
            viewModel.navigateTo(
                screen = Screen.Home,
                popUpToRoute = Screen.Home,
                inclusive = true
            )
        }
    )
}

@Composable
fun DetailScreenContent(
    item: ItemModel,
    onNavigateBack: () -> Unit = {},
    onNavigateHome: () -> Unit = {}
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
            contentDescription = "Imagen de detalle para ${item.titulo}",
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = item.titulo,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "ID recibido por ruta: ${item.id} | Categoría: ${item.categoria}",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = item.subtitulo,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = item.descripcion,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(onClick = onNavigateBack) {
                Text(text = "Volver Atrás")
            }
            OutlinedButton(onClick = onNavigateHome) {
                Text(text = "Ir a Inicio")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDetailScreen() {
    AppProyecto_GrupoXTheme {
        DetailScreenContent(
            item = SampleRepository().obtenerItemPorId("guia_10")
        )
    }
}
