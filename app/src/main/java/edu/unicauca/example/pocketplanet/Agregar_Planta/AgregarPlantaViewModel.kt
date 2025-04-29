package edu.unicauca.example.pocketplanet.Agregar_Planta

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class AgregarPlantaViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    // Función para agregar la planta a Firestore
    fun agregarPlanta(planta: Planta, userId: String) {
        val plantasCollection = db.collection("plantas")
        val configuracionDoc = db.collection("configuracion").document("id_planta") // Documento único que guarda el último ID

        // Obtener el último ID registrado
        plantasCollection.orderBy("id", Query.Direction.DESCENDING).limit(1).get()
            .addOnSuccessListener { documents ->
                var nuevoId = 1 // Valor por defecto en caso de que no haya documentos

                if (!documents.isEmpty) {
                    // Obtenemos el último ID registrado (el más grande)
                    val lastDocument = documents.documents.first()
                    val ultimoId = lastDocument.getLong("id") ?: 0
                    nuevoId = (ultimoId + 1).toInt() // Incrementamos el último ID
                }

                // Crear la planta con el nuevo ID y asociar el userId
                val plantaConId = planta.copy(id = nuevoId, userId = userId)

                // Usamos el método add() para agregar la planta con el ID secuencial
                plantasCollection.add(plantaConId)
                    .addOnSuccessListener { documentReference ->
                        println("Planta registrada con ID: $nuevoId y userId: $userId")
                    }
                    .addOnFailureListener { e ->
                        println("Error al agregar la planta: $e")
                    }

                // Actualizar el último ID registrado en la configuración
                configuracionDoc.update("id_planta", nuevoId)
                    .addOnFailureListener { e ->
                        println("Error al actualizar el ID: $e")
                    }
            }
            .addOnFailureListener { e ->
                println("Error al obtener el último ID: $e")
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
