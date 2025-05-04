package edu.unicauca.example.pocketplanet.Consejos

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import edu.unicauca.example.pocketplanet.InicioAplicacion.NavigationScreens
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.unicauca.example.pocketplanet.PerfilConfiguraciones.PerfilViewModel
import edu.unicauca.example.pocketplanet.R


/*class ConsejosJardineria : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PocketPlanetTheme {
            }
        }
    }
}
*/
//Pantalla principal de consejos de jardinería
@Composable
fun ConsejosScreen(
    navController: NavHostController,
    userId: String,
    modifier: Modifier
) {
    //val storedUserId by remember { mutableStateOf(userId) }
    var searchText by remember { mutableStateOf("") }

    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding(),
                contentAlignment = Alignment.Center
            ) {
                NavigationScreens(
                    navController,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.secondaryContainer.copy(0.5f))
                        .fillMaxWidth()
                        .height(70.dp), // Usa height en lugar de size para evitar restricciones innecesarias
                    userId = userId
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Botón de regreso
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Título de la pantalla
            Text("Consejos de Jardinería", fontSize = 20.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(16.dp))

            // Barra de búsqueda
            SearchBar{searchText = it}

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de plantas
            PlantList(navController, searchText)
        }
    }
}




//Barra de búsqueda
@Composable
fun SearchBar(onSearchChanged: (String) -> Unit) {
    var searchText by remember { mutableStateOf("") }

    TextField(
        value = searchText,
        onValueChange = {
            searchText = it
            onSearchChanged(it)  // Llama a la función para actualizar el texto en la pantalla principal
        },
        placeholder = { Text("Busca consejos") },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Buscar") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(8.dp))
    )
}


//Lista de plantas disponibles
@Composable
fun PlantList(navController: NavController, searchText: String) {
    val plantas = listOf(
        "Aloe Vera" to R.drawable.aloe_vera,
        "Albahaca" to R.drawable.albahaca,
        "Agave" to R.drawable.agave,
        "Buganvilla" to R.drawable.calatea,
        "Bambú" to R.drawable.bambu,
        "Calatea" to R.drawable.calatea
    )

    //Variable para filtrar plantas en la busqueda
    val filteredPlantas = plantas.filter { it.first.contains(searchText, ignoreCase = true) }


    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        items(filteredPlantas) { (nombre, imagen) ->  // <-- ahora usas el listado filtrado
            PlantItem(nombre, imagen) {
                //Navega a la pantalla de detalle de la planta seleccionada
                navController.navigate("detalle_planta/$nombre")
            }
        }
    }
}

//Tarjeta de cada planta en la lista
@Composable
fun PlantItem(nombre: String, imagen: Int, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() }, // Hace que la tarjeta sea clickeable
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = imagen),
            contentDescription = nombre,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(nombre, fontSize = 18.sp, fontWeight = FontWeight.Medium)
    }
}

