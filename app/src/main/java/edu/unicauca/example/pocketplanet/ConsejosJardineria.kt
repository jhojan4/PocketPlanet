package edu.unicauca.example.pocketplanet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import edu.unicauca.example.pocketplanet.Funciones.BackGroundPocketPlanetInicial
import edu.unicauca.example.pocketplanet.Registro.Card_Registro
import edu.unicauca.example.pocketplanet.ui.theme.PocketPlanetTheme


class ConsejosJardineria : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PocketPlanetTheme {
                // Configuración de la navegación en la app
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = { BottomNavigationBar(navController) }
                ) { paddingValues ->
                    Box(modifier = Modifier.padding(paddingValues)) {
                        ConsejosScreen(navController)
                    }
                }
            }
        }
    }
}

//Pantalla principal de consejos de jardinería
@Composable
fun ConsejosScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Fondo con BackGroundPocketPlanetInicial
        Box(modifier = Modifier.fillMaxSize()) {
            BackGroundPocketPlanetInicial()  // Coloca el fondo que ya has definido
            // Contenido de la pantalla
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp) // Aseguramos que el contenido no quede pegado al borde
            ) {
                //Botón de regreso
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Consejos de Jardinería", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }

                //Barra de búsqueda
                SearchBar()

                //Lista de plantas
                PlantList(navController)
            }
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

//Barra de navegación inferior
@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.secondaryContainer
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Inicio") },
            label = { Text("Inicio") },
            selected = false,
            onClick = { navController.navigate("inicio") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Notifications, contentDescription = "Notificaciones") },
            label = { Text("Notificaciones") },
            selected = false,
            onClick = { navController.navigate("notificaciones") }
        )
    }
}

//Pantalla de detalle de planta
@Composable
fun DetallePlantaScreen(plantaNombre: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Detalles de $plantaNombre",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("información sobre el cuidado de la planta.") //Se puede mejorar esto
    }
}

//Configuración de navegación
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "consejos") {
        composable("consejos") { ConsejosScreen(navController) }
        composable("inicio") { /* Aquí va la pantalla de inicio */ }
        composable("notificaciones") { /* Aquí va la pantalla de notificaciones */ }
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

@Composable
fun backgroundRegistro(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxWidth()) {
        BackGroundPocketPlanetInicial()
        Card_Registro(modifier.align(Alignment.Center))
    }
}
