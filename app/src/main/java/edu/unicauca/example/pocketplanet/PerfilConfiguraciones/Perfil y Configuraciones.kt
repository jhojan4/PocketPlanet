package edu.unicauca.example.pocketplanet.PerfilConfiguraciones

// Importaciones necesarias
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import edu.unicauca.example.pocketplanet.Funciones.enviarNotificacion
import edu.unicauca.example.pocketplanet.InicioAplicacion.NavigationScreens
import edu.unicauca.example.pocketplanet.R
import edu.unicauca.example.pocketplanet.ui.theme.ThemeViewModel
import kotlinx.coroutines.launch

// Pantalla de perfil y configuración
@Composable
fun PerfilScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    themeViewModel: ThemeViewModel, // ViewModel para controlar el modo oscuro
    settingsDataStore: SettingsDataStore // DataStore para persistir configuraciones
) {
    // Scope para correr corrutinas desde el Composable
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // Estados que observan el valor en el DataStore (ya persistente)
    val notificationsEnabled by settingsDataStore.notificationsEnabledFlow.collectAsState(initial = true)
    val emailNotifications by settingsDataStore.emailNotificationsFlow.collectAsState(initial = false)

    // Otros estados locales que todavía no persisto (si quiero también podría guardarlos en DataStore)
    var alertNotifications by remember { mutableStateOf(false) }
    var darkMode by remember { mutableStateOf(false) }

    // Lanzador para solicitud de permiso de notificaciones
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                // El permiso fue concedido
                enviarNotificacion(
                    context = context,
                    titulo = "Esta es una notificación de prueba 🎉",
                    mensaje = "Prueba"
                )
            } else {
                // El permiso fue denegado
                Toast.makeText(context, "Permiso de notificación denegado", Toast.LENGTH_SHORT).show()
            }
        }
    )

    Scaffold(
        // Barra de navegación inferior de la app
        bottomBar = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                NavigationScreens(
                    navController,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.secondaryContainer.copy(0.5f))
                        .size(width = 400.dp, height = 70.dp)
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Sección del encabezado del perfil
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color(0xFFA7ECA7),
                        RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
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
                    Button(
                        onClick = { /* Acción para editar perfil (pendiente de implementar) */ },
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Editar Perfil")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sección de Configuración de notificaciones
            Text("Configuración de notificaciones", fontSize = 18.sp, fontWeight = FontWeight.Bold)

            // Switch de Activar/Desactivar notificaciones generales
            SwitchOption(
                text = "Activar/Desactivar notificaciones",
                icon = Icons.Filled.Notifications,
                state = notificationsEnabled
            ) { enabled ->
                scope.launch {
                    settingsDataStore.saveNotificationsEnabled(enabled)
                }
            }

            // Opción para configurar frecuencia de notificaciones
            ConfigOption(
                text = "Frecuencia de notificaciones",
                icon = Icons.Filled.Settings
            ) {
                // Aquí podría abrir una pantalla o diálogo más adelante
            }

            // Switch de Activar/Desactivar notificaciones por correo
            SwitchOption(
                text = "Notificaciones por correo",
                icon = Icons.Filled.Email,
                state = emailNotifications
            ) { enabled ->
                scope.launch {
                    settingsDataStore.saveEmailNotifications(enabled)
                }
            }

            // Switch de Activar/Desactivar alertas (todavía no persiste en DataStore)
            SwitchOption(
                text = "Activar/Desactivar alertas",
                icon = Icons.Filled.Notifications,
                state = alertNotifications
            ) { alertNotifications = it }

            Spacer(modifier = Modifier.height(16.dp))

            // Sección de Temas y apariencia
            Text("Temas y apariencia", fontSize = 18.sp, fontWeight = FontWeight.Bold)

            // Switch de modo oscuro (lo maneja el ThemeViewModel)
            SwitchOption(
                text = "Activar/Desactivar modo oscuro",
                icon = Icons.Filled.DarkMode,
                state = darkMode
            ) {
                darkMode = it
                themeViewModel.toggleTheme()
            }

            // Configuración de idioma
            ConfigOption(
                text = "Idioma",
                icon = Icons.Filled.Language
            ) {
                // Aquí podríamos abrir selector de idiomas más adelante
            }

            // Botón para probar notificación
            Button(
                onClick = {
                    if (notificationsEnabled) {
                        if (ContextCompat.checkSelfPermission(
                                context,
                                android.Manifest.permission.POST_NOTIFICATIONS
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            enviarNotificacion(
                                context = context,
                                titulo = "Esta es una notificación de prueba 🎉",
                                mensaje = "Prueba"
                            )
                        } else {
                            // Solicitar el permiso usando el launcher
                            requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                        }
                    } else {
                        Toast.makeText(context, "Las notificaciones están desactivadas", Toast.LENGTH_SHORT).show()
                    }
                },
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Probar notificación")
            }
        }
    }
}

// Composable genérico para un switch configurable
@Composable
fun SwitchOption(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    state: Boolean,
    onToggle: (Boolean) -> Unit
) {
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

// Composable genérico para opciones que no son switches
@Composable
fun ConfigOption(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
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
    // Simulamos el estado del ViewModel y DataStore en la vista previa
    val themeViewModel = ThemeViewModel() // Puedes usar un ViewModel simulado o mockeado si es necesario
    val settingsDataStore = SettingsDataStore(LocalContext.current)

    // Llamamos a la función que muestra la pantalla de perfil
    PerfilScreen(
        navController = NavHostController(LocalContext.current),
        themeViewModel = themeViewModel,
        settingsDataStore = settingsDataStore
    )
}
