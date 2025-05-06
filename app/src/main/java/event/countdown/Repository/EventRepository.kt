package event.countdown.Repository

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import event.countdown.AlarmManager.Reciver.EventAlarmReceiver
import event.countdown.Data_Room.Event
import event.countdown.Data_Room.EventDao

class EventRepository(private val eventDao: EventDao, private val context: Context) {
    suspend fun addEvent(event: Event): Int {
        return eventDao.insert(event.copy(isFromAppEvent = true)).toInt()
    }

    suspend fun getAllFutureAppEvents(currentTime: Long): List<Event> {
        return eventDao.getAllFutureAppEvents(currentTime)
    }

    @SuppressLint("ServiceCast")
    fun scheduleEventAlarm(eventId: Int, timeInMillis: Long) {
        if (timeInMillis < System.currentTimeMillis()) return

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, EventAlarmReceiver::class.java).apply {
            putExtra("EVENT_ID", eventId)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context, eventId, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent)
    }
}

private fun EventDao.getAllFutureAppEvents(time: Long): List<Event> {
    TODO("Not yet implemented")
}











//import event.countdown.Data_Room.Event
//import event.countdown.Data_Room.EventDao
//import kotlinx.coroutines.flow.Flow
//
//class EventRepository(private val eventDao: EventDao) {
//
//    val allEvents: Flow<List<Event>> = eventDao.getAllEvents()
//
//    suspend fun insert(event: Event) {
//        eventDao.insert(event)
//    }
//
//    suspend fun update(event: Event) {
//        eventDao.update(event)
//    }
//
//    suspend fun delete(event: Event) {
//        eventDao.delete(event)
//    }
//
//    suspend fun getEventById(eventId: Int): Event? {
//        return eventDao.getEventById(eventId)
//    }
//}

//النلف ده خاض بفضل البيانات عن بعض