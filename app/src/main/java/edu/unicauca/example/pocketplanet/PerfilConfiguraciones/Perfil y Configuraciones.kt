package edu.unicauca.example.pocketplanet.PerfilConfiguraciones

// Importaciones necesarias
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.FirebaseApp
import edu.unicauca.example.pocketplanet.Funciones.enviarNotificacion
import edu.unicauca.example.pocketplanet.InicioAplicacion.NavigationScreens
import edu.unicauca.example.pocketplanet.R
import edu.unicauca.example.pocketplanet.ui.theme.ThemeViewModel
import kotlinx.coroutines.launch

object PerfilScreenData {
    var originalEmail: String = "correo@ejemplo.com"
}

//malo

@Composable
fun PerfilScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    userId2: String,
    themeViewModel: ThemeViewModel,
    settingsDataStore: SettingsDataStore,
    languageViewModel: LanguageViewModel// Agregar el viewModel para el idioma
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val notificationsEnabled by settingsDataStore.notificationsEnabledFlow.collectAsState(initial = true)
    val emailNotifications by settingsDataStore.emailNotificationsFlow.collectAsState(initial = false)

    var alertNotifications by remember { mutableStateOf(false) }
    var darkMode by remember { mutableStateOf(false) }

    // Estado para cambiar idioma
    val currentLanguage by languageViewModel.currentLanguage.collectAsState() // Aquí recogemos el idioma actual

    // Estados para editar perfil
    var showEditDialog by remember { mutableStateOf(false) }
    var userName by remember { mutableStateOf("Nombre Usuario") }
    var email by remember { mutableStateOf("correo@ejemplo.com") }

    // Guardamos el correo original al entrar a la pantalla
    PerfilScreenData.originalEmail = email

    // 🔥 Cargar nombre y correo del usuario desde Firestore
    LaunchedEffect(Unit) {
        // Usamos la base de datos "database-pocketplanet"
        val db = FirebaseFirestore.getInstance(FirebaseApp.getInstance("database-pocketplanet"))
        //val database = FirebaseDatabase.getInstance()
        val usersRef = db.collection("users")

        usersRef.whereEqualTo("email", PerfilScreenData.originalEmail)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val userDoc = documents.documents[0]
                    userName = userDoc.getString("name") ?: "Nombre Usuario"
                    email = userDoc.getString("email") ?: "correo@ejemplo.com"
                    Toast.makeText(context, "Usuario cargado: $userName", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Error al cargar usuario: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                enviarNotificacion(
                    context = context,
                    titulo = "Esta es una notificación de prueba 🎉",
                    mensaje = "Prueba"
                )
            } else {
                Toast.makeText(context, "Permiso de notificación denegado", Toast.LENGTH_SHORT).show()
            }
        }
    )

    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                NavigationScreens(
                    navController,
                    userId2,
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
            // Sección del perfil
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
                    Text(userName, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(email, fontSize = 16.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { showEditDialog = true },
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Editar Perfil")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Configuración de notificaciones", fontSize = 18.sp, fontWeight = FontWeight.Bold)

            SwitchOption(
                text = "Activar/Desactivar notificaciones",
                icon = Icons.Filled.Notifications,
                state = notificationsEnabled
            ) { enabled ->
                scope.launch {
                    settingsDataStore.saveNotificationsEnabled(enabled)
                }
            }

            ConfigOption(
                text = "Frecuencia de notificaciones",
                icon = Icons.Filled.Settings
            ) { }

            SwitchOption(
                text = "Notificaciones por correo",
                icon = Icons.Filled.Email,
                state = emailNotifications
            ) { enabled ->
                scope.launch {
                    settingsDataStore.saveEmailNotifications(enabled)
                }
            }

            SwitchOption(
                text = "Activar/Desactivar alertas",
                icon = Icons.Filled.Notifications,
                state = alertNotifications
            ) { alertNotifications = it }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Temas y apariencia", fontSize = 18.sp, fontWeight = FontWeight.Bold)

            SwitchOption(
                text = "Activar/Desactivar modo oscuro",
                icon = Icons.Filled.DarkMode,
                state = darkMode
            ) {
                darkMode = it
                themeViewModel.toggleTheme()
            }

            // Mostrar idioma actual
            ConfigOption(
                text = "Idioma (Actual: $currentLanguage)",  // Mostrar idioma actual
                icon = Icons.Filled.Language
            ) {
                // Aquí cambiamos el idioma cuando se hace clic
                languageViewModel.cambiarIdioma(if (currentLanguage == "en") "es" else "en")
            }

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

            if (showEditDialog) {
                AlertDialog(
                    onDismissRequest = { showEditDialog = false },
                    title = { Text("Editar Perfil") },
                    text = {
                        Column {
                            OutlinedTextField(
                                value = userName,
                                onValueChange = { userName = it },
                                label = { Text("Nombre de usuario") }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = email,
                                onValueChange = { email = it },
                                label = { Text("Correo electrónico") }
                            )
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                actualizarPerfilEnFirestore(
                                    nuevoNombre = userName,
                                    nuevoCorreo = email,
                                    context = context,
                                    onSuccess = {
                                        showEditDialog = false
                                        PerfilScreenData.originalEmail = email
                                    }
                                )
                            }
                        ) {
                            Text("Guardar")
                        }
                    },
                    dismissButton = {
                        OutlinedButton(onClick = { showEditDialog = false }) {
                            Text("Cancelar")
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón de Cerrar Sesión
            Button(
                onClick = {
                    FirebaseAuth.getInstance().signOut()
                    navController.navigate("login_screen") {
                        popUpTo("home_screen") { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Cerrar sesión", color = Color.White)
            }
        }
    }
}

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

fun actualizarPerfilEnFirestore(
    nuevoNombre: String,
    nuevoCorreo: String,
    context: android.content.Context,
    onSuccess: () -> Unit
) {
    val db = FirebaseFirestore.getInstance(FirebaseApp.getInstance("database-pocketplanet"))
    val usersRef = db.collection("users")

    usersRef.whereEqualTo("email", nuevoCorreo)
        .get()
        .addOnSuccessListener { documents ->
            if (documents.isEmpty) {
                Toast.makeText(context, "Correo no registrado", Toast.LENGTH_SHORT).show()
            } else {
                val userDoc = documents.documents[0]
                userDoc.reference.update("name", nuevoNombre, "email", nuevoCorreo)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Perfil actualizado correctamente", Toast.LENGTH_SHORT).show()
                        onSuccess()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(context, "Error al actualizar el perfil: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }
}
