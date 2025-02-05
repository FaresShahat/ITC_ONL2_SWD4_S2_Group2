package event.countdown.Screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SimpleDateFormat")
@Composable
fun Evennt() {
    val context = LocalContext.current
    var selectedDate by remember { mutableStateOf(Calendar.getInstance().time) }
    var showDialog by remember { mutableStateOf(false) }
    val events = remember { mutableStateMapOf<String, MutableList<String>>() }
    val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("ar"))

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Event Countdown", modifier = Modifier.fillMaxWidth(), textAlign = androidx.compose.ui.text.style.TextAlign.Center, fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { /* TODO: Handle Settings */ }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            ) {
                Text("+", fontSize = 24.sp, color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // عرض التاريخ الحالي مع أزرار التنقل
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { selectedDate = changeDate(selectedDate, -1) }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Previous Day", tint = Color.White)
                }
                Text(
                    text = dateFormat.format(selectedDate),
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = { selectedDate = changeDate(selectedDate, 1) }) {
                    Icon(Icons.Default.ArrowForward, contentDescription = "Next Day", tint = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ساعة دائرية تعرض الوقت الحالي
            CircularClock()

            Spacer(modifier = Modifier.height(20.dp))

            // عرض قائمة الأحداث لهذا اليوم
            Text(
                "الأحداث:",
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            val currentDayEvents = events[dateFormat.format(selectedDate)] ?: mutableListOf()
            if (currentDayEvents.isEmpty()) {
                Text(
                    "لا توجد أحداث لهذا اليوم",
                    fontSize = 16.sp,
                    color = Color.LightGray
                )
            } else {
                currentDayEvents.forEach { event ->
                    Text(
                        "- $event",
                        fontSize = 16.sp,
                        color = Color.LightGray
                    )
                }
            }
        }
    }

    // Dialog لإضافة حدث جديد
    if (showDialog) {
        AddEventDialog(
            onAddEvent = { event ->
                val currentDateKey = dateFormat.format(selectedDate)
                events.getOrPut(currentDateKey) { mutableListOf() }.add(event)
                showDialog = false
            },
            onCancel = { showDialog = false }
        )
    }
}

@Composable
fun CircularClock() {
    val currentTime = remember { mutableStateOf(Calendar.getInstance()) }

    // تحديث الوقت كل ثانية
    LaunchedEffect(Unit) {
        while (true) {
            currentTime.value = Calendar.getInstance()
            delay(1000)  // يحدث الوقت كل ثانية
        }
    }

    Box(
        modifier = Modifier
            .size(200.dp)
            .background(Color.DarkGray, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        val hour = currentTime.value.get(Calendar.HOUR_OF_DAY)
        val minute = currentTime.value.get(Calendar.MINUTE)
        val second = currentTime.value.get(Calendar.SECOND)

        Text(
            text = String.format("%02d:%02d:%02d", hour, minute, second),
            fontSize = 24.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventDialog(onAddEvent: (String) -> Unit, onCancel: () -> Unit) {
    var eventText by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onCancel,
        confirmButton = {
            TextButton(onClick = {
                if (eventText.isNotBlank()) {
                    onAddEvent(eventText)
                    eventText = ""
                }
            }) {
                Text("إضافة")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text("إلغاء")
            }
        },
        title = { Text("إضافة حدث جديد", fontWeight = FontWeight.Bold) },
        text = {
            Column {
                Text("أدخل اسم الحدث:")
                TextField(
                    value = eventText,
                    onValueChange = { eventText = it },
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors( Color.White, containerColor = Color.DarkGray)
                )
            }
        },
        containerColor = Color(0xFF333333)
    )
}

private fun changeDate(date: Date, days: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.time = date
    calendar.add(Calendar.DAY_OF_MONTH, days)
    return calendar.time
}

