package event.countdown.Model

import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

class CalendarViewModel : ViewModel() {

    private val today = LocalDate.now()
    val currentMonth: YearMonth = YearMonth.of(today.year, today.month)
    val daysInMonth: Int = currentMonth.lengthOfMonth()
    val firstDayOfMonth: Int = currentMonth.atDay(1).dayOfWeek.value % 7
    val monthYearDisplay: String = today.month.getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + today.year
    val currentDay: Int = today.dayOfMonth
}