//Pantalla de detalle de planta
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetallePlantaScreen(plantaNombre: String, navController: NavHostController, userId: String) {
    val storedUserId by remember { mutableStateOf(userId) }
    val infoPlanta = when (plantaNombre) {
        "Aloe Vera" -> mapOf(
            "nombreCientifico" to stringResource(R.string.aloe_nombre_cientifico),
            "tipo" to stringResource(R.string.aloe_tipo),
            "ubicacion" to stringResource(R.string.aloe_ubicacion),
            "riego" to stringResource(R.string.aloe_riego),
            "luz" to stringResource(R.string.aloe_luz),
            "riegoDetalle" to stringResource(R.string.aloe_riego_detalle),
            "maceta" to stringResource(R.string.aloe_maceta),
            "sustrato" to stringResource(R.string.aloe_sustrato),
            "poda" to stringResource(R.string.aloe_poda),
            "plagas" to stringResource(R.string.aloe_plagas)
        )
        "Albahaca" -> mapOf(
            "nombreCientifico" to stringResource(R.string.albahaca_nombre_cientifico),
            "tipo" to stringResource(R.string.albahaca_tipo),
            "ubicacion" to stringResource(R.string.albahaca_ubicacion),
            "riego" to stringResource(R.string.albahaca_riego),
            "luz" to stringResource(R.string.albahaca_luz),
            "riegoDetalle" to stringResource(R.string.albahaca_riego_detalle),
            "maceta" to stringResource(R.string.albahaca_maceta),
            "sustrato" to stringResource(R.string.albahaca_sustrato),
            "poda" to stringResource(R.string.albahaca_poda),
            "plagas" to stringResource(R.string.albahaca_plagas)
        )
        "Agave" -> mapOf(
            "nombreCientifico" to stringResource(R.string.agave_nombre_cientifico),
            "tipo" to stringResource(R.string.agave_tipo),
            "ubicacion" to stringResource(R.string.agave_ubicacion),
            "riego" to stringResource(R.string.agave_riego),
            "luz" to stringResource(R.string.agave_luz),
            "riegoDetalle" to stringResource(R.string.agave_riego_detalle),
            "maceta" to stringResource(R.string.agave_maceta),
            "sustrato" to stringResource(R.string.agave_sustrato),
            "poda" to stringResource(R.string.agave_poda),
            "plagas" to stringResource(R.string.agave_plagas)
        )
        "Buganvilla" -> mapOf(
            "nombreCientifico" to stringResource(R.string.buganvilla_nombre_cientifico),
            "tipo" to stringResource(R.string.buganvilla_tipo),
            "ubicacion" to stringResource(R.string.buganvilla_ubicacion),
            "riego" to stringResource(R.string.buganvilla_riego),
            "luz" to stringResource(R.string.buganvilla_luz),
            "riegoDetalle" to stringResource(R.string.buganvilla_riego_detalle),
            "maceta" to stringResource(R.string.buganvilla_maceta),
            "sustrato" to stringResource(R.string.buganvilla_sustrato),
            "poda" to stringResource(R.string.buganvilla_poda),
            "plagas" to stringResource(R.string.buganvilla_plagas)
        )
        "Bambú" -> mapOf(
            "nombreCientifico" to stringResource(R.string.bambu_nombre_cientifico),
            "tipo" to stringResource(R.string.bambu_tipo),
            "ubicacion" to stringResource(R.string.bambu_ubicacion),
            "riego" to stringResource(R.string.bambu_riego),
            "luz" to stringResource(R.string.bambu_luz),
            "riegoDetalle" to stringResource(R.string.bambu_riego_detalle),
            "maceta" to stringResource(R.string.bambu_maceta),
            "sustrato" to stringResource(R.string.bambu_sustrato),
            "poda" to stringResource(R.string.bambu_poda),
            "plagas" to stringResource(R.string.bambu_plagas)
        )
        "Calatea" -> mapOf(
            "nombreCientifico" to stringResource(R.string.calatea_nombre_cientifico),
            "tipo" to stringResource(R.string.calatea_tipo),
            "ubicacion" to stringResource(R.string.calatea_ubicacion),
            "riego" to stringResource(R.string.calatea_riego),
            "luz" to stringResource(R.string.calatea_luz),
            "riegoDetalle" to stringResource(R.string.calatea_riego_detalle),
            "maceta" to stringResource(R.string.calatea_maceta),
            "sustrato" to stringResource(R.string.calatea_sustrato),
            "poda" to stringResource(R.string.calatea_poda),
            "plagas" to stringResource(R.string.calatea_plagas)
        )
        else -> emptyMap()
    }


    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding(),
                contentAlignment = Alignment.Center
            ) {
                NavigationScreens(
                    navController,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.secondaryContainer.copy(0.5f))
                        .fillMaxWidth()
                        .height(70.dp), // Usa height en lugar de size para evitar restricciones innecesarias
                    userId = storedUserId
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Botón regresar
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
            }

            // Imagen y nombre
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(30.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer) // Utilizar el color del tema
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.aloe_vera),
                        contentDescription = plantaNombre,
                        modifier = Modifier
                            .size(100.dp)
                            .padding(8.dp)
                            .clip(CircleShape)
                    )
                    Text(
                        plantaNombre,
                        style = MaterialTheme.typography.headlineSmall, // Utilizar tipografía del tema
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary, // Usar color del tema
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .size(30.dp)
                    )
                }
            }

            // Sección Descripción
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant) // Utilizar color del tema
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("📄 Descripción General", style = MaterialTheme.typography.titleMedium)

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("${stringResource(R.string.etiqueta_nombre_cientifico)} ${infoPlanta["nombreCientifico"]}")
                    Text("${stringResource(R.string.etiqueta_tipo)} ${infoPlanta["tipo"]}")
                    Text("${stringResource(R.string.etiqueta_ubicacion)} ${infoPlanta["ubicacion"]}")
                    Text("${stringResource(R.string.etiqueta_riego)} ${infoPlanta["riego"]}")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sección Consejos
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant) // Usar el color del tema
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(stringResource(R.string.seccion_consejos), style = MaterialTheme.typography.titleMedium)

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("${stringResource(R.string.etiqueta_luz)} ${infoPlanta["luz"]}")
                    Text("${stringResource(R.string.etiqueta_riego_detalle)} ${infoPlanta["riegoDetalle"]}")
                    Text("${stringResource(R.string.etiqueta_maceta)} ${infoPlanta["maceta"]}")
                    Text("${stringResource(R.string.etiqueta_sustrato)} ${infoPlanta["sustrato"]}")
                    Text("${stringResource(R.string.etiqueta_poda)} ${infoPlanta["poda"]}")
                    Text("${stringResource(R.string.etiqueta_plagas)} ${infoPlanta["plagas"]}")
                }
            }
        }
    }
}

