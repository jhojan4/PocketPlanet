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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
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
import edu.unicauca.example.pocketplanet.Funciones.enviarNotificacion
import edu.unicauca.example.pocketplanet.InicioAplicacion.NavigationScreens
import edu.unicauca.example.pocketplanet.R
import edu.unicauca.example.pocketplanet.Session.UserSessionViewModel
import edu.unicauca.example.pocketplanet.ui.theme.ThemeViewModel
import kotlinx.coroutines.launch
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.unicauca.example.pocketplanet.Funciones.TopScreen
import edu.unicauca.example.pocketplanet.Presentacion.bottonRedondoStateless
import edu.unicauca.example.pocketplanet.Screens

@Composable
fun PerfilScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    themeViewModel: ThemeViewModel,
    settingsDataStore: SettingsDataStore,
    languageViewModel: LanguageViewModel, // Agregar el viewModel para el idioma
    perfilViewModel: PerfilViewModel = viewModel(), // Usamos el PerfilViewModel
    userId: String
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    // ahora puedes usar userId
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val notificationsEnabled by settingsDataStore.notificationsEnabledFlow.collectAsState(initial = true)


    val darkMode by themeViewModel.isDarkTheme.collectAsState()

    // Estado para cambiar idioma
    val currentLanguage by languageViewModel.currentLanguage.collectAsState() // Aquí recogemos el idioma actual

    // Estados para editar perfil
    var showEditDialog by remember { mutableStateOf(false) }

    // Obtenemos los datos del usuario del ViewModel
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }





    // Cargar perfil si el userId no está vacío
    LaunchedEffect(userId) {
        userId?.let { safeUserId ->
            perfilViewModel.cargarPerfil(
                onSuccess = {
                    nombre = perfilViewModel.userName
                    correo = perfilViewModel.email
                },
                userId = safeUserId,
                onError = { errorMsg ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("❌ $errorMsg")
                    }
                }
            )
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
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
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
                .verticalScroll(rememberScrollState()), // Habilita el scroll vertical
                //.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) { // Botón de volver
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

            // Sección del perfil
            Box(
                /*modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.secondaryContainer.copy(0.5f),
                        RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)
                    ),
                contentAlignment = Alignment.Center*/
            ) {
                TopScreen()
                Column(
                    modifier = Modifier.padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Foto de perfil",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
                        Text(text = "Nombre: $nombre", style = MaterialTheme.typography.bodyLarge)
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Email, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
                        Text(text = "Correo: $correo", style = MaterialTheme.typography.bodyLarge)
                    }
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

            Spacer(modifier = Modifier.height(16.dp))

            Text("Temas y apariencia", fontSize = 18.sp, fontWeight = FontWeight.Bold)

            SwitchOption(
                text = "Activar/Desactivar modo oscuro",
                icon = Icons.Filled.DarkMode,
                state = darkMode
            ) {
                themeViewModel.setDarkTheme(it)
               //themeViewModel.toggleTheme()
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
                                value = nombre,
                                onValueChange = { nombre = it },
                                label = { Text("Nombre de usuario") }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = correo,
                                onValueChange = { correo = it },
                                label = { Text("Correo electrónico") }
                            )
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                perfilViewModel.actualizarPerfil(
                                    userId = userId,
                                    nuevoNombre = nombre,
                                    nuevoCorreo = correo,
                                    onSuccess = {
                                        showEditDialog = false
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar("✅ Usuario editado exitosamente")
                                        }
                                    },
                                    onError = { error ->
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar("❌ Error: $error")
                                        }
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
                    // Navegar a la pantalla de inicio de sesión
                    navController.navigate(Screens.InicioSesionScreen.name) {
                        popUpTo(Screens.InicioSesionScreen.name) { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Cerrar sesión")
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
