package edu.unicauca.example.pocketplanet.Inicio_Sesion

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import java.security.MessageDigest
import com.google.firebase.auth.FirebaseAuth


class LoginViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var userId by mutableStateOf<String?>(null) // 👈 Aquí guardamos el ID del usuario que se loguea

    private val db = FirebaseFirestore.getInstance()
    fun loginUser(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (email.isBlank() || password.isBlank()) {
            onError("Correo y contraseña no pueden estar vacíos")
            return
        }

        if (!isValidEmail(email)) {
            onError("El correo debe tener un formato válido (usuario@dominio.com)")
            return
        }

        viewModelScope.launch {
            db.collection("users")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener { documents ->
                    if (documents.isEmpty) {
                        onError("No existe un usuario con ese correo")
                    } else {
                        val document = documents.first()
                        val storedHashedPassword = document.getString("password") ?: ""

                        val inputHashedPassword = hashPassword(password)

                        if (storedHashedPassword == inputHashedPassword) {
                            // ✅ Login exitoso
                            userId = document.id // 👈 Guardamos el ID del usuario para la siguiente pantalla
                            Log.d("LoginViewModel", "Login exitoso, UserID: $userId")
                            onSuccess()
                        } else {
                            // ❌ Contraseña incorrecta
                            onError("Contraseña incorrecta")
                        }
                    }
                }
                .addOnFailureListener { e ->
                    onError("Error accediendo a la base de datos: ${e.localizedMessage}")
                }
        }
    }
    /*fun loginUser(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (email.isBlank() || password.isBlank()) {
            onError("Correo y contraseña no pueden estar vacíos")
            return
        }

        if (!isValidEmail(email)) {
            onError("Formato de correo inválido")
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    userId = auth.currentUser?.uid
                    onSuccess()
                } else {
                    onError(task.exception?.localizedMessage ?: "Fallo al iniciar sesión")
                }
            }
    }*/

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun hashPassword(password: String): String {
        val bytes = MessageDigest
            .getInstance("SHA-256")
            .digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
