package event.countdown.Model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class ClockViewModel @Inject constructor() : ViewModel() {

    private val _time = MutableStateFlow(Calendar.getInstance())
    val time: StateFlow<Calendar> = _time

    init {
        updateTime()
    }

    private fun updateTime() {
        viewModelScope.launch {
            while (true) {
                _time.value = Calendar.getInstance()
                delay(1000) // يحدث الوقت كل ثانية
            }
        }
    }
}
