package edu.unicauca.example.pocketplanet.Presentacion

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import edu.unicauca.example.pocketplanet.R

class PresentacionViewModel: ViewModel() {

    private val _imageIndex = mutableStateOf(0)
    val imageIndex: State<Int> = _imageIndex
    private val _stringsIndex = mutableStateOf(0)
    val stringsIndex: State<Int> = _stringsIndex

        val images = listOf(
            R.drawable.alerts,
            R.drawable.statistics,
            R.drawable.recomendation,
            R.drawable.logo
        )
    val stringsPrinpals = listOf(
        R.string.alerts,
        R.string.statistics,
        R.string.recomendation,
        R.string.logo

    )

        fun previousImage() {
            _imageIndex.value = (_imageIndex.value - 1 + images.size) % images.size
            _stringsIndex.value = (_stringsIndex.value - 1 + stringsPrinpals.size) % stringsPrinpals.size
        }

        fun nextImage() {
            _imageIndex.value = (_imageIndex.value + 1) % images.size
            _stringsIndex.value = (_stringsIndex.value + 1) % stringsPrinpals.size
        }

}