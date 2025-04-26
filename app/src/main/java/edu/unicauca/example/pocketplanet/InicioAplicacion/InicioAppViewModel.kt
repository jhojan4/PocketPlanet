package edu.unicauca.example.pocketplanet.Inicio_Aplicacion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

/**
 * ViewModel que maneja la consulta y almacenamiento de plantas desde Firestore.
 */
class InicioAplicacionViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    // Lista observable de plantas
    var plantas = mutableStateListOf<Planta>()
        private set

    /**
     * Función para cargar todas las plantas desde la colección de Firestore.
     */
    fun cargarPlantas() {
        viewModelScope.launch {
            db.collection("dataplants") // Asegúrate que tu colección se llame exactamente así
                .get()
                .addOnSuccessListener { documents ->
                    plantas.clear()
                    for (document in documents) {
                        val nombre = document.getString("nombre") ?: "Sin nombre"
                        val imagenUrl = document.getString("imagen") ?: ""
                        plantas.add(Planta(nombre, imagenUrl))
                    }
                }
                .addOnFailureListener { e ->
                    // Manejar error si quieres
                }
        }
    }
}

/**
 * Modelo de datos para representar una planta.
 */
data class Planta(
    val nombre: String,
    val imagenUrl: String
)
