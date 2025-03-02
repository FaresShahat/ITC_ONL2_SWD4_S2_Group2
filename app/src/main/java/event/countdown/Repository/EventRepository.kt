package event.countdown.Repository

import event.countdown.Data_Room.Event
import event.countdown.Data_Room.EventDao
import kotlinx.coroutines.flow.Flow

class EventRepository(private val eventDao: EventDao) {

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
}

//النلف ده خاض بفضل البيانات عن بعض