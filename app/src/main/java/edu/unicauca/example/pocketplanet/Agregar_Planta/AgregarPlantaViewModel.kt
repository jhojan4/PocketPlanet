package edu.unicauca.example.pocketplanet.Agregar_Planta

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class AgregarPlantaViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    // Función para agregar la planta a Firestore dejando que Firebase genere el ID automáticamente
    fun agregarPlanta(planta: Planta, userId: String) {
        val plantasCollection = db.collection("dataplants")

        // Crear la planta con el userId (sin ID asignado)
        val plantaConUserId = planta.copy(userId = userId)

        // Usamos .add() para que Firebase genere automáticamente el ID del documento
        plantasCollection.add(plantaConUserId)
            .addOnSuccessListener { documentReference ->
                println("Planta registrada con ID generado por Firestore: ${documentReference.id} y userId: $userId")
            }
            .addOnFailureListener { e ->
                println("Error al agregar la planta: $e")
            }
    }
}

// Definir la data class Planta dentro del ViewModel
data class Planta(
    val id: Int = 0,
    val userId: String = "", // Campo userId
    val nombre: String = "",
    val tipo: String = "",
    val horasSol: Int = 0,
    val cantidadAgua: Float = 0f,
    val tipoFertilizacion: String = "",
    val informacionAdicional: String = ""
)
