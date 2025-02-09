package event.countdown.Viewmodel

import android.app.Application
import androidx.lifecycle.*
import event.countdown.Data_Room.Event
import event.countdown.Data_Room.EventDatabase
import event.countdown.Repository.EventRepository
import kotlinx.coroutines.launch
import java.util.Date

class EventViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: EventRepository

    val allEvents: LiveData<List<Event>>

    init {
        val eventDao = EventDatabase.getDatabase(application, viewModelScope).eventDao()
        repository = EventRepository(eventDao)
        allEvents = repository.allEvents.asLiveData()
    }

    fun addEvent(title: String, description: String, timestamp: Date) {
        viewModelScope.launch {
            repository.insert(Event(title = title, description = description, timestamp = timestamp))
        }
    }

    fun deleteEvent(event: Event) {
        viewModelScope.launch {
            repository.delete(event)
        }
    }
}
