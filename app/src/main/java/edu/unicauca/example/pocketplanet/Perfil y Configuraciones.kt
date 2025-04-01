package edu.unicauca.example.pocketplanet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
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
import androidx.navigation.compose.rememberNavController
import edu.unicauca.example.pocketplanet.ui.theme.PocketPlanetTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon


class PerfilConfiguracion : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PocketPlanetTheme {
                val navController = rememberNavController()
                Scaffold(
                    topBar = { TopBar(navController) }
                ) { paddingValues ->
                    Box(modifier = Modifier.padding(paddingValues)) {
                        PerfilScreen(navController)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)//Esta linea evita que TopAppBarDefaults de error
@Composable
fun TopBar(navController: NavController) {
    androidx.compose.material3.TopAppBar(
        title = { Text("Perfil y Configuración") },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFA7ECA7)
        )
    )
}


@Composable
fun PerfilScreen(navController: NavController) {
    var notificationsEnabled by remember { mutableStateOf(true) }
    var emailNotifications by remember { mutableStateOf(false) }
    var alertNotifications by remember { mutableStateOf(false) }
    var darkMode by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFA7ECA7), RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.logo),//foto usuario
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Nombre Usuario", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text("Correo", fontSize = 16.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { /* Editar perfil */ }, shape = RoundedCornerShape(8.dp)) {
                    Text("Editar Perfil")
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Configuración de notificaciones", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        SwitchOption("Activar/Desactivar notificaciones", Icons.Filled.Notifications, notificationsEnabled) {
            notificationsEnabled = it
        }
        ConfigOption("Frecuencia de notificaciones", Icons.Filled.Settings) {}
        SwitchOption("Notificaciones por correo", Icons.Filled.Email, emailNotifications) {
            emailNotifications = it
        }
        SwitchOption("Activar/Desactivar alertas", Icons.Filled.Notifications, alertNotifications) {
            alertNotifications = it
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Temas y apariencia", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        SwitchOption("Activar/Desactivar modo oscuro", Icons.Filled.DarkMode, darkMode) {
            darkMode = it
        }
        ConfigOption("Idioma", Icons.Filled.Language) {}
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /* Cerrar sesión */ }, shape = RoundedCornerShape(8.dp)) {
            Icon(Icons.Filled.Logout, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Cerrar Sesión")
        }
    }
}

@Composable
fun SwitchOption(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector, state: Boolean, onToggle: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text, fontSize = 16.sp, modifier = Modifier.weight(1f))
        Switch(checked = state, onCheckedChange = { onToggle(it) })
    }
}

@Composable
fun ConfigOption(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text, fontSize = 16.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPerfilScreen() {
    val navController = rememberNavController()
    PerfilScreen(navController)
}



