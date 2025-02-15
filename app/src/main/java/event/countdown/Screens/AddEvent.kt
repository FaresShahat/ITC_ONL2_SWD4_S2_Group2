package event.countdown.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import event.countdown.Model.AddEventViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AddEvent(navController: NavHostController, viewModel: AddEventViewModel = viewModel()) {
    var showDialog by remember { mutableStateOf(true) }
    val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("ar"))
    var selectedDate by remember { mutableStateOf(Calendar.getInstance().time) }

    if (showDialog) {
        AddEventDialog(
            onAddEvent = { event ->
                val currentDateKey = dateFormat.format(selectedDate)
                viewModel.addEvent(currentDateKey, event)
                showDialog = false
            },
            onCancel = { showDialog = false },
            navController
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventDialog(onAddEvent: (String) -> Unit, onCancel: () -> Unit, navController: NavHostController) {
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
            Button(onClick = { navController.popBackStack() }) {
                Text("إلغاء")
            }
        },
        title = { Text("إضافة حدث", fontWeight = FontWeight.Bold) },
        text = {
            Column {
                Text("أدخل اسم الحدث:")

                TextField(
                    value = eventText,
                    onValueChange = { eventText = it },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.White,  // لون النص عندما يكون الحقل مفعّلًا
                        unfocusedTextColor = Color.White, // لون النص عندما يكون الحقل غير مفعّل
                        focusedContainerColor = Color.DarkGray, // لون الخلفية عندما يكون الحقل مفعّلًا
                        unfocusedContainerColor = Color.DarkGray // لون الخلفية عندما يكون الحقل غير مفعّل
                    )
                )
            }
        },
        containerColor = Color(0xFF333333)
    )
}
