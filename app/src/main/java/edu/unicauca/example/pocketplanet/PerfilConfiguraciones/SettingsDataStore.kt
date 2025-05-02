package edu.unicauca.example.pocketplanet.PerfilConfiguraciones

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Crear el DataStore
val Context.dataStore by preferencesDataStore("settings")

class SettingsDataStore(private val context: Context) {

    // Definir llaves para cada opción
    companion object {
        val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
    }

    // Funciones para guardar valores
    suspend fun saveNotificationsEnabled(enabled: Boolean) {
        context.dataStore.edit { settings ->
            settings[NOTIFICATIONS_ENABLED] = enabled
        }
    }

    // Funciones para leer valores
    val notificationsEnabledFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[NOTIFICATIONS_ENABLED] ?: true // valor por defecto
        }


}
