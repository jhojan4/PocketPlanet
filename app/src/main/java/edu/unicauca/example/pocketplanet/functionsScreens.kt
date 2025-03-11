package edu.unicauca.example.pocketplanet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
//Es un box que crea la parte inferior de lal background
fun EndScreen(modifier: Modifier = Modifier,) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp) // Altura del fondo verde
            .clip(RoundedCornerShape( topEnd = 100.dp)) // 🔹 Curva en la parte superior
            .background(MaterialTheme.colorScheme.secondaryContainer.copy(0.5f))
            .border(0.3.dp,
                MaterialTheme.colorScheme.onSecondaryContainer, RoundedCornerShape(topEnd = 100.dp)
            ),
    ) {
        //Row(
        //  modifier = Modifier.padding(16.dp),
        //horizontalArrangement = Arrangement.SpaceEvenly
        //) {
        //buttoms({}, stringResource(R.string.buttom_inicio)) // ✅ Usar `stringResource`
        // Spacer(modifier = Modifier.width(16.dp))
        //buttoms({}, stringResource(R.string.Buttom_Register))
        //}
    }
}
@Composable
fun TopScreen(modifier: Modifier = Modifier){
    Box (
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp) // Altura del fondo verde
            .clip(RoundedCornerShape(bottomStart = 100.dp)) // 🔹 Curva en la parte superior
            .background(MaterialTheme.colorScheme.secondaryContainer.copy(0.5f))
            .border(
                0.3.dp,
                MaterialTheme.colorScheme.onSecondaryContainer,
                RoundedCornerShape(bottomStart = 100.dp)
            ),
    ) {}
}
@Composable
//Es la funcion que crea la parte superior y la parte del fondo
fun BackGroundPocketPlanetInicial(){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween, // Top arriba y End abajo
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopScreen()
        Spacer(modifier = Modifier.weight(8f)) // Empuja hacia arriba y abajo
        EndScreen()
    }
}
@Composable
//Crea imagener que recibi como componente R.drawablw.laimagencreada y un valor entero
fun Imagenes(imagenurl:Int,sizeImage:Int){
    Image(painter = painterResource(/*R.drawable.statistics*/imagenurl),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(sizeImage.dp)
            .clip(CircleShape))

}
@Composable
//Es la construccion de PocketPlanet acompañada del titulo
fun TitleIcon(modifier: Modifier=Modifier){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(40.dp),
        contentAlignment = Alignment.Center
    ){
        Row (
            modifier=modifier
                .padding(30.dp),
            //.fillMaxWidth()

        ) {
            Text(
                stringResource(R.string.title),
                modifier = Modifier.padding(horizontal = 10.dp)
                    .align(alignment = Alignment.CenterVertically,
                    )
            )
            Imagenes( R.drawable.logo,100)

        }
    }


}