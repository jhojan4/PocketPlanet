package edu.unicauca.example.pocketplanet


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.unicauca.example.pocketplanet.ui.theme.PocketPlanetTheme


class Infomation : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PocketPlanetTheme {
                PlantInfoScreen(
                    plantName = intent.getStringExtra("plant_name") ?: "Planta",
                    plantImage = intent.getIntExtra("plant_image", R.drawable.agave),
                    plantInfo = intent.getStringExtra("plant_info") ?: "Información no disponible",
                    onBack = {
                        startActivity(Intent(this, ConsejosJardineria::class.java))
                        finish()
                    }
                )
            }
        }
    }
}

@Composable
fun PlantInfoScreen(plantName: String, plantImage: Int, plantInfo: String, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8F8D8))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(8.dp)
        )

        {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar")
        }

        Text(text = plantName, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        Image(
            painter = painterResource(id = plantImage),
            contentDescription = plantName,
            modifier = Modifier
                .size(150.dp)
                .background(Color.White, shape = RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Text(
                text = plantInfo,
                modifier = Modifier.padding(16.dp),
                fontSize = 16.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPlantInfoScreen() {
    PocketPlanetTheme {
        PlantInfoScreen(
            plantName = "Aloe Vera",
            plantImage = R.drawable.agave,
            plantInfo = "Tipo de planta: Herbácea\nCantidad de Agua / riego: 1-2 litros\nTipo de Fertilización: Abono orgánico\nHoras de sol: 6-8 horas",
            onBack = {}
        )
    }
}