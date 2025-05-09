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
import edu.unicauca.example.pocketplanet.Informacion_Planta.Informacion_Planta
import edu.unicauca.example.pocketplanet.InicioAplicacion.Screen_Inicio_Aplicacion
import edu.unicauca.example.pocketplanet.Inicio_Sesion.Inicio_Sesio
import edu.unicauca.example.pocketplanet.Notificaciones.AlertConfigScreen
import edu.unicauca.example.pocketplanet.PerfilConfiguraciones.LanguageViewModel
import edu.unicauca.example.pocketplanet.PerfilConfiguraciones.PerfilScreen

import edu.unicauca.example.pocketplanet.PerfilConfiguraciones.SettingsDataStore
import edu.unicauca.example.pocketplanet.Presentacion.backgroundPresentacion

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

        //Esta ruta para mostrar la pantalla de detalles por planta
        composable("detalle_planta/{nombre}") { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("nombre") ?: "Desconocida"
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            DetallePlantaScreen(plantaNombre = nombre, navController, userId)
        }

        // Nueva ruta para la pantalla AgregarPlanta
        composable("${Screens.AgregarPlantaScreen.name}/{userId}") {backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            AgregarPlanta(navController = navController,userId)
        }


        composable("informacion_planta/{nombre}/{userId}") { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            Informacion_Planta(navController = navController, userId,plantaNombre = nombre)
        }

        composable("${Screens.AlertConfigScreen.name}/{userId}") { backStackEntry ->
            val context = LocalContext.current // Obtenemos el contexto actual
            val userId = backStackEntry.arguments?.getString("userId") ?: "" // Extraemos el parámetro userId
            AlertConfigScreen(
                context = context,
                navController = navController, // Pasamos el navController necesario para NavigationScreens
                userId = userId, // Pasamos el userId necesario para NavigationScreens
                onBack = { navController.popBackStack() } // Acción para regresar a la pantalla anterior
            )
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
    ConfiguracionesScreen,
    DetallePlantaScreen,
    AgregarPlantaScreen,
    AlertConfigScreen
}