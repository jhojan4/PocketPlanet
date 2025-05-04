package edu.unicauca.example.pocketplanet.PerfilConfiguraciones

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import edu.unicauca.example.pocketplanet.ui.theme.ThemeViewModel

class ThemeViewModelFactory(private val themeRepository: SettingsDataStore) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ThemeViewModel(themeRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}