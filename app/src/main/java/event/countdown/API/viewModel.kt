package event.countdown.API

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import event.countdown.API.model.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class EventState {
    object Loading : EventState()
    data class Success(val events: List<Event>) : EventState()
    data class Error(val message: String) : EventState()
}

class EventViewModel : ViewModel() {
    private val _state = MutableStateFlow<EventState>(EventState.Loading)
    val state: StateFlow<EventState> = _state

    init {
        fetchEvents()
    }

    private fun fetchEvents() {
        viewModelScope.launch {
            _state.value = EventState.Loading
            try {
                val response = RetrofitInstance.api.getEvents()
                _state.value = EventState.Success(response.events_results)
            } catch (e: Exception) {
                _state.value = EventState.Error("Failed to load events: ${e.message}")
            }
        }
    }
}