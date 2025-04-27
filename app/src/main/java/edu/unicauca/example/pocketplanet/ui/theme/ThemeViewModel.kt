// ui/theme/ThemeViewModel.kt
package edu.unicauca.example.pocketplanet.ui.theme

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ThemeViewModel : ViewModel() {

    private val _isDarkTheme = MutableStateFlow(false)  // Empieza en modo claro
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme

    fun toggleTheme() {
        _isDarkTheme.value = !_isDarkTheme.value
    }
}
