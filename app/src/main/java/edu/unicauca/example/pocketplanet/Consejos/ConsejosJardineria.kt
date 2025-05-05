package edu.unicauca.example.pocketplanet.Consejos

import androidx.annotation.DrawableRes
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.filled.ArrowBack
import edu.unicauca.example.pocketplanet.Funciones.TopScreen
import edu.unicauca.example.pocketplanet.Presentacion.bottonRedondoStateless


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
                        .height(70.dp),
                    userId = userId
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Coloca TopScreen encima
            TopScreen(
                modifier = Modifier
                    .align(Alignment.TopCenter) // Posiciona TopScreen arriba
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, top = 10.dp)
            ) {
                bottonRedondoStateless(
                    onClick = { navController.popBackStack() },
                    icon = Icons.Default.ArrowBack,
                    colors = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.size(40.dp)
                )
            }

            // Barra de búsqueda y título
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp) // Ajusta la distancia desde la parte superior
                    .align(Alignment.TopCenter) // Centra la columna
            ) {
                // Título de la pantalla (centrado)
                Text(
                    "Consejos de Jardinería",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally) // Alinea el título al centro
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Barra de búsqueda (envolver en un Box para alineación)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp) // Añadir un poco de espacio a los lados
                ) {
                    SearchBar(
                        onSearchChanged = { searchText = it },
                        modifier = Modifier.align(Alignment.TopCenter) // Alinea la barra de búsqueda al centro
                    )
                }
            }

            // Contenedor para la lista de plantas
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 140.dp) // Desplaza la columna para que no quede debajo de los elementos superpuestos
                    .padding(horizontal = 16.dp) // Añade un pequeño padding horizontal
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // Lista de plantas
                PlantList(navController, searchText)
            }
        }
    }
}

