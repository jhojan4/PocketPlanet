package edu.unicauca.example.pocketplanet.Registro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.navOptions
import edu.unicauca.example.pocketplanet.Funciones.BackGroundPocketPlanetInicial
import edu.unicauca.example.pocketplanet.Funciones.Imagenes
import edu.unicauca.example.pocketplanet.Inicio_Sesion.LabelDatos
import edu.unicauca.example.pocketplanet.Presentacion.bottonRedondoStateless
import edu.unicauca.example.pocketplanet.Presentacion.cambioPantallaStateless
import edu.unicauca.example.pocketplanet.R
import edu.unicauca.example.pocketplanet.Screens
import edu.unicauca.example.pocketplanet.ui.theme.PocketPlanetTheme


/*class Registro(navController: NavHostController) : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PocketPlanetTheme{
                Scaffold(
                    modifier = Modifier.fillMaxWidth(),
                    content = { paddingValues -> // 🔹 Corrige la estructura del lambda
                        Box(modifier = Modifier.padding(paddingValues)) {
                            backgroundRegistro()
                        }
                    }
                )
            }
        }
    }

}*/
@Composable
fun backgroundRegistro(navController: NavHostController,modifier: Modifier=Modifier) {
    Box(modifier = modifier.fillMaxWidth()) {
        BackGroundPocketPlanetInicial()
        Box(modifier=Modifier.align(Alignment.TopStart).padding(30.dp)){
            bottonRedondoStateless(onClick={navController.navigate(Screens.PresentacionScreen.name)}, Icons.Default.ArrowBack,colors =MaterialTheme.colorScheme.tertiary, modifier = Modifier
                .size(width = 40.dp, height = 40.dp)


            )
        }
        Card_Registro(navController,modifier.align(Alignment.Center))
    }
}
@Composable
fun Card_Registro(navController: NavHostController,modifier: Modifier = Modifier) {
    Box(modifier=modifier) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(800.dp)
                .padding(30.dp)
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
                horizontalAlignment = Alignment.CenterHorizontally){
                Imagenes(R.drawable.logo,120)
                Spacer(modifier = Modifier.height(30.dp))
                LabelDatos(stringResource(R.string.Users), Icons.Default.AccountCircle,modifier = Modifier.size(width = 400.dp, height = 50.dp))
                Spacer(modifier = Modifier.height(30.dp),)
                LabelDatos(stringResource(R.string.Email), Icons.Default.Email,
                    modifier = Modifier.size(width = 400.dp, height = 50.dp))
                Spacer(modifier = Modifier.height(30.dp))
                LabelDatos(stringResource(R.string.Country), Icons.Default.LocationOn,
                    modifier = Modifier.size(width = 400.dp, height = 50.dp))
                Spacer(modifier = Modifier.height(30.dp))
                LabelDatos(stringResource(R.string.PhoneNumber), Icons.Default.Call,
                    modifier = Modifier.size(width = 400.dp, height = 50.dp))
                Spacer(modifier = Modifier.height(30.dp))
                LabelDatos(stringResource(R.string.Password), Icons.Default.Lock,
                    modifier = Modifier.size(width = 400.dp, height = 50.dp))
                Spacer(modifier = Modifier.height(30.dp))
                LabelDatos(stringResource(R.string.RepeatPassword), Icons.Default.Lock,
                    modifier = Modifier.size(width = 400.dp, height = 50.dp))
                Spacer(modifier = Modifier.height(30.dp))
                cambioPantallaStateless(onClick = {navController.navigate(Screens.InicioSesionScreen.name)}, description = stringResource(R.string.Buttom_Register))



            }

        }
    }
}
@Preview
@Composable
fun backgroundRegistroPreview(){
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
