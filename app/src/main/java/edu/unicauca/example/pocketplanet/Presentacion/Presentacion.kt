package edu.unicauca.example.pocketplanet.Presentacion

import androidx.compose.foundation.Image
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import edu.unicauca.example.pocketplanet.Funciones.EndScreen
import edu.unicauca.example.pocketplanet.Funciones.Imagenes
import edu.unicauca.example.pocketplanet.R
import edu.unicauca.example.pocketplanet.Funciones.TitleIcon
import edu.unicauca.example.pocketplanet.Screens
//import com.example.compose.PocketPlanetTheme
import edu.unicauca.example.pocketplanet.ui.theme.PocketPlanetTheme
@Composable
fun backgroundPresentacion(navController: NavHostController,
                           modifier: Modifier = Modifier.fillMaxSize()) {
    Scaffold{paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onPrimary).padding(paddingValues) // Fondo superior blanco
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

}



@Composable
fun MiddleScreen(viewModel: PresentacionViewModel =viewModel(),modifier: Modifier = Modifier) {
    val imageIndex = viewModel.imageIndex.value
    val images = viewModel.images
    val stringsIndex = viewModel.stringsIndex.value
    val strings = viewModel.stringsPrinpals
    Box(
        modifier = Modifier
            .fillMaxWidth()
            //.height(150.dp)
            //.padding(horizontal = 16.dp, vertical = 16.dp),
        //contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            cambioImagenesStateless(
                IconoVector = Icons.Default.ArrowBack,
                onClick = { viewModel.previousImage() }
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                ) {
                    ImagenesPrincipal(
                        images[(imageIndex - 1 + images.size) % images.size],
                        150.dp,
                        2f,
                        Modifier.offset(x = 30.dp)
                    )
                    ImagenesPrincipal(
                        images[(imageIndex + images.size) % images.size],
                        120.dp,
                        1f,
                        Modifier.offset(x = -5.dp)
                    )
                    ImagenesPrincipal(
                        images[(imageIndex + 1 + images.size) % images.size],
                        80.dp,
                        0f,
                        Modifier.offset(x = -50.dp)
                    )
                    ImagenesPrincipal(
                        images[(imageIndex + 2 + images.size) % images.size],
                        30.dp,
                        -1f,
                        Modifier.offset(x = -85.dp)
                    )




                }

                    Spacer(modifier = Modifier.height(20.dp))

                Box(

                    modifier = Modifier.width(200.dp).height(50.dp)) {
                    Text(
                        text = stringResource(id = strings[(stringsIndex - 1 + strings.size) % strings.size]),
                        style = TextStyle(
                            //color = Color.Black,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            shadow = Shadow(
                                color = MaterialTheme.colorScheme.scrim.copy(alpha = 0.1f),
                                offset = Offset(2f, 2f),
                                blurRadius = 5f
                            )

                        ),
                        modifier = modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center

                    )
                }
            }
            cambioImagenesStateless(
                IconoVector = Icons.Default.ArrowForward,
                onClick = {viewModel.nextImage() }
            )
        }
    }
}

@Composable
fun cambioImagenesStateless(IconoVector: ImageVector, onClick:() -> Unit, modifier: Modifier=Modifier){
    BottonActionImagenes(onClick = onClick, IconoVector)
}

@Composable
fun ImagenesPrincipal(imagen: Int, tamanio: Dp, zIndex: Float, modifier: Modifier = Modifier) {
    Box(modifier = modifier
        .zIndex(zIndex)  // Controla el orden de apilamiento
        .clip(CircleShape)
        //.size(tamanio)
    ) {
        Image(
            painter = painterResource(id = imagen),
            contentDescription = null, // Proporciona descripciones adecuadas para la accesibilidad
            modifier = Modifier.size(tamanio)
        )
    }
}
@Preview
@Composable
fun MiddleScreenPreview(){
    MiddleScreen()
}

@Composable
fun BottonActionImagenes(onClick: () -> Unit, icon:ImageVector,modifier: Modifier=Modifier):Unit {
    IconButton(onClick = onClick) {
        Icon(
            contentDescription = "",
            imageVector = icon,
            modifier = modifier
        )

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