//Barra de búsqueda
@Composable
fun SearchBar(onSearchChanged: (String) -> Unit, modifier: Modifier) {
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
        "Buganvilla" to R.drawable.buganvilla,
        "Bambú" to R.drawable.bambu,
        "Cactus" to R.drawable.cactus,
        "Calatea" to R.drawable.calatea,
        "Dalia" to R.drawable.dalia,
        "Eucalipto" to R.drawable.eucalipto,
        "Ficus" to R.drawable.ficus,
        "Geranio" to R.drawable.geranio,
        "Hortensia" to R.drawable.hortensia,
        "Jazmín" to R.drawable.jazmin,
        "Lavanda" to R.drawable.lavanda,
        "Menta" to R.drawable.menta,
        "Nandina" to R.drawable.nandina,
        "Orquídea" to R.drawable.orquidea,
        "Petunia" to R.drawable.petunia,
        "Romero" to R.drawable.romero,
        "Salvia" to R.drawable.salvia,
        "Tulipán" to R.drawable.tulipan,
        "Uña de gato" to R.drawable.u_a_de_gato,
        "Violeta africana" to R.drawable.violeta_africana ,
        "Wisteria" to R.drawable.wisteria,
        "Jade" to R.drawable.jade,
        "Yuca" to R.drawable.yuca,
        "Zinnia" to R.drawable.zinnia

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
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = imagen),
                contentDescription = nombre,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = nombre,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
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
        "Cactus" -> mapOf(
            "nombreCientifico" to stringResource(R.string.cactus_nombre_cientifico),
            "tipo" to stringResource(R.string.cactus_tipo),
            "ubicacion" to stringResource(R.string.cactus_ubicacion),
            "riego" to stringResource(R.string.cactus_riego),
            "luz" to stringResource(R.string.cactus_luz),
            "riegoDetalle" to stringResource(R.string.cactus_riego_detalle),
            "maceta" to stringResource(R.string.cactus_maceta),
            "sustrato" to stringResource(R.string.cactus_sustrato),
            "poda" to stringResource(R.string.cactus_poda),
            "plagas" to stringResource(R.string.cactus_plagas)
        )
        "Dalia" -> mapOf(
            "nombreCientifico" to stringResource(R.string.dalia_nombre_cientifico),
            "tipo" to stringResource(R.string.dalia_tipo),
            "ubicacion" to stringResource(R.string.dalia_ubicacion),
            "riego" to stringResource(R.string.dalia_riego),
            "luz" to stringResource(R.string.dalia_luz),
            "riegoDetalle" to stringResource(R.string.dalia_riego_detalle),
            "maceta" to stringResource(R.string.dalia_maceta),
            "sustrato" to stringResource(R.string.dalia_sustrato),
            "poda" to stringResource(R.string.dalia_poda),
            "plagas" to stringResource(R.string.dalia_plagas)
        )
        "Eucalipto" ->  mapOf(
            "nombreCientifico" to stringResource(R.string.eucalipto_nombre_cientifico),
            "tipo" to stringResource(R.string.eucalipto_tipo),
            "ubicacion" to stringResource(R.string.eucalipto_ubicacion),
            "riego" to stringResource(R.string.eucalipto_riego),
            "luz" to stringResource(R.string.eucalipto_luz),
            "riegoDetalle" to stringResource(R.string.eucalipto_riego_detalle),
            "maceta" to stringResource(R.string.eucalipto_maceta),
            "sustrato" to stringResource(R.string.eucalipto_sustrato),
            "poda" to stringResource(R.string.eucalipto_poda),
            "plagas" to stringResource(R.string.eucalipto_plagas)
        )
        "Ficus" ->  mapOf(
            "nombreCientifico" to stringResource(R.string.ficus_nombre_cientifico),
            "tipo" to stringResource(R.string.ficus_tipo),
            "ubicacion" to stringResource(R.string.ficus_ubicacion),
            "riego" to stringResource(R.string.ficus_riego),
            "luz" to stringResource(R.string.ficus_luz),
            "riegoDetalle" to stringResource(R.string.ficus_riego_detalle),
            "maceta" to stringResource(R.string.ficus_maceta),
            "sustrato" to stringResource(R.string.ficus_sustrato),
            "poda" to stringResource(R.string.ficus_poda),
            "plagas" to stringResource(R.string.ficus_plagas)
        )
        "Geranio" -> mapOf(
            "nombreCientifico" to stringResource(R.string.geranio_nombre_cientifico),
            "tipo" to stringResource(R.string.geranio_tipo),
            "ubicacion" to stringResource(R.string.geranio_ubicacion),
            "riego" to stringResource(R.string.geranio_riego),
            "luz" to stringResource(R.string.geranio_luz),
            "riegoDetalle" to stringResource(R.string.geranio_riego_detalle),
            "maceta" to stringResource(R.string.geranio_maceta),
            "sustrato" to stringResource(R.string.geranio_sustrato),
            "poda" to stringResource(R.string.geranio_poda),
            "plagas" to stringResource(R.string.geranio_plagas)
        )
        "Hortensia" -> mapOf(
            "nombreCientifico" to stringResource(R.string.hortensia_nombre_cientifico),
            "tipo" to stringResource(R.string.hortensia_tipo),
            "ubicacion" to stringResource(R.string.hortensia_ubicacion),
            "riego" to stringResource(R.string.hortensia_riego),
            "luz" to stringResource(R.string.hortensia_luz),
            "riegoDetalle" to stringResource(R.string.hortensia_riego_detalle),
            "maceta" to stringResource(R.string.hortensia_maceta),
            "sustrato" to stringResource(R.string.hortensia_sustrato),
            "poda" to stringResource(R.string.hortensia_poda),
            "plagas" to stringResource(R.string.hortensia_plagas)
        )
        "Jazmín" -> mapOf(
            "nombreCientifico" to stringResource(R.string.jazmin_nombre_cientifico),
            "tipo" to stringResource(R.string.jazmin_tipo),
            "ubicacion" to stringResource(R.string.jazmin_ubicacion),
            "riego" to stringResource(R.string.jazmin_riego),
            "luz" to stringResource(R.string.jazmin_luz),
            "riegoDetalle" to stringResource(R.string.jazmin_riego_detalle),
            "maceta" to stringResource(R.string.jazmin_maceta),
            "sustrato" to stringResource(R.string.jazmin_sustrato),
            "poda" to stringResource(R.string.jazmin_poda),
            "plagas" to stringResource(R.string.jazmin_plagas)
        )
        "Lavanda" -> mapOf(
            "nombreCientifico" to stringResource(R.string.lavanda_nombre_cientifico),
            "tipo" to stringResource(R.string.lavanda_tipo),
            "ubicacion" to stringResource(R.string.lavanda_ubicacion),
            "riego" to stringResource(R.string.lavanda_riego),
            "luz" to stringResource(R.string.lavanda_luz),
            "riegoDetalle" to stringResource(R.string.lavanda_riego_detalle),
            "maceta" to stringResource(R.string.lavanda_maceta),
            "sustrato" to stringResource(R.string.lavanda_sustrato),
            "poda" to stringResource(R.string.lavanda_poda),
            "plagas" to stringResource(R.string.lavanda_plagas)
        )
        "Menta" -> mapOf(
            "nombreCientifico" to stringResource(R.string.menta_nombre_cientifico),
            "tipo" to stringResource(R.string.menta_tipo),
            "ubicacion" to stringResource(R.string.menta_ubicacion),
            "riego" to stringResource(R.string.menta_riego),
            "luz" to stringResource(R.string.menta_luz),
            "riegoDetalle" to stringResource(R.string.menta_riego_detalle),
            "maceta" to stringResource(R.string.menta_maceta),
            "sustrato" to stringResource(R.string.menta_sustrato),
            "poda" to stringResource(R.string.menta_poda),
            "plagas" to stringResource(R.string.menta_plagas)
        )
        "Nandina" -> mapOf(
            "nombreCientifico" to stringResource(R.string.nandina_nombre_cientifico),
            "tipo" to stringResource(R.string.nandina_tipo),
            "ubicacion" to stringResource(R.string.nandina_ubicacion),
            "riego" to stringResource(R.string.nandina_riego),
            "luz" to stringResource(R.string.nandina_luz),
            "riegoDetalle" to stringResource(R.string.nandina_riego_detalle),
            "maceta" to stringResource(R.string.nandina_maceta),
            "sustrato" to stringResource(R.string.nandina_sustrato),
            "poda" to stringResource(R.string.nandina_poda),
            "plagas" to stringResource(R.string.nandina_plagas)
        )
        "Orquídea" -> mapOf(
            "nombreCientifico" to stringResource(R.string.orquidea_nombre_cientifico),
            "tipo" to stringResource(R.string.orquidea_tipo),
            "ubicacion" to stringResource(R.string.orquidea_ubicacion),
            "riego" to stringResource(R.string.orquidea_riego),
            "luz" to stringResource(R.string.orquidea_luz),
            "riegoDetalle" to stringResource(R.string.orquidea_riego_detalle),
            "maceta" to stringResource(R.string.orquidea_maceta),
            "sustrato" to stringResource(R.string.orquidea_sustrato),
            "poda" to stringResource(R.string.orquidea_poda),
            "plagas" to stringResource(R.string.orquidea_plagas)
        )
        "Petunia" -> mapOf(
            "nombreCientifico" to stringResource(R.string.petunia_nombre_cientifico),
            "tipo" to stringResource(R.string.petunia_tipo),
            "ubicacion" to stringResource(R.string.petunia_ubicacion),
            "riego" to stringResource(R.string.petunia_riego),
            "luz" to stringResource(R.string.petunia_luz),
            "riegoDetalle" to stringResource(R.string.petunia_riego_detalle),
            "maceta" to stringResource(R.string.petunia_maceta),
            "sustrato" to stringResource(R.string.petunia_sustrato),
            "poda" to stringResource(R.string.petunia_poda),
            "plagas" to stringResource(R.string.petunia_plagas)
        )
        "Romero" -> mapOf(
            "nombreCientifico" to stringResource(R.string.romero_nombre_cientifico),
            "tipo" to stringResource(R.string.romero_tipo),
            "ubicacion" to stringResource(R.string.romero_ubicacion),
            "riego" to stringResource(R.string.romero_riego),
            "luz" to stringResource(R.string.romero_luz),
            "riegoDetalle" to stringResource(R.string.romero_riego_detalle),
            "maceta" to stringResource(R.string.romero_maceta),
            "sustrato" to stringResource(R.string.romero_sustrato),
            "poda" to stringResource(R.string.romero_poda),
            "plagas" to stringResource(R.string.romero_plagas)
        )
        "Salvia" -> mapOf(
            "nombreCientifico" to stringResource(R.string.salvia_nombre_cientifico),
            "tipo" to stringResource(R.string.salvia_tipo),
            "ubicacion" to stringResource(R.string.salvia_ubicacion),
            "riego" to stringResource(R.string.salvia_riego),
            "luz" to stringResource(R.string.salvia_luz),
            "riegoDetalle" to stringResource(R.string.salvia_riego_detalle),
            "maceta" to stringResource(R.string.salvia_maceta),
            "sustrato" to stringResource(R.string.salvia_sustrato),
            "poda" to stringResource(R.string.salvia_poda),
            "plagas" to stringResource(R.string.salvia_plagas)
        )
        "Tulipán" -> mapOf(
            "nombreCientifico" to stringResource(R.string.tulipan_nombre_cientifico),
            "tipo" to stringResource(R.string.tulipan_tipo),
            "ubicacion" to stringResource(R.string.tulipan_ubicacion),
            "riego" to stringResource(R.string.tulipan_riego),
            "luz" to stringResource(R.string.tulipan_luz),
            "riegoDetalle" to stringResource(R.string.tulipan_riego_detalle),
            "maceta" to stringResource(R.string.tulipan_maceta),
            "sustrato" to stringResource(R.string.tulipan_sustrato),
            "poda" to stringResource(R.string.tulipan_poda),
            "plagas" to stringResource(R.string.tulipan_plagas)
        )
        "Uña de gato" -> mapOf(
            "nombreCientifico" to stringResource(R.string.unadegato_nombre_cientifico),
            "tipo" to stringResource(R.string.unadegato_tipo),
            "ubicacion" to stringResource(R.string.unadegato_ubicacion),
            "riego" to stringResource(R.string.unadegato_riego),
            "luz" to stringResource(R.string.unadegato_luz),
            "riegoDetalle" to stringResource(R.string.unadegato_riego_detalle),
            "maceta" to stringResource(R.string.unadegato_maceta),
            "sustrato" to stringResource(R.string.unadegato_sustrato),
            "poda" to stringResource(R.string.unadegato_poda),
            "plagas" to stringResource(R.string.unadegato_plagas)
        )
        "Violeta africana" -> mapOf(
            "nombreCientifico" to stringResource(R.string.violetaafricana_nombre_cientifico),
            "tipo" to stringResource(R.string.violetaafricana_tipo),
            "ubicacion" to stringResource(R.string.violetaafricana_ubicacion),
            "riego" to stringResource(R.string.violetaafricana_riego),
            "luz" to stringResource(R.string.violetaafricana_luz),
            "riegoDetalle" to stringResource(R.string.violetaafricana_riego_detalle),
            "maceta" to stringResource(R.string.violetaafricana_maceta),
            "sustrato" to stringResource(R.string.violetaafricana_sustrato),
            "poda" to stringResource(R.string.violetaafricana_poda),
            "plagas" to stringResource(R.string.violetaafricana_plagas)
        )
        "Wisteria" -> mapOf(
            "nombreCientifico" to stringResource(R.string.wisteria_nombre_cientifico),
            "tipo" to stringResource(R.string.wisteria_tipo),
            "ubicacion" to stringResource(R.string.wisteria_ubicacion),
            "riego" to stringResource(R.string.wisteria_riego),
            "luz" to stringResource(R.string.wisteria_luz),
            "riegoDetalle" to stringResource(R.string.wisteria_riego_detalle),
            "maceta" to stringResource(R.string.wisteria_maceta),
            "sustrato" to stringResource(R.string.wisteria_sustrato),
            "poda" to stringResource(R.string.wisteria_poda),
            "plagas" to stringResource(R.string.wisteria_plagas)
        )
        "Jade" -> mapOf(
            "nombreCientifico" to stringResource(R.string.jade_nombre_cientifico),
            "tipo" to stringResource(R.string.jade_tipo),
            "ubicacion" to stringResource(R.string.jade_ubicacion),
            "riego" to stringResource(R.string.jade_riego),
            "luz" to stringResource(R.string.jade_luz),
            "riegoDetalle" to stringResource(R.string.jade_riego_detalle),
            "maceta" to stringResource(R.string.jade_maceta),
            "sustrato" to stringResource(R.string.jade_sustrato),
            "poda" to stringResource(R.string.jade_poda),
            "plagas" to stringResource(R.string.jade_plagas)
        )
        "Yuca" -> mapOf(
            "nombreCientifico" to stringResource(R.string.yuca_nombre_cientifico),
            "tipo" to stringResource(R.string.yuca_tipo),
            "ubicacion" to stringResource(R.string.yuca_ubicacion),
            "riego" to stringResource(R.string.yuca_riego),
            "luz" to stringResource(R.string.yuca_luz),
            "riegoDetalle" to stringResource(R.string.yuca_riego_detalle),
            "maceta" to stringResource(R.string.yuca_maceta),
            "sustrato" to stringResource(R.string.yuca_sustrato),
            "poda" to stringResource(R.string.yuca_poda),
            "plagas" to stringResource(R.string.yuca_plagas)
        )
        "Zinnia" -> mapOf(
            "nombreCientifico" to stringResource(R.string.zinnia_nombre_cientifico),
            "tipo" to stringResource(R.string.zinnia_tipo),
            "ubicacion" to stringResource(R.string.zinnia_ubicacion),
            "riego" to stringResource(R.string.zinnia_riego),
            "luz" to stringResource(R.string.zinnia_luz),
            "riegoDetalle" to stringResource(R.string.zinnia_riego_detalle),
            "maceta" to stringResource(R.string.zinnia_maceta),
            "sustrato" to stringResource(R.string.zinnia_sustrato),
            "poda" to stringResource(R.string.zinnia_poda),
            "plagas" to stringResource(R.string.zinnia_plagas)
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
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(0.5f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp), // espaciado uniforme
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = getImagenPlanta(plantaNombre)),
                        contentDescription = plantaNombre,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = plantaNombre,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
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

