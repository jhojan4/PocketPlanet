package edu.unicauca.example.pocketplanet.Registro

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.google.firebase.firestore.FirebaseFirestore
import java.security.MessageDigest

/**
 * ViewModel encargado de manejar el registro de usuarios en Firebase Firestore.
 */
class RegistroViewModel : ViewModel() {

    var userName by mutableStateOf("")
    var email by mutableStateOf("")
    var country by mutableStateOf("")
    var phoneNumber by mutableStateOf("")
    var password by mutableStateOf("")
    var repeatPassword by mutableStateOf("")

    private val db = FirebaseFirestore.getInstance()

    fun registerUser(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (password != repeatPassword) {
            onError("Las contraseñas no coinciden")
            return
        }

        if (userName.isBlank() || email.isBlank() || country.isBlank() || phoneNumber.isBlank() || password.isBlank()) {
            onError("Todos los campos deben estar llenos")
            return
        }

        if (!isValidEmail(email)) {
            onError("El correo debe tener un formato válido (usuario@dominio.com)")
            return
        }

        if (!phoneNumber.all { it.isDigit() }) {
            onError("El número de teléfono solo debe contener números")
            return
        }

        db.collection("users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    saveUser(onSuccess, onError)
                } else {
                    onError("Este correo ya está registrado.")
                }
            }
            .addOnFailureListener { e ->
                onError("Error consultando la base de datos: ${e.localizedMessage}")
            }
    }

    private fun saveUser(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val hashedPassword = hashPassword(password)

        val user = hashMapOf(
            "name" to userName,
            "email" to email,
            "country" to country,
            "phone" to phoneNumber,
            "password" to hashedPassword
        )

        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d("RegistroViewModel", "Usuario registrado con ID: ${documentReference.id}")
                clearFields()
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.w("RegistroViewModel", "Error registrando usuario", e)
                onError("Error al registrar el usuario: ${e.localizedMessage}")
            }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun hashPassword(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    private fun clearFields() {
        userName = ""
        email = ""
        country = ""
        phoneNumber = ""
        password = ""
        repeatPassword = ""
    }
}
