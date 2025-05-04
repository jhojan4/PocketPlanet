package edu.unicauca.example.pocketplanet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.runtime.collectAsState
import edu.unicauca.example.pocketplanet.Funciones.createNotificationChannel
import edu.unicauca.example.pocketplanet.PerfilConfiguraciones.SettingsDataStore
import edu.unicauca.example.pocketplanet.PerfilConfiguraciones.ThemeViewModelFactory
import edu.unicauca.example.pocketplanet.ui.theme.PocketPlanetTheme
import edu.unicauca.example.pocketplanet.ui.theme.ThemeViewModel

class MainActivity : ComponentActivity() {

    private val themeRepository by lazy { SettingsDataStore(context = applicationContext) } // Crea la instancia del repositorio
    private val themeViewModel: ThemeViewModel by viewModels {
        ThemeViewModelFactory(themeRepository) // Asegúrate de usar el Factory que pasará el repositorio
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel(this)
        enableEdgeToEdge()
        setContent {
            val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

            PocketPlanetTheme(darkTheme = isDarkTheme) { // pasamos si está oscuro o no
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavigation(themeViewModel) // mandamos el ViewModel a la navegación
                }
            }
        }
    }
}

