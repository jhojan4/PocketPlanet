package edu.unicauca.example.pocketplanet


import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.unicauca.example.pocketplanet.Consejos.ConsejosScreen
import edu.unicauca.example.pocketplanet.Consejos.DetallePlantaScreen
import edu.unicauca.example.pocketplanet.Estadisticas.StatisticsScreen
import edu.unicauca.example.pocketplanet.InicioAplicacion.Screen_Inicio_Aplicacion
import edu.unicauca.example.pocketplanet.Inicio_Sesion.Inicio_Sesio
import edu.unicauca.example.pocketplanet.PerfilConfiguraciones.PerfilScreen
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
    NavHost(navController = navController, startDestination = Screens.InicioSesionScreen.name) {

        composable(Screens.PresentacionScreen.name) {
            backgroundPresentacion(navController)
        }

        composable(Screens.InicioSesionScreen.name) {
            Inicio_Sesio(navController, modifier = Modifier)
        }

        composable(Screens.RegistroScreen.name) {
            backgroundRegistro(navController)
        }

        composable(Screens.InicioAplicacion.name) {
            Screen_Inicio_Aplicacion(navController)
        }

        composable(Screens.ConsejosScreen.name) {
            ConsejosScreen(navController, modifier = Modifier)
        }

        composable(Screens.ConfiguracionesScreen.name) {
            val context = LocalContext.current // 👈 pido el context
            val settingsDataStore = SettingsDataStore(context) // 👈 creo el objeto bien

            PerfilScreen(
                navController = navController,
                modifier = Modifier,
                themeViewModel = themeViewModel,
                settingsDataStore = settingsDataStore
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
    DetallePlantaScreen

}