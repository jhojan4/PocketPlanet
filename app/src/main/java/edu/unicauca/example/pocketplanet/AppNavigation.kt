package edu.unicauca.example.pocketplanet


import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.unicauca.example.pocketplanet.Agregar_Planta.AgregarPlanta
import edu.unicauca.example.pocketplanet.Consejos.ConsejosScreen
import edu.unicauca.example.pocketplanet.Consejos.DetallePlantaScreen
import edu.unicauca.example.pocketplanet.Estadisticas.StatisticsScreen
import edu.unicauca.example.pocketplanet.InicioAplicacion.Screen_Inicio_Aplicacion
import edu.unicauca.example.pocketplanet.Inicio_Sesion.Inicio_Sesio
import edu.unicauca.example.pocketplanet.PerfilConfiguraciones.LanguageViewModel
import edu.unicauca.example.pocketplanet.PerfilConfiguraciones.PerfilScreen
import edu.unicauca.example.pocketplanet.PerfilConfiguraciones.PerfilViewModel
import edu.unicauca.example.pocketplanet.PerfilConfiguraciones.SettingsDataStore
import edu.unicauca.example.pocketplanet.Presentacion.backgroundPresentacion
//import edu.unicauca.example.pocketplanet.Presentacion
//import edu.unicauca.example.pocketplanet.Registro.Registro
//import edu.unicauca.example.pocketplanet.Inicio_Sesion.Inicio_Sesion
//import edu.unicauca.example.pocketplanet.Presentacion.backgroundPresentacion
import edu.unicauca.example.pocketplanet.Registro.backgroundRegistro
import edu.unicauca.example.pocketplanet.ui.theme.ThemeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(themeViewModel: ThemeViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.PresentacionScreen.name) {

        composable(Screens.PresentacionScreen.name) {
            backgroundPresentacion(navController)
        }

        composable(Screens.InicioSesionScreen.name) {
            Inicio_Sesio(navController, modifier = Modifier)
        }

        composable(Screens.RegistroScreen.name) {
            backgroundRegistro(navController)
        }

        /*composable(Screens.InicioAplicacion.name) {
            Screen_Inicio_Aplicacion(navController)
        }*/
        composable("${Screens.InicioAplicacion.name}/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            Screen_Inicio_Aplicacion(navController = navController, userId = userId)
        }

        composable("${Screens.ConsejosScreen.name}/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            ConsejosScreen(
                navController = navController,
                modifier = Modifier,
                userId = userId
            )
        }



        composable("${Screens.ConfiguracionesScreen.name}/{userId}") { backStackEntry ->
            val context = LocalContext.current // pido el context
            val settingsDataStore = SettingsDataStore(context) // creo el objeto bien
            // Obtengo la instancia del LanguageViewModel
            val LanguageViewModel: LanguageViewModel = viewModel()
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            PerfilScreen(
                navController = navController,
                modifier = Modifier,
                userId = userId,
                themeViewModel = themeViewModel,
                settingsDataStore = settingsDataStore,
                languageViewModel = LanguageViewModel
            )
        }


        composable(Screens.EstadisticasScreen.name) {
            StatisticsScreen(navController, modifier = Modifier)
        }

        //Esta ruta para mostrar la pantalla de detalles por planta
        composable("detalle_planta/{nombre}") { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("nombre") ?: "Desconocida"
            DetallePlantaScreen(plantaNombre = nombre, navController)
        }

        // Nueva ruta para la pantalla AgregarPlanta
        composable("${Screens.AgregarPlantaScreen.name}/{userId}") {backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            AgregarPlanta(navController = navController,userId)
        }


    }
}

enum class Screens(){
    PresentacionScreen,
    InicioSesionScreen,
    RegistroScreen,
    InicioAplicacion,
    ConsejosScreen,
    InicioScreen,
    NotificacionesScreen,
    ConfiguracionesScreen,
    EstadisticasScreen,
    DetallePlantaScreen,
    AgregarPlantaScreen
}