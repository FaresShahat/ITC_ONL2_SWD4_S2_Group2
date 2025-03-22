package event.countdown.AlarmManager.Reciver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.plcoding.alarmmanagerguide.ui.theme.AlarmManager.EventScheduler.scheduleEventAlarm
import event.countdown.Data_Room.EventDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            CoroutineScope(Dispatchers.IO).launch {
                val eventDao = EventDatabase.getInstance(context).eventDao()
                val events = eventDao.getAllEvents()
                events.forEach { event ->
                    if (event.timeInMillis > System.currentTimeMillis()) {
                        scheduleEventAlarm(context, event.id, event.timeInMillis)
                    }
                }
            }
        }
    }
}
