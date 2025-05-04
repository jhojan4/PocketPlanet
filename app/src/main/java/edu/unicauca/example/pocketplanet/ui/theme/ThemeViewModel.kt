// ui/theme/ThemeViewModel.kt
package edu.unicauca.example.pocketplanet.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.unicauca.example.pocketplanet.PerfilConfiguraciones.SettingsDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ThemeViewModel(private val themeRepository: SettingsDataStore) : ViewModel() {

    private val _isDarkTheme = MutableStateFlow(false)  // Empieza en modo claro
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    init {
        // Cargar el estado inicial del modo oscuro
        viewModelScope.launch {
            themeRepository.isDarkModeEnabled.collect { isEnabled ->
                _isDarkTheme.value = isEnabled
            }
        }
    }

    fun toggleTheme() {
        val newState = !_isDarkTheme.value
        viewModelScope.launch {
            themeRepository.setDarkMode(newState)
        }
        _isDarkTheme.value = newState
    }

    // Función para actualizar el estado del tema
    fun setDarkTheme(enabled: Boolean) {
        viewModelScope.launch {
            themeRepository.setDarkMode(enabled)
        }
    }
}
