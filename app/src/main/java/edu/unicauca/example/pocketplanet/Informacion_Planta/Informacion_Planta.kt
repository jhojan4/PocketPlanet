package edu.unicauca.example.pocketplanet.Informacion_Planta


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun Informacion_Planta(navController: NavHostController, plantaNombre: String, viewModel: InformacionPlantaViewModel = viewModel()) {
    // Cargar la planta usando el ViewModel por nombre
    LaunchedEffect(plantaNombre) {
        viewModel.cargarDetallesPlanta(plantaNombre)
    }

    val planta = viewModel.planta.value

    // Esperamos a que los datos estén disponibles
    if (planta == null) {
        // Si la planta aún no se ha cargado, mostramos un cargando...
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }

    } else {
        // Una vez que tenemos la planta, mostramos sus detalles
        Scaffold { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Detalles de la Planta", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(16.dp))

                    // Mostramos los detalles de la planta
                    Text(text = "Nombre: ${planta.nombre}")
                    Text(text = "Tipo: ${planta.tipo}")
                    Text(text = "Horas de Sol: ${planta.horasSol}")
                    Text(text = "Cantidad de Agua: ${planta.cantidadAgua} litros")
                    Text(text = "Tipo de Fertilización: ${planta.tipoFertilizacion}")
                    Text(text = "Información Adicional: ${planta.informacionAdicional}")
                }
            }
        }
    }
}