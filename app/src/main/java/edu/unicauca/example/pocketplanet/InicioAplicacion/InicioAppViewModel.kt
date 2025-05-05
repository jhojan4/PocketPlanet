package edu.unicauca.example.pocketplanet.Inicio_Aplicacion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.runtime.mutableStateListOf

/**
 * ViewModel que maneja la consulta y almacenamiento de plantas desde Firestore.
 */
class InicioAplicacionViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    var plantas = mutableStateListOf<Planta>()
        private set

    var plantasFiltradas = mutableStateListOf<Planta>()
        private set

    fun cargarPlantas(userId: String) {
        viewModelScope.launch {
            db.collection("dataplants")
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener { documents ->
                    plantas.clear()
                    for (document in documents) {
                        val nombre = document.getString("nombre") ?: "Sin nombre"
                        val imagenUrl = document.getString("imagenUrl") ?: ""
                        plantas.add(Planta(nombre, imagenUrl))
                    }
                    // Cuando cargas, plantasFiltradas inicia con todas
                    plantasFiltradas.clear()
                    plantasFiltradas.addAll(plantas)
                }
                .addOnFailureListener { e ->
                    // Manejar error
                }
        }
    }

    fun buscarPlantas(consulta: String) {
        plantasFiltradas.clear()
        if (consulta.isBlank()) {
            plantasFiltradas.addAll(plantas)
        } else {
            plantasFiltradas.addAll(
                plantas.filter { it.nombre.contains(consulta, ignoreCase = true) }
            )
        }
    }
}

/**
 * Modelo de datos para representar una planta.
 */

data class Planta(

    val nombre: String,
    val imagenUrl: String,

)

