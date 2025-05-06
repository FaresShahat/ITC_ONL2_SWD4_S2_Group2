package event.countdown.Model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import event.countdown.Repository.EventRepository
import event.countdown.Data_Room.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEventViewModel @Inject constructor(
    private val repository: EventRepository
) : ViewModel() {


    fun addEvent(date: String, eventName: String) {
        viewModelScope.launch {
//            repository.addEvent(date, eventName)
        }
    }
}