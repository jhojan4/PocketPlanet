package edu.unicauca.example.pocketplanet.Notificaciones


import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import edu.unicauca.example.pocketplanet.InicioAplicacion.NavigationScreens
import edu.unicauca.example.pocketplanet.Presentacion.bottonRedondoStateless

@Composable
fun AlertConfigScreen(
    context: Context,
    navController: NavHostController,
    userId: String,
    onBack: () -> Unit
) {
    val notificationChannelId = "alerts_channel"

    // Crear canal de notificación (para API 26+)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "Alertas"
        val descriptionText = "Canal para notificaciones de alertas"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(notificationChannelId, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    // Estados de los switches y tiempo
    var isRiegoEnabled by remember { mutableStateOf(false) }
    var riegoTime by remember { mutableStateOf("") } // Tiempo para riego

    var isFertilizacionEnabled by remember { mutableStateOf(false) }
    var fertilizacionTime by remember { mutableStateOf("") } // Tiempo para fertilización

    var isPodaEnabled by remember { mutableStateOf(false) }
    var podaTime by remember { mutableStateOf("") } // Tiempo para poda

    MaterialTheme { // Aplicar el tema del proyecto
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background), // Fondo según el tema
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Botón de regreso en la parte superior
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.secondaryContainer) // Color primario del tema
                    .padding(16.dp)
            ) {
                bottonRedondoStateless(
                    onClick = onBack, // Acción para volver
                    icon = Icons.Default.ArrowBack,
                    colors = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Título
            Text(
                text = "Configuración Alertas",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground // Color según el tema
            )

            Text(
                text = "Activa / Desactiva las alertas y configura el tiempo",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Opciones de configuración con tiempo
            AlertOptionWithTime(
                title = "RIEGO",
                isEnabled = isRiegoEnabled,
                onToggle = { isRiegoEnabled = it },
                time = riegoTime,
                onTimeChange = { riegoTime = it },
                context = context,
                notificationChannelId = notificationChannelId
            )

            AlertOptionWithTime(
                title = "FERTILIZACIÓN",
                isEnabled = isFertilizacionEnabled,
                onToggle = { isFertilizacionEnabled = it },
                time = fertilizacionTime,
                onTimeChange = { fertilizacionTime = it },
                context = context,
                notificationChannelId = notificationChannelId
            )

            AlertOptionWithTime(
                title = "PODA",
                isEnabled = isPodaEnabled,
                onToggle = { isPodaEnabled = it },
                time = podaTime,
                onTimeChange = { podaTime = it },
                context = context,
                notificationChannelId = notificationChannelId
            )

            Spacer(modifier = Modifier.weight(1f)) // Espaciado flexible para empujar los elementos hacia abajo

            // Navegador en la parte inferior
            NavigationScreens(
                navController = navController,
                userId = userId,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface) // Fondo del navegador
            )
        }
    }
}

@Composable
fun AlertOptionWithTime(
    title: String,
    isEnabled: Boolean,
    onToggle: (Boolean) -> Unit,
    time: String,
    onTimeChange: (String) -> Unit,
    context: Context,
    notificationChannelId: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Switch(
                checked = isEnabled,
                onCheckedChange = {
                    onToggle(it)
                    if (it && time.isNotEmpty()) {
                        scheduleNotification(context, notificationChannelId, title, time.toInt())
                    }
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = "Configura el tiempo en minutos", fontSize = 14.sp, color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Campo para ingresar el tiempo
        OutlinedTextField(
            value = time,
            onValueChange = onTimeChange,
            label = { Text("Tiempo (min)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@SuppressLint("ScheduleExactAlarm")
fun scheduleNotification(context: Context, channelId: String, title: String, timeInMinutes: Int) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, NotificationReceiver::class.java).apply {
        putExtra("title", title)
        putExtra("channelId", channelId)
    }
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        title.hashCode(),
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    // Verifica los permisos antes de programar la alarma
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.SCHEDULE_EXACT_ALARM) != PackageManager.PERMISSION_GRANTED) {
            // No tienes el permiso para programar alarmas exactas
            return
        }
    }

    val triggerTime = System.currentTimeMillis() + timeInMinutes * 60 * 1000L
    alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
}

// BroadcastReceiver para manejar la notificación
class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title") ?: "Alerta"
        val channelId = intent.getStringExtra("channelId") ?: "default_channel"

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Alerta activada")
            .setContentText("La alerta para $title está activa.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            notify(title.hashCode(), builder.build())
        }
    }
}


