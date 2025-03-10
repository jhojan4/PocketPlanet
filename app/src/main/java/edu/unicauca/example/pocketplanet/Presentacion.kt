package edu.unicauca.example.pocketplanet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.BoxScopeInstance.align
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
//import com.example.compose.PocketPlanetTheme
import edu.unicauca.example.pocketplanet.ui.theme.PocketPlanetTheme

class Presentacion : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PocketPlanetTheme{
                Scaffold(
                    modifier = Modifier.fillMaxWidth(),
                    content = { paddingValues -> // 🔹 Corrige la estructura del lambda
                        Box(modifier = Modifier.padding(paddingValues)) {
                            backgroundPocketPlanet()
                        }
                    }
                )
            }
        }
    }

}


@Composable
fun backgroundPocketPlanet(modifier: Modifier = Modifier.fillMaxSize()) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White) // Fondo superior blanco
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            TopScreen()
            MiddleScreen()
            Spacer(modifier = Modifier.weight(1f)) // Empuja el contenido hacia arriba
            EndScreen() // ✅ Ahora está dentro del fondo
        }
    }
}

@Composable
fun EndScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp) // Altura del fondo verde
            .clip(RoundedCornerShape( topEnd = 100.dp)) // 🔹 Curva en la parte superior
            .background(MaterialTheme.colorScheme.secondaryContainer),
        contentAlignment = Alignment.Center // ✅ Asegura que los botones estén centrados
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            buttoms({}, stringResource(R.string.buttom_inicio)) // ✅ Usar `stringResource`
            Spacer(modifier = Modifier.width(16.dp))
            buttoms({}, stringResource(R.string.Buttom_Register))
        }
    }
}




@Preview
@Composable
fun backgroundPocketPlanetPreview() {
    PocketPlanetTheme{
        Scaffold(
            content = { paddingValues -> // 🔹 Corrige la estructura del lambda
                Box(modifier = Modifier.padding(paddingValues)) {
                    backgroundPocketPlanet()
                }
            }
        )
    }
}


@Composable
fun TopScreen(modifier: Modifier=Modifier){
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
                modifier = Modifier.padding(horizontal = 10.dp).align(alignment = Alignment.CenterVertically
                )


            )
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)

            )

        }
    }


}

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
            IconButton(onClick = {},false,modifier=Modifier.size(16.dp)){}
            Image(painter = painterResource(R.drawable.alerts),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)

            )
            Image(painter = painterResource(R.drawable.statistics),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape))

            Image(painter = painterResource(R.drawable.recomendation),
                contentDescription = null,
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape))
            IconButton(onClick = {},true,modifier=Modifier.size(16.dp)){}
        }
    }

}
/*@Composable
fun  EndScreen(modifier: Modifier=Modifier){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp) // Altura de la parte verde
            .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp)) // 🔹 Curva en la parte superior
            .background(MaterialTheme.colorScheme.secondaryContainer)
    )
    Row(modifier=modifier){
        buttoms({}, R.string.buttom_inicio.toString())
        buttoms({}, R.string.Butto
m_Register.toString())
    }
}
*/
@Composable
fun buttoms(onClick: () -> Unit, description:String, modifier: Modifier=Modifier):Unit{

        Button(onClick = { onClick() },
            //modifier=modifier.padding(10.dp),
            //colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)
        ) {
            Text(description)
        }


}
@Composable
fun IconButton(
    onClick: () -> Unit,
    state:Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
){
    if (state ==false){
        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Arrow")
    }else if(state == true) {
        Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = "Arrow")
    }
}