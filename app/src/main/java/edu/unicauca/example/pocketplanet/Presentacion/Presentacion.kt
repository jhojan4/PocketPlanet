package edu.unicauca.example.pocketplanet.Presentacion

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import edu.unicauca.example.pocketplanet.Funciones.EndScreen
import edu.unicauca.example.pocketplanet.Funciones.Imagenes
import edu.unicauca.example.pocketplanet.R
import edu.unicauca.example.pocketplanet.Funciones.TitleIcon
import edu.unicauca.example.pocketplanet.Screens
//import com.example.compose.PocketPlanetTheme
import edu.unicauca.example.pocketplanet.ui.theme.PocketPlanetTheme
@Composable
fun backgroundPresentacion(navController: NavHostController,
                           viewModel: PresentacionViewModel =viewModel(),
                           modifier: Modifier = Modifier.fillMaxSize()) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onPrimary) // Fondo superior blanco
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            TitleIcon()
            MiddleScreen()
            Spacer(modifier = Modifier.weight(1f)) // Empuja el contenido hacia arriba
            EndScreen() // ✅ Ahora está dentro del fondo

            }

        Box(modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(vertical = 120.dp))  {
            Row() {
                cambioPantallaStateless(
                    onClick = {navController.navigate(Screens.InicioSesionScreen.name)},
                    description = stringResource(R.string.buttom_inicio)
                )
                Spacer(modifier = Modifier.width(30.dp))
                cambioPantallaStateless(onClick = { navController.navigate(Screens.RegistroScreen.name) }, description = stringResource(R.string.Buttom_Register),
                )
            }

        }
    }
}





/*@Preview
@Composable
fun backgroundPocketPlanetPreview() {
    PocketPlanetTheme{
        Scaffold(
            content = { paddingValues -> // 🔹 Corrige la estructura del lambda
                Box(modifier = Modifier.padding(paddingValues)) {
                    backgroundPresentacion()
                }
            }
        )
    }
}
*/





@Composable
fun MiddleScreen(modifier: Modifier=Modifier
                 ){
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)
        .padding(40.dp),
        contentAlignment = Alignment.Center){
        Row (modifier=modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically){
            Box(modifier=Modifier.weight(1f)){
                cambioImagenesStateless(R.drawable.alerts,stringResource( R.string.Presentacion1),onClick = {})
            }
        }
    }

}
//Acciones de los botones
@Composable
fun cambioImagenesStateless(urlImages:Int, texto:String, onClick:() -> Unit, modifier: Modifier=Modifier){

    BottonActionImagenes(onClick = onClick, description = texto)

}
@Composable
fun BottonActionImagenes(onClick: () -> Unit, description:String,modifier: Modifier=Modifier):Unit {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = description,
            modifier = modifier
        )
        Text(description)

    }
}

//Estas dos funciones estan con StateJetPackCompose y permite separar los botones de las acciones, si se desean usar y tomar como ejemplo las p
//las pantallas de rsgistro e inicio, esa es la forma en las que van a quedar, solo hace falta las direcciones de ruta para que funcionen

@Composable
fun cambioPantallaStateless(onClick: () -> Unit, description:String,modifier: Modifier=Modifier):Unit {
    bottonCambioPantalla(onClick = onClick, description = description)
}
@Composable
fun bottonCambioPantalla(onClick: () -> Unit, description:String, modifier: Modifier=Modifier){
    Button(onClick = onClick,
        colors=ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary),
        modifier = modifier
            .size(width = 120.dp, height = 35.dp)
    ){

        Text(description)

    }
}
@Composable
fun bottonRedondoStateless(onClick: () -> Unit, icon: ImageVector, colors:Color, modifier: Modifier=Modifier){
    bottonRedondo(onClick = onClick, iconboton = icon,colorsbot = colors,modifier = modifier)
}
//Botones que redondos que permiten cambiar de pantallas o navegar por una pantalla
@Composable
fun bottonRedondo(onClick: () -> Unit, iconboton:ImageVector,colorsbot:Color ,modifier: Modifier=Modifier){
    Button(onClick = onClick,colors = ButtonDefaults.buttonColors(colorsbot), contentPadding = PaddingValues(5.dp),modifier = modifier) {
        Icon(
            imageVector = iconboton,
            contentDescription = null,
            modifier = modifier
        )
    }
}
