package event.countdown.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun AddEvent(navController: NavHostController){
    var showDialog by remember { mutableStateOf(true) }
    val events = remember { mutableStateMapOf<String, MutableList<String>>() }
    val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("ar"))
    var selectedDate by remember { mutableStateOf(Calendar.getInstance().time) }
    if (showDialog) {
        AddEventDialog(
            onAddEvent = { event ->
                val currentDateKey = dateFormat.format(selectedDate)
                events.getOrPut(currentDateKey) { mutableListOf() }.add(event)
                showDialog = false
            },
            onCancel = { showDialog = false },
            navController
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventDialog(onAddEvent: (String) -> Unit, onCancel: () -> Unit,navController: NavHostController) {
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
                Text("Add")
            }
        },
        dismissButton = {
            Button(
                onClick ={navController.popBackStack()}
            ) {
                Text("Cancel")
            }
        },
        title = { Text("Add Event", fontWeight = FontWeight.Bold) },
        text = {
            Column {
                Text("Add Event name: ")
                TextField(
                    value = eventText,
                    onValueChange = { eventText = it },
                    singleLine = true,
                    colors =  TextFieldDefaults.colors()/*textFieldColors( Color.White, containerColor = Color.DarkGray)*/
                )
            }
        },
        containerColor = Color(0xFF333333)
    )
}
