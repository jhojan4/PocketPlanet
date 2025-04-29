package edu.unicauca.example.pocketplanet.Agregar_Planta

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class AgregarPlantaViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    // Función para agregar la planta a Firestore
    fun agregarPlanta(planta: Planta, userId: String) {
        val plantasCollection = db.collection("dataplants")
        val configuracionDoc = db.collection("configuracion").document("id_planta") // Documento único que guarda el último ID

        // Obtener el último ID registrado
        configuracionDoc.get()
            .addOnSuccessListener { document ->
                var nuevoId = 1 // Valor por defecto en caso de que no exista el documento
                if (document.exists()) {
                    val ultimoId = document.getLong("id_planta") ?: 0
                    nuevoId = (ultimoId + 1).toInt() // Incrementamos el último ID
                }

                // Crear la planta con el nuevo ID y asociar el userId
                val plantaConId = planta.copy(id = nuevoId, userId = userId)

                // Guardar la planta en la colección
                plantasCollection.document(nuevoId.toString()).set(plantaConId)
                    .addOnSuccessListener {
                        println("Planta registrada con ID: $nuevoId y userId: $userId")
                    }
                    .addOnFailureListener { e ->
                        println("Error al agregar la planta: $e")
                    }

                // Actualizar el último ID registrado
                configuracionDoc.update("id_planta", nuevoId)
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
