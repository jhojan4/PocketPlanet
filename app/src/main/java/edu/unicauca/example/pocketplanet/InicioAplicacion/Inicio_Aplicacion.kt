package edu.unicauca.example.pocketplanet.InicioAplicacion

//import android.os.Build.VERSION_CODES.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import edu.unicauca.example.pocketplanet.Funciones.TopScreen
import edu.unicauca.example.pocketplanet.Inicio_Sesion.Inicio_Sesio
import edu.unicauca.example.pocketplanet.Inicio_Sesion.LabelDatos
import edu.unicauca.example.pocketplanet.Presentacion.bottonRedondo
import edu.unicauca.example.pocketplanet.Presentacion.bottonRedondoStateless
import edu.unicauca.example.pocketplanet.R
import edu.unicauca.example.pocketplanet.Screens
//import edu.unicauca.example.pocketplanet.R
import edu.unicauca.example.pocketplanet.ui.theme.PocketPlanetTheme

/*class Inicio_Aplicacion : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PocketPlanetTheme{
                Scaffold(
                    modifier = Modifier.fillMaxWidth(),
                    content = { paddingValues -> // 🔹 Corrige la estructura del lambda
                        Box(modifier = Modifier.padding(paddingValues)) {
                            Screen_Inicio_Aplicacion()
                        }
                    }
                )
            }
        }
    }
}*/

@Composable
fun Screen_Inicio_Aplicacion(navController: NavHostController, modifier: Modifier = Modifier) {
    Box(modifier=modifier){
        TopScreen()
        Column(modifier=Modifier.padding(top=10.dp)){
            LabelDatos(
                stringResource(R.string.buscador), Icons.Default.Search,
                modifier = Modifier.size(width = 400.dp, height = 50.dp)
            )
            Spacer(modifier = Modifier.weight(8f))

            Box(modifier=Modifier.align(Alignment.CenterHorizontally)
                .background(MaterialTheme.colorScheme.secondaryContainer.copy(0.5f))
                .size(width = 400.dp, height = 70.dp)

            ){
                Row(horizontalArrangement = Arrangement.SpaceAround,modifier=Modifier.align(Alignment.Center)
                    .padding(10.dp)
                    .fillMaxWidth()){
                    bottonRedondoStateless(onClick = {}, Icons.Default.Home, colors = MaterialTheme.colorScheme.tertiary, modifier = Modifier
                        .size(35.dp))
                    bottonRedondoStateless(onClick = {navController.navigate(Screens.ConsejosScreen.name)}, Icons.Default.Notifications, colors = MaterialTheme.colorScheme.tertiary, modifier = Modifier
                        .size (35.dp))
                    bottonRedondoStateless(onClick = {}, Icons.Default.Star, colors = MaterialTheme.colorScheme.tertiary, modifier = Modifier
                        .size (35.dp))
                    bottonRedondoStateless(onClick = {}, Icons.Default.Settings, colors = MaterialTheme.colorScheme.tertiary, modifier = Modifier
                        .size (35.dp)
                    )
                }
            }
        }
    }
}
/*
@Preview
@Composable
fun Inicio_AplicacionPreview(){
    PocketPlanetTheme{
        Scaffold(
            content = { paddingValues -> // 🔹 Corrige la estructura del lambda
                Box(modifier = Modifier
                    .padding(paddingValues)) {
                    Screen_Inicio_Aplicacion()
                }
            }
        )
    }
}*/