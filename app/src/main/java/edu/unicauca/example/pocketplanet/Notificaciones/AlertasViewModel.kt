package edu.unicauca.example.pocketplanet.Notificaciones

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AlertasViewModel : ViewModel() {

    private val _riegoHora = MutableStateFlow("")
    private val _fertilizacionHora = MutableStateFlow("")
    private val _podaHora = MutableStateFlow("")

    val riegoHora: StateFlow<String> = _riegoHora
    val fertilizacionHora: StateFlow<String> = _fertilizacionHora
    val podaHora: StateFlow<String> = _podaHora

    fun actualizarHora(tipo: String, hora: String) {
        viewModelScope.launch {
            when (tipo.uppercase()) {
                "RIEGO" -> _riegoHora.value = hora
                "FERTILIZACIÓN" -> _fertilizacionHora.value = hora
                "PODA" -> _podaHora.value = hora
            }
        }
    }
}
