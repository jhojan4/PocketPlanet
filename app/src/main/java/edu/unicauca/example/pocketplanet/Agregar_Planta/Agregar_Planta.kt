package edu.unicauca.example.pocketplanet.Agregar_Planta

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import edu.unicauca.example.pocketplanet.Funciones.Imagenes
import edu.unicauca.example.pocketplanet.Funciones.TopScreen
import edu.unicauca.example.pocketplanet.InicioAplicacion.NavigationScreens
import edu.unicauca.example.pocketplanet.Presentacion.bottonRedondoStateless
import edu.unicauca.example.pocketplanet.R
import edu.unicauca.example.pocketplanet.Screens
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarPlanta(navController: NavHostController, userId:String,modifier: Modifier = Modifier) {
    // Estado para los campos del formulario
    val nombrePlanta = remember { mutableStateOf("") }
    val tipoPlanta = remember { mutableStateOf("") }
    val horasSol = remember { mutableStateOf("") }
    val cantidadAgua = remember { mutableStateOf("") }
    val tipoFertilizacion = remember { mutableStateOf("") }
    val informacionAdicional = remember { mutableStateOf("") }

    // SnackbarHostState para mostrar el mensaje
    val snackbarHostState = remember { SnackbarHostState() }

    // Crear un Scope para las corrutinas
    val coroutineScope = rememberCoroutineScope()

    // ViewModel
    val viewModel: AgregarPlantaViewModel = viewModel()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) } // Snackbar en el Scaffold
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Caja con los campos del formulario
            TopScreen()

            Column(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(16.dp)
            ) {
                // Botón de regreso
                Box(modifier = Modifier.padding(bottom = 16.dp)) {
                    bottonRedondoStateless(onClick={navController.navigate("${Screens.InicioAplicacion.name}/${userId}")},
                        Icons.Default.ArrowBack,
                        colors = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier
                            .size(width = 40.dp, height = 40.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Box(modifier = Modifier.align(Alignment.CenterHorizontally)){Imagenes(R.drawable.logo,100)}
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = nombrePlanta.value,
                    onValueChange = { nombrePlanta.value = it },
                    label = { Text("Nombre de la Planta") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = tipoPlanta.value,
                    onValueChange = { tipoPlanta.value = it },
                    label = { Text("Tipo de Planta") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = horasSol.value,
                    onValueChange = { horasSol.value = it },
                    label = { Text("Horas de Sol") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = cantidadAgua.value,
                    onValueChange = { cantidadAgua.value = it },
                    label = { Text("Cantidad de Agua (Lt)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = tipoFertilizacion.value,
                    onValueChange = { tipoFertilizacion.value = it },
                    label = { Text("Tipo de Fertilización") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = informacionAdicional.value,
                    onValueChange = { informacionAdicional.value = it },
                    label = { Text("Información Adicional") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Botón de Guardar
                Button(
                    onClick = {
                        val planta = Planta(
                            nombre = nombrePlanta.value,
                            tipo = tipoPlanta.value,
                            horasSol = horasSol.value.toIntOrNull() ?: 0,
                            cantidadAgua = cantidadAgua.value.toFloatOrNull() ?: 0f,
                            tipoFertilizacion = tipoFertilizacion.value,
                            informacionAdicional = informacionAdicional.value
                        )

                        // Llamamos al ViewModel para guardar la planta con userId
                        viewModel.agregarPlanta(planta, userId)

                        // Mostrar el Snackbar con la confirmación
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                message = "✅ Planta registrada correctamente",
                                duration = SnackbarDuration.Short
                            )
                            delay(1000) // Espera 1 segundo para mostrar el mensaje
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Guardar Planta")
                }
            }
            NavigationScreens(navController,userId,modifier
                .background(MaterialTheme.colorScheme.secondaryContainer.copy(0.5f))
                .fillMaxWidth()
                .size(70.dp)
                .align(Alignment.BottomCenter))

        }

    }
}






