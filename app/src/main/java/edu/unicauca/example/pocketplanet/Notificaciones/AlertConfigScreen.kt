package edu.unicauca.example.pocketplanet.Notificaciones

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import edu.unicauca.example.pocketplanet.Funciones.TopScreen
import edu.unicauca.example.pocketplanet.InicioAplicacion.NavigationScreens
import edu.unicauca.example.pocketplanet.Presentacion.bottonRedondoStateless
import java.util.*

@Composable
fun AlertConfigScreen(
    context: Context,
    navController: NavHostController,
    userId: String,
    onBack: () -> Unit
) {
    val notificationChannelId = "alerts_channel"

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            notificationChannelId,
            "Alertas",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Canal para notificaciones de alertas"
        }
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        Log.d("Notificacion", "Canal de notificación creado: $notificationChannelId")
    }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    context as ComponentActivity,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    100
                )
            }
        }
    }

    val viewModel: AlertasViewModel = viewModel()
    val riegoTime by viewModel.riegoHora.collectAsState()
    val fertilizacionTime by viewModel.fertilizacionHora.collectAsState()
    val podaTime by viewModel.podaHora.collectAsState()

    var isRiegoEnabled by remember { mutableStateOf(false) }
    var isFertilizacionEnabled by remember { mutableStateOf(false) }
    var isPodaEnabled by remember { mutableStateOf(false) }

    MaterialTheme {
        Scaffold { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                TopScreen()
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        bottonRedondoStateless(
                            onClick = onBack,
                            icon = Icons.Default.ArrowBack,
                            colors = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.size(40.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Configuración Alertas",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Text(
                        text = "Activa / Desactiva las alertas y configura el tiempo",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    AlertOptionWithTime(
                        title = "RIEGO",
                        isEnabled = isRiegoEnabled,
                        onToggle = {
                            isRiegoEnabled = it
                            handleToggle(context, notificationChannelId, "RIEGO", riegoTime, it)
                        },
                        time = riegoTime,
                        onTimeChange = { viewModel.actualizarHora("RIEGO", it) },
                        context = context,
                        notificationChannelId = notificationChannelId
                    )

                    AlertOptionWithTime(
                        title = "FERTILIZACIÓN",
                        isEnabled = isFertilizacionEnabled,
                        onToggle = {
                            isFertilizacionEnabled = it
                            handleToggle(context, notificationChannelId, "FERTILIZACIÓN", fertilizacionTime, it)
                        },
                        time = fertilizacionTime,
                        onTimeChange = { viewModel.actualizarHora("FERTILIZACIÓN", it) },
                        context = context,
                        notificationChannelId = notificationChannelId
                    )

                    AlertOptionWithTime(
                        title = "PODA",
                        isEnabled = isPodaEnabled,
                        onToggle = {
                            isPodaEnabled = it
                            handleToggle(context, notificationChannelId, "PODA", podaTime, it)
                        },
                        time = podaTime,
                        onTimeChange = { viewModel.actualizarHora("PODA", it) },
                        context = context,
                        notificationChannelId = notificationChannelId
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    // Botón de prueba
                    Button(onClick = {
                        val intent = Intent(context, NotificationReceiver::class.java).apply {
                            putExtra("title", "PRUEBA INMEDIATA")
                            putExtra("channelId", notificationChannelId)
                        }
                        context.sendBroadcast(intent)
                    }) {
                        Text("Test Notificación")
                    }
                    NavigationScreens(
                        navController, userId,
                        Modifier
                            .background(MaterialTheme.colorScheme.secondaryContainer.copy(0.5f))
                            .fillMaxWidth()
                            .size(70.dp)
                            //.align(Alignment.BottomCenter)
                    )
                }


            }
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
                onCheckedChange = onToggle
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = "Configura la hora (hh:mm)", fontSize = 14.sp, color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = time,
            onValueChange = onTimeChange,
            label = { Text("Hora (hh:mm)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

fun handleToggle(context: Context, channelId: String, title: String, time: String, isEnabled: Boolean) {
    if (isEnabled && time.contains(":")) {
        val parts = time.split(":")
        val hour = parts.getOrNull(0)?.toIntOrNull() ?: return
        val minute = parts.getOrNull(1)?.toIntOrNull() ?: return
        scheduleDailyNotification(context, channelId, title, hour, minute)
    } else {
        cancelNotification(context, title)
    }
}

fun scheduleDailyNotification(context: Context, channelId: String, title: String, hour: Int, minute: Int) {
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

    val calendar = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
        set(Calendar.SECOND, 0)
        if (before(Calendar.getInstance())) {
            add(Calendar.DAY_OF_YEAR, 1)
        }
    }

    alarmManager.setRepeating(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        AlarmManager.INTERVAL_DAY,
        pendingIntent
    )

    Log.d("Notificacion", "Alarma programada para $title a las $hour:$minute")
}

fun cancelNotification(context: Context, title: String) {
    val intent = Intent(context, NotificationReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        title.hashCode(),
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    alarmManager.cancel(pendingIntent)
    Log.d("Notificacion", "Alarma cancelada para $title")
}

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title") ?: "Alerta"
        val channelId = intent.getStringExtra("channelId") ?: "default_channel"

        Log.d("Notificacion", "Broadcast recibido para $title")

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
                return
            }
            notify(title.hashCode(), builder.build())
        }
    }
}