@file:Suppress("UnusedMaterial3ScaffoldPaddingParameter")

package event.countdown.Screens

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEvent(navController: NavHostController) {
    var showDialog by remember { mutableStateOf(true) }
    var eventName by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf(8f) }
    var endTime by remember { mutableStateOf(9f) }
    if (showDialog) {
        AddEventDialog(
            eventName = eventName,
            startTime = startTime,
            endTime = endTime,
            onEventNameChange = { eventName = it },
            onStartTimeChange = { startTime = it },
            onEndTimeChange = { endTime = it },
            onAddEvent = {
                if (eventName.isNotBlank()) showDialog = false
            },
            onCancel = { navController.popBackStack() },
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
    val colors = MaterialTheme.colorScheme
    val context = LocalContext.current

    var eventDate by remember {
        val c = Calendar.getInstance()
        mutableStateOf("${c.get(Calendar.DAY_OF_MONTH)}/${c.get(Calendar.MONTH) + 1}/${c.get(Calendar.YEAR)}")
    }
    var showPicker by remember { mutableStateOf(false) }
    if (showPicker) {
        ShowDatePickerDialog(context) {
            eventDate = it
            showPicker = false
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF0D1B2A), Color(0xFF1B263B))
                )
            )
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = { Text("Add Event", color = Color.White, fontSize = 20.sp) },
                    navigationIcon = {
                        IconButton(onClick = onCancel) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent)
                )
            }
        ) { padding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(colors.surfaceVariant),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(Modifier.padding(20.dp)) {
                        OutlinedTextField(
                            value = eventName,
                            onValueChange = onEventNameChange,
                            label = { Text("Event Name") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                containerColor = colors.surface,
                                focusedBorderColor = colors.primary,
                                unfocusedBorderColor = colors.onSurfaceVariant,
                                cursorColor = colors.primary
                            ),
                            leadingIcon = { Icon(Icons.Default.Event, contentDescription = null, tint = colors.primary) }
                        )

                        Spacer(Modifier.height(24.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(colors.surface)
                                .clickable { showPicker = true }
                                .padding(12.dp)
                        ) {
                            Icon(Icons.Default.DateRange, contentDescription = null, tint = colors.primary)
                            Spacer(Modifier.width(8.dp))
                            Text("Date: $eventDate", color = colors.onSurface)
                        }

                        Spacer(Modifier.height(24.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.AccessTime, contentDescription = null, tint = colors.primary)
                            Spacer(Modifier.width(8.dp))
                            Text("Start Time", color = colors.onSurface, fontSize = 16.sp)
                        }
                        Text("Start: ${formatTime(startTime)}", color = colors.onSurface)
                        Slider(
                            value = startTime,
                            onValueChange = { onStartTimeChange(it.coerceAtMost(endTime)) },
                            valueRange = 0f..23f,
                            colors = SliderDefaults.colors(
                                thumbColor = colors.primary,
                                activeTrackColor = colors.primary
                            )
                        )

                        Spacer(Modifier.height(12.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.AccessTime, contentDescription = null, tint = colors.primary)
                            Spacer(Modifier.width(8.dp))
                            Text("End Time", color = colors.onSurface, fontSize = 16.sp)
                        }
                        Text("End: ${formatTime(endTime)}", color = colors.onSurface)
                        Slider(
                            value = endTime,
                            onValueChange = { onEndTimeChange(it.coerceAtLeast(startTime)) },
                            valueRange = 0f..23f,
                            colors = SliderDefaults.colors(
                                thumbColor = colors.primary,
                                activeTrackColor = colors.primary
                            )
                        )

                        Spacer(Modifier.height(24.dp))

                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TextButton(onClick = onCancel) {
                                Text("Cancel", color = colors.onSurfaceVariant)
                            }
                            Button(
                                onClick = onAddEvent,
                                enabled = eventName.isNotBlank(),
                                colors = ButtonDefaults.buttonColors(containerColor = colors.primary),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Text("Save", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ShowDatePickerDialog(context: Context, onDateSelected: (String) -> Unit) {
    val c = Calendar.getInstance()
    DatePickerDialog(
        context,
        { _, y, m, d -> onDateSelected("$d/${m + 1}/$y") },
        c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)
    ).show()
}

private fun formatTime(h: Float): String {
    val hh = h.toInt()
    val mm = ((h - hh) * 60).toInt()
    return "%02d:%02d".format(hh, mm)
}