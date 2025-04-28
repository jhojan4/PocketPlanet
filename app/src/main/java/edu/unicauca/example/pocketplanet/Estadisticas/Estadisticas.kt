package edu.unicauca.example.pocketplanet.Estadisticas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import edu.unicauca.example.pocketplanet.InicioAplicacion.NavigationScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(navController: NavHostController, modifier: Modifier) {
    var humedad by remember { mutableStateOf(23) }
    var temperatura by remember { mutableStateOf(33) }
    var fertilizante by remember { mutableStateOf(30) }
    var crecimiento by remember { mutableStateOf(65) }

    val stats = listOf(
        StatData("Humedad", humedad, Color(0xFF66BB6A)),
        StatData("Temperatura", temperatura, Color(0xFFFFA726)),
        StatData("Fertilizante", fertilizante, Color(0xFF42A5F5)),
        StatData("Crecimiento", crecimiento, Color(0xFFAB47BC))
    )

    Scaffold(
        // Barra de navegación inferior centrada
        bottomBar = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                NavigationScreens(
                    navController,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.secondaryContainer.copy(0.5f)) // Fondo translúcido
                        .size(width = 400.dp, height = 70.dp) // Tamaño fijo
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {
            // Encabezado con forma curva y título centrado
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color(0xFFA7ECA7),
                        RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                TopAppBar(
                    title = {
                        Text(
                            "Foro y estadísticas",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Gráfico de barras personalizado
            BarChart(stats)

            Spacer(modifier = Modifier.height(16.dp))

            // Tarjetas de estadísticas con valores editables
            StatsSection(stats) { updatedStat, newValue ->
                when (updatedStat) {
                    "Humedad" -> humedad = newValue
                    "Temperatura" -> temperatura = newValue
                    "Fertilizante" -> fertilizante = newValue
                    "Crecimiento" -> crecimiento = newValue
                }
            }
        }
    }
}


@Composable
fun BarChart(stats: List<StatData>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxWidth().height(200.dp)) {
            val barWidth = 50f
            val spacing = 40f
            val maxValue = 100f // Escalar a porcentaje
            val totalWidth = stats.size * (barWidth + spacing) - spacing
            val startX = (size.width - totalWidth) / 2

            stats.forEachIndexed { index, stat ->
                val barHeight = (stat.value / maxValue) * size.height
                drawRect(
                    color = stat.color,
                    topLeft = Offset(startX + index * (barWidth + spacing), size.height - barHeight),
                    size = Size(barWidth, barHeight)
                )
            }
        }
    }
}

@Composable
fun StatsSection(stats: List<StatData>, onValueChange: (String, Int) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        stats.forEach { stat ->
            StatCard(stat, onValueChange)
        }
    }
}

@Composable
fun StatCard(stat: StatData, onValueChange: (String, Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = stat.color),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(stat.name, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text("${stat.value}%", color = Color.White, fontSize = 16.sp)
        }
    }
}

data class StatData(val name: String, val value: Int, val color: Color)


