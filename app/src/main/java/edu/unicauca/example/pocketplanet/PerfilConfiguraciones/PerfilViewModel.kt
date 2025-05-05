package edu.unicauca.example.pocketplanet.PerfilConfiguraciones

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PerfilViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    var userName: String = ""
    var email: String = ""
    var country: String = ""
    var phone: String = ""

    /**
     * Cargar el perfil actual usando el userId proporcionado.
     */
    fun cargarPerfil(
        userId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (userId.isNotBlank()) {
            db.collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        userName = document.getString("name") ?: ""
                        email = document.getString("email") ?: ""
                        country = document.getString("country") ?: ""
                        phone = document.getString("phone") ?: ""
                        onSuccess()
                    } else {
                        onError("Usuario no encontrado.")
                    }
                }
                .addOnFailureListener { e ->
                    onError("Error al cargar perfil: ${e.localizedMessage}")
                }
        } else {
            onError("ID de usuario vacío.")
        }
    }

    /**
     * Actualizar perfil con nuevo nombre y correo, usando userId.
     */
    fun actualizarPerfil(
        userId: String,
        nuevoNombre: String,
        nuevoCorreo: String,
        nuevoPais: String,
        nuevoTelefono: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (userId.isNotBlank()) {
            val updates = mapOf(
                "name" to nuevoNombre,
                "email" to nuevoCorreo,
                "country" to nuevoPais,
                "phone" to nuevoTelefono
            )

            db.collection("users")
                .document(userId)
                .update(updates)
                .addOnSuccessListener {
                    userName = nuevoNombre
                    email = nuevoCorreo
                    country = nuevoPais
                    phone = nuevoTelefono
                    onSuccess()
                }
                .addOnFailureListener { e ->
                    onError("Error al actualizar perfil: ${e.localizedMessage}")
                }
        } else {
            onError("ID de usuario vacío.")
        }
    }
}
