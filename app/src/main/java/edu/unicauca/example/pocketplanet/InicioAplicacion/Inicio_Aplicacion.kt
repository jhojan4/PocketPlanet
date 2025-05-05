package edu.unicauca.example.pocketplanet.InicioAplicacion

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import edu.unicauca.example.pocketplanet.Funciones.TopScreen
import edu.unicauca.example.pocketplanet.Inicio_Sesion.LabelDatos
import edu.unicauca.example.pocketplanet.Presentacion.bottonRedondoStateless
import edu.unicauca.example.pocketplanet.R
import edu.unicauca.example.pocketplanet.Screens
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.unicauca.example.pocketplanet.Funciones.Imagenes
import edu.unicauca.example.pocketplanet.Inicio_Aplicacion.InicioAplicacionViewModel
import edu.unicauca.example.pocketplanet.Inicio_Aplicacion.Planta
import kotlinx.coroutines.delay



@Composable
fun Screen_Inicio_Aplicacion(navController: NavHostController, userId: String, modifier: Modifier = Modifier) {
    val viewModel : InicioAplicacionViewModel=viewModel()
    var textoBusqueda by remember { mutableStateOf("") }
    var control :Boolean=true
    val isBuscando = textoBusqueda.isNotEmpty()

    LaunchedEffect(isBuscando) {
        while (!isBuscando && control) {
            viewModel.cargarPlantas(userId)
            delay(3000) // Se ejecuta solo si no se está buscando
        }
    }

    // Al entrar a la pantalla, cargar las plantas desde Firestore
    /*LaunchedEffect(Unit) {
        viewModel.cargarPlantas()
    }*/

    /*LaunchedEffect(Unit) {

        while (control) { // 🔥 Bucle infinito controlado
            viewModel.cargarPlantas(userId)
            delay(3000) // espera 30 segundos (30000 milisegundos) antes de volver a cargar
        }
    }*/


    Scaffold{paddingValues ->
        Box(modifier=modifier.padding(paddingValues).fillMaxSize()){
            TopScreen()
            Column(modifier=Modifier.padding(top=10.dp).fillMaxSize()){
                LabelDatos(value = textoBusqueda, onValueChange = { nuevoValor ->
                    textoBusqueda = nuevoValor
                    viewModel.buscarPlantas(nuevoValor)
                },
                    stringResource(R.string.buscador), Icons.Default.Search,
                    modifier = Modifier.size(width = 400.dp, height = 50.dp)
                )
                Spacer(modifier = Modifier.height(30.dp))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .padding(10.dp)
                        //.fillMaxWidth()
                        .fillMaxSize()
                        //.height(570.dp)
                        //.background(MaterialTheme.colorScheme.secondaryContainer.copy(0.5f))
                ){
                    items(viewModel.plantasFiltradas.size) { index ->
                        // Aquí vamos a agregar la acción de navegación al hacer clic en una planta
                        val planta = viewModel.plantasFiltradas[index]
                        PlantaCard(planta = planta, navController = navController) // Pasamos el navController
                    }

                }
                //Aqui está lo de los botones de navigación aqui abajo



            }
            Box(modifier=Modifier
                .padding(horizontal = 30.dp,vertical = 90.dp)
                //.fillMaxWidth()
                .size(40.dp).align(Alignment.BottomEnd),
               /* contentAlignment = Alignment.BottomEnd*/){
                bottonRedondoStateless(onClick={navController.navigate("${Screens.AgregarPlantaScreen.name}/${userId}")},
                    Icons.Default.Add,colors =MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier
                        .size(width = 40.dp, height = 40.dp)
                )
            }
            NavigationScreens(navController,userId,modifier
                .background(MaterialTheme.colorScheme.secondaryContainer.copy(0.5f))
                .fillMaxWidth()
                .size(70.dp)
                .align(Alignment.BottomCenter)

            )
        }

    }

}

@Composable
fun PlantaCard(planta: Planta,navController: NavHostController) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceDim.copy(0.3f)
        ),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(150.dp)
            .border(0.4.dp, MaterialTheme.colorScheme.scrim, RoundedCornerShape(20.dp))
            .clickable { // Aquí agregamos la acción de clic
                navController.navigate("informacion_planta/${planta.nombre}")
            }
    ) {
        Column(
            modifier = Modifier
                .padding(1.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Luego puedes poner imagen si quieres
            Imagenes(R.drawable.logo,80)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = planta.nombre, style = MaterialTheme.typography.bodyLarge)
        }
    }
}



@Composable
fun NavigationScreens(navController: NavHostController,userId: String,modifier: Modifier) {
    Box(modifier = modifier,) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = modifier
                .align(Alignment.Center)
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                bottonRedondoStateless(
                    onClick = { navController.navigate("${Screens.InicioAplicacion.name}/${userId}") },
                    Icons.Default.Home,
                    colors = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.size(35.dp)
                )
                Text(stringResource(R.string.Inicio), fontSize = 10.sp)
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                bottonRedondoStateless(
                    onClick = { navController.navigate("${Screens.AlertConfigScreen.name}/${userId}") }, // Navegación a AlertConfigScreen
                    Icons.Default.Notifications,
                    colors = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.size(35.dp)
                )
                Text(stringResource(R.string.Alertas), fontSize = 10.sp)
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                bottonRedondoStateless(
                    onClick = { navController.navigate("${Screens.ConsejosScreen.name}/${userId}") },
                    Icons.Default.Comment,
                    colors = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.size(35.dp)
                )
                Text(stringResource(R.string.Consejos), fontSize = 10.sp)
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                bottonRedondoStateless(
                    onClick = { navController.navigate("${Screens.ConfiguracionesScreen.name}/${userId}") },
                    Icons.Default.Settings,
                    colors = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.size(35.dp)
                )
                Text(stringResource(R.string.Perfil), fontSize = 10.sp)
            }
        }
    }
}