package edu.unicauca.example.pocketplanet.PerfilConfiguraciones

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LanguageViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferences = application.getSharedPreferences("app_settings", Context.MODE_PRIVATE)

    // Cambiar MutableLiveData a MutableStateFlow
    private val _currentLanguage = MutableStateFlow("en") // Valor predeterminado es "en" (inglés)
    val currentLanguage: StateFlow<String> get() = _currentLanguage

    init {
        // Cargar el idioma guardado al inicio
        _currentLanguage.value = sharedPreferences.getString("language", "en") ?: "en"
    }

    fun cambiarIdioma(idioma: String) {
        // Guardar el idioma seleccionado en SharedPreferences
        val editor = sharedPreferences.edit()
        editor.putString("language", idioma)
        editor.apply()

        // Notificar el cambio de idioma a la UI
        _currentLanguage.value = idioma
    }
}
