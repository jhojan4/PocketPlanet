package edu.unicauca.example.pocketplanet.Session

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserSessionViewModel : ViewModel() {
    private val _userId = MutableStateFlow<String?>(null)
    val userId: StateFlow<String?> = _userId

    fun setUserId(id: String) {
        _userId.value = id
    }

    fun clearSession() {
        _userId.value = null
    }
}
