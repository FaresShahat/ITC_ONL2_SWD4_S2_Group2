package com.plcoding.alarmmanagerguide.ui.eventdetail

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plcoding.alarmmanagerguide.ui.theme.AlarmManager.EventScheduler
import event.countdown.AlarmManager.Reciver.EventAlarmReceiver
import event.countdown.Data_Room.Event
import event.countdown.Data_Room.EventDao
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun EventDetailScreen(eventId: Int, eventDao: EventDao, onClose: () -> Unit) {
    val event = remember { mutableStateOf<Event?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(eventId) {
        coroutineScope.launch {
            event.value = eventDao.getEvent(eventId)
        }
    }

    event.value?.let { currentEvent ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "تفاصيل الحدث", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "عنوان الحدث: ${currentEvent.title}", fontSize = 18.sp)
            Text(
                text = "الوقت: ${SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date(currentEvent.timeInMillis))}",
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            eventDao.delete(eventId)
                            cancelEventNotification(context, eventId)
                            onClose()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                ) {
                    Text(text = "حذف الحدث", color = Color.White)
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        coroutineScope.launch {
                            val newTime = System.currentTimeMillis() + 10 * 60 * 1000 // تأجيل 10 دقائق
                            eventDao.updateTime(eventId, newTime)
                            EventScheduler.scheduleEventAlarm(context, eventId, newTime)
                            onClose()
                        }
                    }
                ) {
                    Text(text = "تأجيل 10 دقائق")
                }
            }
        }
    } ?: run {
        Text(text = "لا يوجد بيانات لهذا الحدث", fontSize = 18.sp, color = Color.Red)
    }
}

fun cancelEventNotification(context: Context, eventId: Int) {
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.cancel(eventId)

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, EventAlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(context, eventId, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    alarmManager.cancel(pendingIntent)
}
