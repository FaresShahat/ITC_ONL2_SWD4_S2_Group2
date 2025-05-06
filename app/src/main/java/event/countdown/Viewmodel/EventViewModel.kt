package event.countdown.Viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import event.countdown.Data_Room.Event
import event.countdown.Data_Room.EventDatabase
import event.countdown.Repository.EventRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date

class EventViewModel(application: Application) : AndroidViewModel(application) {
    private val eventDao = EventDatabase.getInstance(application).eventDao()
    private val repository = EventRepository(eventDao, application)

    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> get() = _events

    init {
        fetchEvents()
    }

    private fun fetchEvents() {
        viewModelScope.launch(Dispatchers.IO) {
            _events.value = repository.getAllFutureAppEvents(System.currentTimeMillis())
        }
    }

    fun addAppEvent(event: Event) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val eventId = repository.addEvent(event)
                repository.scheduleEventAlarm(eventId, event.timeInMillis)
                fetchEvents() // تحديث القائمة بعد الإضافة
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun restoreAppEventAlarms() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val allEvents = repository.getAllFutureAppEvents(System.currentTimeMillis())
                allEvents.forEach { event ->
                    repository.scheduleEventAlarm(event.id, event.timeInMillis)
                }
                _events.value = allEvents // تحديث الحالة بعد الاسترجاع
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}











//
//import android.app.Application
//import androidx.lifecycle.*
//import event.countdown.Data_Room.Event
//import event.countdown.Data_Room.EventDatabase
//import event.countdown.Repository.EventRepository
//import kotlinx.coroutines.launch
//import java.util.Date
//
//class EventViewModel(application: Application) : AndroidViewModel(application) {
//    private val repository: EventRepository
//
//   // val allEvents: LiveData<List<Event>>
//
//    init {
//        val eventDao = EventDatabase.getDatabase(application, viewModelScope).eventDao()
//        repository = EventRepository(eventDao)
//     //   allEvents = repository.allEvents.asLiveData()
//    }
//
//    fun addEvent(title: String, description: String, timestamp: Date) {
//        viewModelScope.launch {
//            repository.insert(Event(title = title, description = description, timestamp = timestamp))
//        }
//    }
//
//    fun deleteEvent(event: Event) {
//        viewModelScope.launch {
//            repository.delete(event)
//        }
//    }
//}
