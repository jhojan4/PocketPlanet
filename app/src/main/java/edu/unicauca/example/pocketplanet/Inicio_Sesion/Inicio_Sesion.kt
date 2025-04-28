package edu.unicauca.example.pocketplanet.Inicio_Sesion


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import edu.unicauca.example.pocketplanet.Funciones.BackGroundPocketPlanetInicial
import edu.unicauca.example.pocketplanet.Funciones.Imagenes
import edu.unicauca.example.pocketplanet.Presentacion.bottonRedondoStateless
import edu.unicauca.example.pocketplanet.Presentacion.cambioPantallaStateless
import edu.unicauca.example.pocketplanet.R
import edu.unicauca.example.pocketplanet.Registro.CustomSnackbarHost
import edu.unicauca.example.pocketplanet.Screens
import edu.unicauca.example.pocketplanet.Session.UserSessionViewModel
//import edu.unicauca.example.pocketplanet.Presentacion.backgroundPocketPlanet
//import com.example.compose.PocketPlanetTheme
import edu.unicauca.example.pocketplanet.ui.theme.PocketPlanetTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Inicio_Sesio(navController: NavHostController,modifier: Modifier) {
    val viewModel: LoginViewModel = viewModel()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val userSessionViewModel: UserSessionViewModel = viewModel()


    Scaffold(snackbarHost = { CustomSnackbarHost(hostState = snackbarHostState) } ){paddingValues ->
       Box(
           modifier = modifier
               .fillMaxSize()
               .background(MaterialTheme.colorScheme.surface)
               .padding(paddingValues)
       ) {
           // Fondo estructurado en Column
           BackGroundPocketPlanetInicial()
           //Botton superior
           Box(modifier=Modifier.align(Alignment.TopStart).padding(10.dp)){
               bottonRedondoStateless(onClick={navController.navigate(Screens.PresentacionScreen.name)}, Icons.Default.ArrowBack,colors =MaterialTheme.colorScheme.tertiary, modifier = Modifier
                   .size(width = 40.dp, height = 40.dp)
               )
           }
           // Tarjeta de Inicio de Sesión centrada en la pantalla
           Box(
               modifier = Modifier
                   .fillMaxSize(),
               contentAlignment = Alignment.Center
           ) {
               Card_InicioSesion(navController,viewModel,userSessionViewModel,snackbarHostState = snackbarHostState,
                   coroutineScope = coroutineScope,)
           }
       }
   }
}
/*
@Preview
@Composable
fun backgroundInicioSesionPreview(){
    PocketPlanetTheme{
        Scaffold(
            content = { paddingValues -> // 🔹 Corrige la estructura del lambda
                Box(modifier = Modifier
                    .padding(paddingValues)) {

                }
            }
        )
    }
}
*/

@Composable
fun Card_InicioSesion(navController: NavHostController,viewModel: LoginViewModel, userSessionViewModel: UserSessionViewModel,snackbarHostState: SnackbarHostState,
                      coroutineScope: CoroutineScope,modifier: Modifier=Modifier) {
    Box(modifier=modifier){
        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(580.dp)
                .padding(20.dp)
                .border(
                    0.8.dp,
                    MaterialTheme.colorScheme.scrim.copy(0.5f),
                    RoundedCornerShape(30.dp)
                ),

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
                Imagenes(R.drawable.logo,120)
                Spacer(modifier = Modifier.height(60.dp))
                LabelDatos(value = viewModel.email, onValueChange = { viewModel.email = it } ,stringResource(R.string.Users),Icons.Default.AccountCircle,
                    modifier = Modifier.size(width = 400.dp, height = 50.dp))
                Spacer(modifier = Modifier.height(15.dp))
                LabelDatos(value = viewModel.password, onValueChange = { viewModel.password = it },stringResource(R.string.Password),Icons.Default.Lock,esPassword = true,
                    modifier = Modifier.size(width = 400.dp, height = 50.dp))
                Spacer(modifier = Modifier.height(25.dp))
                //cambioPantallaStateless(onClick = {navController.navigate(Screens.InicioAplicacion.name)}, description = stringResource(R.string.buttom_iniciar_sesion))
                cambioPantallaStateless(
                    onClick = {
                        viewModel.loginUser(
                            onSuccess = {
                                // Si el login es correcto, navegas y ya tienes el userId guardado
                                //Aqui guardo el id en la variable de viewmodel de usersession
                                viewModel.userId?.let { id ->
                                    userSessionViewModel.setUserId(id) // 🔥 Guardas el userId globalmente
                                }
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "✅ Inicio de sesión exitoso",
                                        duration = SnackbarDuration.Short
                                    )
                                    delay(1000) // esperar para ver el mensaje
                                    //Se le pasa el Id de la persona que inicio sesión
                                    navController.navigate("${Screens.InicioAplicacion.name}/${viewModel.userId}")
                                }
                            },
                            onError = { message ->
                                // Mostrar Snackbar o error
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "❌ $message",
                                        duration = SnackbarDuration.Long
                                    )
                                }
                            }
                        )
                    },
                    description = stringResource(R.string.buttom_iniciar_sesion)

                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(stringResource(R.string.sinCuenta),modifier = Modifier.clickable { navController.navigate(
                    Screens.RegistroScreen.name) })
            }
        }

    }

}

//Lo puedo hacer con lo de estados
//Esta funcion permite crear campos de texto donde se puede ingresar información
//Recibe como parámetros de entrada un texto, un icono y un modificador
@Composable
fun LabelDatos(
    value: String,
    onValueChange: (String) -> Unit,
    textoId:String
    ,icon: ImageVector,
    esPassword: Boolean = false,
    modifier: Modifier = Modifier) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        visualTransformation = if (esPassword) PasswordVisualTransformation() else VisualTransformation.None,
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null
            )
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceDim.copy(0.1f)
        ),
        placeholder = {
            Text(textoId)
        },
        modifier = modifier
            .heightIn(min=20.dp)
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .border(0.3.dp, MaterialTheme.colorScheme.scrim
                , RoundedCornerShape(30.dp))
            .clip(RoundedCornerShape(30.dp))
        
    )
}



