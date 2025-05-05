package edu.unicauca.example.pocketplanet.Notificaciones


import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

@Composable
fun AlertConfigScreen(context: Context) {
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

    // Estados de los switches
    var isRiegoEnabled by remember { mutableStateOf(false) }
    var isFertilizacionEnabled by remember { mutableStateOf(false) }
    var isPodaEnabled by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0F7E9)), // Color verde claro
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Encabezado
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFB2EBF2)) // Color verde menta
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Icono de notificación",
                modifier = Modifier.align(Alignment.Center),
                tint = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Título
        Text(
            text = "Configuración Alertas",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Activa / Desactiva las alertas",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Opciones de configuración
        AlertOption(
            title = "RIEGO",
            isEnabled = isRiegoEnabled,
            onToggle = { isRiegoEnabled = it },
            context = context,
            notificationChannelId = notificationChannelId
        )

        AlertOption(
            title = "FERTILIZACIÓN",
            isEnabled = isFertilizacionEnabled,
            onToggle = { isFertilizacionEnabled = it },
            context = context,
            notificationChannelId = notificationChannelId
        )

        AlertOption(
            title = "PODA",
            isEnabled = isPodaEnabled,
            onToggle = { isPodaEnabled = it },
            context = context,
            notificationChannelId = notificationChannelId
        )
    }
}

@Composable
fun AlertOption(
    title: String,
    isEnabled: Boolean,
    onToggle: (Boolean) -> Unit,
    context: Context,
    notificationChannelId: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Switch(
            checked = isEnabled,
            onCheckedChange = {
                onToggle(it)
                if (it) {
                    sendNotification(context, notificationChannelId, title)
                }
            }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(text = "Frecuencia de notificaciones", fontSize = 14.sp, color = Color.Gray)
        }
    }
}

private fun sendNotification(context: Context, channelId: String, title: String) {
    // Verificar el permiso para Android 13+
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Solicitar el permiso si no está otorgado
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                101 // Código de solicitud
            )
            return // No continuar si el permiso no está otorgado
        }
    }

    // Crear la notificación
    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setContentTitle("Alerta activada")
        .setContentText("La alerta para $title está activa.")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    // Enviar la notificación
    with(NotificationManagerCompat.from(context)) {
        notify(title.hashCode(), builder.build()) // ID único para cada notificación
    }
}