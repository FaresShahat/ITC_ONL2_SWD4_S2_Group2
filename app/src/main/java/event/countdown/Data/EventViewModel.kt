package event.countdown.Data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmandcroforproject.Data.Event
import com.example.mvvmandcroforproject.Data.EventDao
import com.example.mvvmandcroforproject.Data.EventState
import com.example.mvvmandcroforproject.Data.Eventevent
import com.example.mvvmandcroforproject.Data.SortType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class EventViewModel(
    private val dao: EventDao
): ViewModel() {
    private val _sortType=MutableStateFlow(SortType.DATE)
    private val _contacts = _sortType
        .flatMapLatest {sortType->
            when(sortType){
                SortType.DATE ->dao.getEventsOrderedByDate()
                SortType.NAME -> dao.getEventsOrderedByName()
            }
        }
        .stateIn(viewModelScope,SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(EventState())
    val state = combine(_state,_sortType,_contacts){ state, sortType,contacts->
        state.copy(
            events = contacts,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), EventState() )

    fun onEvent(event: Eventevent){

        when(event){
            is Eventevent.DeleteEvents -> {
                viewModelScope.launch {
                    dao.deleteEvent(event.event)
                }
            }
            Eventevent.HideDialog -> {
                _state.update {
                    it.copy(
                        isAddingContact = false
                    )
                }
            }
            Eventevent.SaveEvent -> {
                val name=state.value.name
                val date=state.value.date
                val startTime=state.value.startTime
                val endTime=state.value.endTime

                if(name!!.isBlank()||date!!.isBlank()){
                    return
                }
                val event = Event(
                    name = name,
                    startTime = startTime,
                    endTime = endTime,
                    date = date
                )
                viewModelScope.launch {
                    dao.insertEvent(event)
                }
                _state.update {
                    it.copy(
                        isAddingContact = false,
                        date = "",
                        startTime = null,
                        endTime = null
                    )
                }
            }
            is Eventevent.SetDate -> {
                _state.update {
                    it.copy(
                        date = event.date
                    )
                }
            }
            is Eventevent.SetEndTim -> {
                _state.update {
                    it.copy(
                        endTime = event.endTim
                    )
                }
            }
            is Eventevent.SetName -> {
                _state.update {
                    it.copy(
                        name = event.name
                    )
                }
            }
            is Eventevent.SetStartTime -> {
                _state.update {
                    it.copy(
                        startTime = event.startTime
                    )
                }
            }
            Eventevent.ShowDialog -> {
                _state.update {
                    it.copy(
                        isAddingContact = true
                    )
                }
            }
            is Eventevent.sortEvents -> {
                _sortType.value = event.sortType
            }
            else -> {}
        }
    }
}
