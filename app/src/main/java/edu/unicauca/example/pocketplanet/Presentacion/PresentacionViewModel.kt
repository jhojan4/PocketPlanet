package edu.unicauca.example.pocketplanet.Presentacion

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import edu.unicauca.example.pocketplanet.R

class PresentacionViewModel: ViewModel() {

        private val _imageIndex = mutableStateOf(0)
        val imageIndex: State<Int> = _imageIndex

        val images = listOf(
            R.drawable.alerts,
            R.drawable.statistics,
            R.drawable.recomendation,
            R.drawable.logo
        )

        fun previousImage() {
            _imageIndex.value = (_imageIndex.value - 1 + images.size) % images.size
        }

        fun nextImage() {
            _imageIndex.value = (_imageIndex.value + 1) % images.size
        }
}