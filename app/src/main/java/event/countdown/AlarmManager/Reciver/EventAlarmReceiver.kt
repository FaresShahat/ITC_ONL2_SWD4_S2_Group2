package event.countdown.AlarmManager.Reciver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.plcoding.alarmmanagerguide.ui.theme.AlarmManager.EventNotificationService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        try {
            val eventId = intent?.getIntExtra("EVENT_ID", -1) ?: return
            if (eventId == -1) return

            CoroutineScope(Dispatchers.IO).launch {
                EventNotificationService().showNotification(context, eventId)
            }
        } catch (e: Exception) {
            Log.e("EventAlarmReceiver", "Error: ${e.message}")
        }
    }
}
