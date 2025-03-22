package event.countdown.AlarmManager.Reciver

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.plcoding.alarmmanagerguide.ui.theme.AlarmManager.EventScheduler
import event.countdown.Data_Room.EventDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

 class EventActionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val eventId = intent?.getIntExtra("EVENT_ID", -1) ?: return
        val action = intent.action


        when (action) {
            "ACTION_SNOOZE" -> {
                val newTime = System.currentTimeMillis() + 10 * 60 * 1000 // تأجيل 10 دقائق
                CoroutineScope(Dispatchers.IO).launch {
                    val eventDao = EventDatabase.getInstance(context).eventDao()
                    eventDao.updateTime(eventId, newTime)
                    EventScheduler.scheduleEventAlarm(context, eventId, newTime)
                }
                Log.d("EventActionReceiver", "تم تأجيل الحدث ID: $eventId لمدة 10 دقائق")
            }

            "ACTION_CANCEL" -> {
                cancelEventNotification(context, eventId)
                Log.d("EventActionReceiver", "تم إلغاء الإشعار للحدث ID: $eventId")
            }
        }
    }
    private fun cancelEventNotification(context: Context, eventId: Int) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(eventId)

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, EventAlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, eventId, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        alarmManager.cancel(pendingIntent)
    }
}


//     when (action) {
//         "ACTION_SNOOZE" -> {
//             val newTime = System.currentTimeMillis() + 10 * 60 * 1000 // تأجيل 10 دقائق
//             CoroutineScope(Dispatchers.IO).launch {
//                 val eventDao = EventDatabase.getInstance(context).eventDao()
//                 eventDao.updateTime(eventId, newTime)
//                 EventScheduler.scheduleEventAlarm(context, eventId, newTime)
//             }
//             Log.d("EventActionReceiver", "تم تأجيل الحدث ID: $eventId لمدة 10 دقائق")
//         }
//
//         "ACTION_CANCEL" -> {
//             cancelEventNotification(context, eventId)
//             Log.d("EventActionReceiver", "تم إلغاء الإشعار للحدث ID: $eventId")
//         }
//     }
// }
