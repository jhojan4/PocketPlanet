package edu.unicauca.example.pocketplanet.PerfilConfiguraciones

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class UpdateUserViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    fun updateUser(userName: String, email: String) {
        val nuevosDatos = mapOf(
            "userName" to userName,
            "email" to email
        )

        // Aquí debes obtener el userId de alguna manera. Puede ser de la autenticación
        val userId = "user_id_aqui" // Obtén el userId de donde corresponda

        viewModelScope.launch {
            db.collection("users")
                .document(userId)
                .update(nuevosDatos)
                .addOnSuccessListener {
                    // Acción cuando se actualiza correctamente
                    Log.d("Firestore", "Usuario actualizado exitosamente.")
                }
                .addOnFailureListener { e ->
                    // Acción en caso de error
                    Log.e("Firestore", "Error al actualizar usuario: ${e.localizedMessage}")
                }
        }
    }
}

