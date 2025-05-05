package edu.unicauca.example.pocketplanet.Informacion_Planta

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import edu.unicauca.example.pocketplanet.Agregar_Planta.Planta
import androidx.compose.runtime.State

class InformacionPlantaViewModel : ViewModel() {
    private val _planta = mutableStateOf<Planta?>(null)
    val planta: State<Planta?> = _planta

    // Función para cargar los detalles de una planta desde Firebase por nombre
    fun cargarDetallesPlanta(nombre: String) {
        FirebaseFirestore.getInstance().collection("dataplants")
            .whereEqualTo("nombre", nombre) // Búsqueda por nombre
            .get()
            .addOnSuccessListener { result ->
                if (result.documents.isNotEmpty()) {
                    val document = result.documents[0]  // Tomamos el primer documento con el nombre coincidente
                    _planta.value = Planta(
                        id = document.getLong("id")?.toInt() ?: 0,
                        userId = document.getString("userId") ?: "",
                        nombre = document.getString("nombre") ?: "",
                        tipo = document.getString("tipo") ?: "",
                        horasSol = document.getLong("horasSol")?.toInt() ?: 0,
                        cantidadAgua = document.getDouble("cantidadAgua")?.toFloat() ?: 0f,
                        tipoFertilizacion = document.getString("tipoFertilizacion") ?: "",
                        informacionAdicional = document.getString("informacionAdicional") ?: ""
                    )
                }
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Error al obtener la planta: $exception")
            }
    }
}