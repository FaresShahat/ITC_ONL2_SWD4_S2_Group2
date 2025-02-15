package event.countdown.Repository

import event.countdown.Data_Room.Event
import event.countdown.Data_Room.EventDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepository @Inject constructor(private val eventDao: EventDao) {

    val allEvents: Flow<List<Event>> = eventDao.getAllEvents()

    suspend fun insert(event: Event) {
        eventDao.insert(event)
    }

    suspend fun update(event: Event) {
        eventDao.update(event)
    }

    suspend fun delete(event: Event) {
        eventDao.delete(event)
    }

    suspend fun getEventById(eventId: Int): Event? {
        return eventDao.getEventById(eventId)
    }

    // ✅ تعديل `addEvent` ليتم تخزين الحدث في قاعدة البيانات
    suspend fun addEvent(date: String, eventName: String) {
        val newEvent = Event(date = date, eventName = eventName)
        eventDao.insert(newEvent)  // إدخال الحدث في قاعدة البيانات
    }

    private fun Event(date: String, eventName: String): Event {
        TODO("Not yet implemented")
    }
}

//النلف ده خاض بفضل البيانات عن بعض


