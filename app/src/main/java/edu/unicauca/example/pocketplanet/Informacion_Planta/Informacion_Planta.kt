package edu.unicauca.example.pocketplanet.Informacion_Planta


import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect

import edu.unicauca.example.pocketplanet.Presentacion.bottonRedondoStateless


@Composable
fun Informacion_Planta(navController: NavHostController, plantaNombre: String, viewModel: InformacionPlantaViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    // Cargar la planta usando el ViewModel por nombre
    LaunchedEffect(plantaNombre) {
        viewModel.cargarDetallesPlanta(plantaNombre)
    }

    val planta = viewModel.planta.value

    // Esperamos a que los datos estén disponibles
    if (planta == null) {
        // Si la planta aún no se ha cargado, mostramos un cargando...
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            androidx.compose.material3.CircularProgressIndicator()
        }

    } else {
        // Una vez que tenemos la planta, mostramos sus detalles
        Scaffold { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Botón de regreso en la parte superior izquierda
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopStart) {
                        bottonRedondoStateless(
                            onClick = { navController.popBackStack() },
                            icon = androidx.compose.material.icons.Icons.Default.ArrowBack,
                            colors = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.size(40.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Encabezado
                    Text(
                        text = "Detalles de la Planta",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Mostramos los detalles de la planta en un cuadro
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(16.dp)
                    ) {
                        Column {
                            Text(text = "Nombre: ${planta.nombre}", color = MaterialTheme.colorScheme.onSurface)
                            Text(text = "Tipo: ${planta.tipo}", color = MaterialTheme.colorScheme.onSurface)
                            Text(text = "Horas de Sol: ${planta.horasSol}", color = MaterialTheme.colorScheme.onSurface)
                            Text(text = "Cantidad de Agua: ${planta.cantidadAgua} litros", color = MaterialTheme.colorScheme.onSurface)
                            Text(text = "Tipo de Fertilización: ${planta.tipoFertilizacion}", color = MaterialTheme.colorScheme.onSurface)
                            Text(text = "Información Adicional: ${planta.informacionAdicional}", color = MaterialTheme.colorScheme.onSurface)
                        }
                    }
                }
            }
        }
    }
}