@DrawableRes
fun getImagenPlanta(plantaNombre: String): Int {
    return when (plantaNombre) {
        "Aloe Vera" -> R.drawable.aloe_vera
        "Albahaca" -> R.drawable.albahaca
        "Agave" -> R.drawable.agave
        "Buganvilla" -> R.drawable.buganvilla
        "Bambú" -> R.drawable.bambu
        "Calatea" -> R.drawable.calatea
        "Cactus" -> R.drawable.cactus
        "Dalia" -> R.drawable.dalia
        "Eucalipto" -> R.drawable.eucalipto
        "Ficus" -> R.drawable.ficus
        "Geranio" -> R.drawable.geranio
        "Hortensia" -> R.drawable.hortensia
        "Jazmín" -> R.drawable.jazmin
        "Lavanda" -> R.drawable.lavanda
        "Menta" -> R.drawable.menta
        "Nandina" -> R.drawable.nandina
        "Orquídea" -> R.drawable.orquidea
        "Petunia" -> R.drawable.petunia
        "Romero" -> R.drawable.romero
        "Salvia" -> R.drawable.salvia
        "Tulipán" -> R.drawable.tulipan
        "Uña de gato" -> R.drawable.u_a_de_gato
        "Violeta africana" -> R.drawable.violeta_africana
        "Wisteria" -> R.drawable.wisteria
        "Jade" -> R.drawable.jade
        "Yuca" -> R.drawable.yuca
        "Zinnia" -> R.drawable.zinnia
        else -> R.drawable.logo  // Imagen por defecto
    }
}


