package event.countdown.Screens

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import java.util.*
import java.util.concurrent.TimeUnit

@Composable
fun AddEvent(navController: NavHostController) {
    val calendar = Calendar.getInstance()
    val currentMinutes = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE)

    var showDialog by remember { mutableStateOf(true) }
    var eventName by remember { mutableStateOf("") }

    // Initialize start and end times based on current time
    var startTime by remember { mutableStateOf(currentMinutes.toFloat()) }
    var endTime by remember { mutableStateOf((currentMinutes + 60).coerceAtMost(1439).toFloat()) }

    if (showDialog) {
        AddEventDialog(
            eventName = eventName,
            startTime = startTime,
            endTime = endTime,
            onEventNameChange = { eventName = it },
            onStartTimeChange = { startTime = it },
            onEndTimeChange = { endTime = it },
            onAddEvent = {
                if (eventName.isNotBlank()) {
                    showDialog = false
                }
            },
            onCancel = { showDialog = false },
            navController = navController
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventDialog(
    eventName: String,
    startTime: Float,
    endTime: Float,
    onEventNameChange: (String) -> Unit,
    onStartTimeChange: (Float) -> Unit,
    onEndTimeChange: (Float) -> Unit,
    onAddEvent: () -> Unit,
    onCancel: () -> Unit,
    navController: NavHostController
) {
    val context = LocalContext.current
    // حالة لتخزين التاريخ المختار، القيمة الابتدائية هي تاريخ اليوم بصيغة "يوم/شهر/سنة"
    val calendar = Calendar.getInstance()
    var eventDate by remember { mutableStateOf(
        "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.YEAR)}"
    ) }
    // حالة لتحديد ما إذا كان DatePickerDialog ظاهر أم لا
    var showDatePicker by remember { mutableStateOf(false) }

    // دالة لفتح DatePickerDialog
    if (showDatePicker) {
        ShowDatePickerDialog(context) { selectedDate ->
            eventDate = selectedDate
            showDatePicker = false
        }
    }

    AlertDialog(
        onDismissRequest = onCancel,
        confirmButton = {
            TextButton(onClick = onAddEvent) {
                Text("Add", color = Color(0xFF9C27B0))
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text("Cancel", color = Color(0xFF9C27B0))
            }
        },
        title = {
            Text("Add Event", fontWeight = FontWeight.Bold, color = Color(0xFF9C27B0))
        },
        text = {
            Column {
                TextField(
                    value = eventName,
                    onValueChange = onEventNameChange,
                    label = { Text("Event Name", color = Color(0xFF9C27B0)) },
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        focusedIndicatorColor = Color(0xFF9C27B0),
                        unfocusedIndicatorColor = Color(0xFF9C27B0)
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))

                // حقل اختيار التاريخ
                Text(
                    text = "Event Date: $eventDate",
                    color = Color(0xFF9C27B0),
                    modifier = Modifier
                        .clickable { showDatePicker = true }
                        .padding(vertical = 8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text("Start Time: ${formatTime(startTime.toInt())}", color = Color(0xFF9C27B0))
                Slider(
                    value = startTime,
                    onValueChange = onStartTimeChange,
                    valueRange = 0f..1439f
                )

                Text("End Time: ${formatTime(endTime.toInt())}", color = Color(0xFF9C27B0))
                Slider(
                    value = endTime,
                    onValueChange = onEndTimeChange,
                    valueRange = startTime..1439f
                )

                val timeDifference = endTime.toInt() - startTime.toInt()
                Text(
                    "The Time Is: ${formatTime(timeDifference)}",
                    color = Color(0xFF9C27B0),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        },
        containerColor = Color.White
    )
}

// دالة لإنشاء وعرض DatePickerDialog
@Composable
fun ShowDatePickerDialog(context: Context, onDateSelected: (String) -> Unit) {
    // الحصول على التاريخ الحالي ليكون القيمة الافتراضية
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    // إنشاء DatePickerDialog
    DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            // يتم تعديل الشهر بسبب أن الشهور تبدأ من 0
            val dateString = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            onDateSelected(dateString)
        },
        year,
        month,
        day
    ).show()
}

fun getTodayDate(): String {
    val c = Calendar.getInstance()
    return "${c.get(Calendar.DAY_OF_MONTH)}/${c.get(Calendar.MONTH)+1}/${c.get(Calendar.YEAR)}"
}

fun formatTime(minutes: Int): String {
    val hours = TimeUnit.MINUTES.toHours(minutes.toLong())
    val remainingMinutes = minutes % 60
    return String.format("%02d:%02d", hours, remainingMinutes)
}