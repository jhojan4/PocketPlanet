package edu.unicauca.example.pocketplanet

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.unicauca.example.pocketplanet.InicioAplicacion.Screen_Inicio_Aplicacion
import edu.unicauca.example.pocketplanet.Inicio_Sesion.Inicio_Sesio
//import edu.unicauca.example.pocketplanet.Presentacion
//import edu.unicauca.example.pocketplanet.Registro.Registro
//import edu.unicauca.example.pocketplanet.Inicio_Sesion.Inicio_Sesion
import edu.unicauca.example.pocketplanet.Presentacion.backgroundPresentacion
import edu.unicauca.example.pocketplanet.Registro.backgroundRegistro


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(){
    val  navController= rememberNavController()
    NavHost(navController = navController, startDestination = Screens.PresentacionScreen.name){
        composable(Screens.PresentacionScreen.name){
            backgroundPresentacion(navController)
        }
        composable(Screens.InicioSesionScreen.name){
            Inicio_Sesio(navController,modifier = Modifier)
        }
        composable(Screens.RegistroScreen.name){
            backgroundRegistro(navController)
        }
        composable(Screens.InicioAplicacion.name){
            Screen_Inicio_Aplicacion(navController)
        }
    }

}
enum class Screens(){
    PresentacionScreen,
    InicioSesionScreen,
    RegistroScreen,
    InicioAplicacion,
}