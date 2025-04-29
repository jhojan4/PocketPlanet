package edu.unicauca.example.pocketplanet.Registro

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import edu.unicauca.example.pocketplanet.Agregar_Planta.AgregarPlanta
import edu.unicauca.example.pocketplanet.Funciones.BackGroundPocketPlanetInicial
import edu.unicauca.example.pocketplanet.Funciones.Imagenes
import edu.unicauca.example.pocketplanet.Inicio_Sesion.LabelDatos
import edu.unicauca.example.pocketplanet.Presentacion.bottonRedondoStateless
import edu.unicauca.example.pocketplanet.Presentacion.cambioPantallaStateless
import edu.unicauca.example.pocketplanet.R
import edu.unicauca.example.pocketplanet.Screens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun backgroundRegistro(navController: NavHostController, modifier: Modifier = Modifier) {
    val viewModel: RegistroViewModel = viewModel()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { CustomSnackbarHost(hostState = snackbarHostState) } // 🔥 Snackbars personalizados
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(paddingValues)
        ) {
            BackGroundPocketPlanetInicial() // 🔥 Fondo personalizado PocketPlanet

            Box(modifier = Modifier.align(Alignment.TopStart).padding(10.dp)) {
                bottonRedondoStateless(
                    onClick = { navController.navigate(Screens.PresentacionScreen.name) },
                    Icons.Default.ArrowBack,
                    colors = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.size(40.dp)
                )
            }

            Card_Registro(
                navController = navController,
                viewModel = viewModel,
                snackbarHostState = snackbarHostState,
                coroutineScope = coroutineScope,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun Card_Registro(
    navController: NavHostController,
    viewModel: RegistroViewModel,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(800.dp)
                .padding(30.dp)
                .border(0.8.dp, MaterialTheme.colorScheme.scrim.copy(0.5f), RoundedCornerShape(30.dp)),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceDim.copy(0.3f)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Imagenes(R.drawable.logo, 120) // 🔥 Logo de PocketPlanet
                Spacer(modifier = Modifier.height(30.dp))
                LabelDatos(viewModel.userName, { viewModel.userName = it }, stringResource(R.string.Users), Icons.Default.AccountCircle, modifier = Modifier.size(400.dp, 50.dp))
                Spacer(modifier = Modifier.height(30.dp))
                LabelDatos(viewModel.email, { viewModel.email = it }, stringResource(R.string.Email), Icons.Default.Email, modifier = Modifier.size(400.dp, 50.dp))
                Spacer(modifier = Modifier.height(30.dp))
                LabelDatos(viewModel.country, { viewModel.country = it }, stringResource(R.string.Country), Icons.Default.LocationOn, modifier = Modifier.size(400.dp, 50.dp))
                Spacer(modifier = Modifier.height(30.dp))
                LabelDatos(viewModel.phoneNumber, { viewModel.phoneNumber = it }, stringResource(R.string.PhoneNumber), Icons.Default.Call, modifier = Modifier.size(400.dp, 50.dp))
                Spacer(modifier = Modifier.height(30.dp))
                LabelDatos(viewModel.password, { viewModel.password = it }, stringResource(R.string.Password), Icons.Default.Lock, esPassword = true, modifier = Modifier.size(400.dp, 50.dp))
                Spacer(modifier = Modifier.height(30.dp))
                LabelDatos(viewModel.repeatPassword, { viewModel.repeatPassword = it }, stringResource(R.string.RepeatPassword), Icons.Default.Lock, esPassword = true, modifier = Modifier.size(400.dp, 50.dp))
                Spacer(modifier = Modifier.height(30.dp))

                cambioPantallaStateless(
                    onClick = {
                        viewModel.registerUser(
                            onSuccess = {
                                // 🔥 Guardamos el correo registrado para usarlo en la pantalla de Perfil
                                //PerfilScreenData.originalEmail = viewModel.email
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "✅ Usuario registrado correctamente",
                                        duration = SnackbarDuration.Short
                                    )
                                    delay(1000) // 🔥 Espera 1 segundo para ver el mensaje
                                    navController.navigate(Screens.InicioSesionScreen.name)
                                }
                            },
                            onError = { message ->
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "❌ $message",
                                        duration = SnackbarDuration.Long
                                    )
                                }
                            }
                        )
                    },
                    description = stringResource(R.string.Buttom_Register)
                )
            }
        }
    }
}

@Composable
fun CustomSnackbarHost(hostState: SnackbarHostState) {
    SnackbarHost(hostState = hostState) { data ->
        val isSuccess = data.visuals.message.startsWith("✅")
        val backgroundColor = if (isSuccess) Color(0xFF4CAF50) else Color(0xFFF44336)

        Snackbar(
            snackbarData = data,
            containerColor = backgroundColor,
            contentColor = Color.White,
            actionColor = Color.Yellow
        )
    }
}

