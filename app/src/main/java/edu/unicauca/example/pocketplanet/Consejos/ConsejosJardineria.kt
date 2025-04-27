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
fun ConsejosScreen(navController: NavHostController, modifier: Modifier) {
    Scaffold(
        // Barra de navegación inferior centrada
        bottomBar = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                NavigationScreens(
                    navController,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.secondaryContainer.copy(0.5f)) // Color de fondo translúcido
                        .size(width = 400.dp, height = 70.dp) // Tamaño fijo para la barra
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Padding para evitar que el contenido se solape con la barra
                .padding(16.dp) // Padding general de la pantalla
        ) {
            // Botón de regreso
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Título de la pantalla
            Text(
                "Consejos de Jardinería",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Barra de búsqueda
            SearchBar()

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de plantas
            PlantList(navController)
        }
    }
}



//Barra de búsqueda
@Composable
fun SearchBar() {
    var searchText by remember { mutableStateOf("") }

    TextField(
        value = searchText,
        onValueChange = { searchText = it },
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
fun PlantList(navController: NavController) {
    val plantas = listOf(
        "Aloe Vera" to R.drawable.aloe_vera,
        "Albahaca" to R.drawable.albahaca,
        "Agave" to R.drawable.agave,
        "Buganvilla" to R.drawable.calatea,
        "Bambú" to R.drawable.bambu,
        "Calatea" to R.drawable.calatea
    )

    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        items(plantas) { (nombre, imagen) ->
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
fun DetallePlantaScreen(plantaNombre: String, navController: NavHostController) {
    val infoPlanta = when (plantaNombre) {
        "Aloe Vera" -> mapOf(
            "nombreCientifico" to "Aloe barbadensis miller.",
            "tipo" to "Suculenta.",
            "ubicacion" to "Interior y exterior.",
            "riego" to "Cada 2-3 semanas en verano, menos en invierno.",
            "luz" to "Necesita luz indirecta brillante o sol directo parcial.",
            "riegoDetalle" to "Usa el 'método del suelo seco': riega solo cuando la tierra esté completamente seca.",
            "maceta" to "Prefiere macetas de barro con buen drenaje.",
            "sustrato" to "Usa tierra para suculentas o una mezcla de arena y tierra normal.",
            "poda" to "Corta hojas secas o dañadas con una herramienta desinfectada.",
            "plagas" to "Puede atraer cochinillas y ácaros si hay exceso de humedad."
        )
        "Albahaca" -> mapOf(
            "nombreCientifico" to "Ocimum basilicum.",
            "tipo" to "Hierba aromática.",
            "ubicacion" to "Principalmente exterior, con buena luz.",
            "riego" to "Riega cada 2 días en verano, cada 3-4 días en climas más frescos.",
            "luz" to "Luz solar directa al menos 6 horas al día.",
            "riegoDetalle" to "Mantén el sustrato ligeramente húmedo, pero evita el encharcamiento.",
            "maceta" to "Macetas con buen drenaje, preferiblemente de arcilla.",
            "sustrato" to "Sustrato fértil y bien drenado, con compost.",
            "poda" to "Pellizca los tallos regularmente para fomentar un crecimiento frondoso.",
            "plagas" to "Mosca blanca, pulgones y mildiu."
        )
        "Agave" -> mapOf(
            "nombreCientifico" to "Agave americana.",
            "tipo" to "Suculenta.",
            "ubicacion" to "Exterior, zonas soleadas.",
            "riego" to "Muy esporádico, cada 3-4 semanas.",
            "luz" to "Sol directo todo el día.",
            "riegoDetalle" to "Solo riega cuando el sustrato esté completamente seco.",
            "maceta" to "Macetas grandes y pesadas, de barro o concreto.",
            "sustrato" to "Sustrato arenoso, bien drenado.",
            "poda" to "Retira hojas muertas en la base con cuidado.",
            "plagas" to "Cochinillas y hongos si hay exceso de agua."
        )
        "Buganvilla" -> mapOf(
            "nombreCientifico" to "Bougainvillea spp.",
            "tipo" to "Planta trepadora con flores.",
            "ubicacion" to "Exterior, ideal en muros o pérgolas.",
            "riego" to "Una vez por semana, menos en invierno.",
            "luz" to "Sol directo todo el día.",
            "riegoDetalle" to "Prefiere riegos profundos pero espaciados.",
            "maceta" to "Macetas grandes con buen drenaje.",
            "sustrato" to "Ligero, arenoso, con algo de materia orgánica.",
            "poda" to "Pódala después de la floración para darle forma.",
            "plagas" to "Pulgones, cochinillas y araña roja."
        )
        "Bambú" -> mapOf(
            "nombreCientifico" to "Bambusa vulgaris.",
            "tipo" to "Gramínea perenne.",
            "ubicacion" to "Exterior o interior amplio con luz.",
            "riego" to "Frecuente en verano, 3 veces por semana.",
            "luz" to "Luz indirecta brillante o media sombra.",
            "riegoDetalle" to "Mantén el suelo húmedo, no encharcado.",
            "maceta" to "Macetas grandes y profundas.",
            "sustrato" to "Rico en nutrientes, con buen drenaje.",
            "poda" to "Corta cañas secas o débiles en la base.",
            "plagas" to "Ácaros, pulgones y hongos."
        )
        "Calatea" -> mapOf(
            "nombreCientifico" to "Calathea spp.",
            "tipo" to "Planta ornamental de interior.",
            "ubicacion" to "Interior con humedad.",
            "riego" to "2-3 veces por semana en verano.",
            "luz" to "Luz indirecta, nunca sol directo.",
            "riegoDetalle" to "Usa agua sin cal. El sustrato debe estar húmedo pero no encharcado.",
            "maceta" to "Macetas medianas con drenaje.",
            "sustrato" to "Tierra para interior con perlita y turba.",
            "poda" to "Elimina hojas secas desde la base.",
            "plagas" to "Araña roja, cochinilla y hongos si hay poca ventilación."
        )
        else -> emptyMap()
    }


    Scaffold(
        // Barra de navegación inferior centrada
        bottomBar = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                NavigationScreens(
                    navController,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.secondaryContainer.copy(0.5f)) // Fondo translúcido
                        .size(width = 400.dp, height = 70.dp) // Tamaño fijo
                )
            }
        }
    ){ padding ->
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
                colors = CardDefaults.cardColors(containerColor = Color(0xFFA7ECA7))
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
                    Text(plantaNombre, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color.White,
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
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F8F8))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("📄 Descripción General", fontWeight = FontWeight.Bold)

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("🌿 Nombre científico: ${infoPlanta["nombreCientifico"]}")
                    Text("🌱 Tipo de planta: ${infoPlanta["tipo"]}")
                    Text("📍 Ubicación: ${infoPlanta["ubicacion"]}")
                    Text("💧 Frecuencia de riego: ${infoPlanta["riego"]}")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sección Consejos
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F8F8))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("🌟 Consejos Claves", fontWeight = FontWeight.Bold)

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("🔆 Luz: ${infoPlanta["luz"]}")
                    Text("💧 Riego: ${infoPlanta["riegoDetalle"]}")
                    Text("🪴 Maceta: ${infoPlanta["maceta"]}")
                    Text("🌱 Sustrato: ${infoPlanta["sustrato"]}")
                    Text("✂️ Poda: ${infoPlanta["poda"]}")
                    Text("🐛 Plagas comunes: ${infoPlanta["plagas"]}")
                }
            }
        }
    }
}



//Configuración de navegación
/*Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "consejos") {
        composable("consejos") { ConsejosScreen(navController) }
        composable("detalle_planta/{nombre}") { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("nombre") ?: "Desconocida"
            DetallePlantaScreen(plantaNombre = nombre)
        }
    }
}

//Previsualización del diseño
@Preview(showBackground = true)
@Composable
fun PreviewConsejosScreen() {
    val navController = rememberNavController()
    ConsejosScreen(navController)
}

*/