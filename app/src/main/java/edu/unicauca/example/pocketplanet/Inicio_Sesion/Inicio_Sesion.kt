package edu.unicauca.example.pocketplanet.Inicio_Sesion


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.unicauca.example.pocketplanet.Funciones.BackGroundPocketPlanetInicial
import edu.unicauca.example.pocketplanet.Funciones.Imagenes
import edu.unicauca.example.pocketplanet.Presentacion.bottonCambioPantalla
import edu.unicauca.example.pocketplanet.Presentacion.cambioPantallaStateless
import edu.unicauca.example.pocketplanet.R
//import edu.unicauca.example.pocketplanet.Presentacion.backgroundPocketPlanet
//import com.example.compose.PocketPlanetTheme
import edu.unicauca.example.pocketplanet.ui.theme.PocketPlanetTheme

class Inicio_Sesion : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PocketPlanetTheme{
                Scaffold(
                    modifier = Modifier.fillMaxWidth(),
                    content = { paddingValues -> // 🔹 Corrige la estructura del lambda
                        Box(modifier = Modifier.padding(paddingValues)) {
                            Inicio_Sesio()
                        }
                    }
                )
            }
        }
    }
}
@Composable
fun Inicio_Sesio(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        // Fondo estructurado en Column
        BackGroundPocketPlanetInicial()
        // Tarjeta de Inicio de Sesión centrada en la pantalla
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Card_InicioSesion()
        }
    }
}

@Preview
@Composable
fun backgroundInicioSesionPreview(){
    PocketPlanetTheme{
        Scaffold(
            content = { paddingValues -> // 🔹 Corrige la estructura del lambda
                Box(modifier = Modifier
                    .padding(paddingValues)) {
                    Inicio_Sesio()
                }
            }
        )
    }
}


@Composable
fun Card_InicioSesion(modifier: Modifier = Modifier) {
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
                LabelDatos(stringResource(R.string.Users),Icons.Default.AccountCircle,
                    modifier = Modifier.size(width = 400.dp, height = 50.dp))
                Spacer(modifier = Modifier.height(15.dp))
                LabelDatos(stringResource(R.string.Password),Icons.Default.Lock,
                    modifier = Modifier.size(width = 400.dp, height = 50.dp))
                Spacer(modifier = Modifier.height(25.dp))
                cambioPantallaStateless(onClick = {}, description = stringResource(R.string.buttom_iniciar_sesion))
            }
        }

    }

}

//Lo puedo hacer con lo de estados
//Esta funcion permite crear campos de texto donde se puede ingresar información
//Recibe como parámetros de entrada un texto, un icono y un modificador
@Composable
fun LabelDatos(
    textoId:String
    ,icon: ImageVector
    ,modifier: Modifier = Modifier) {
    TextField(
        value = "",
        onValueChange = {},
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



