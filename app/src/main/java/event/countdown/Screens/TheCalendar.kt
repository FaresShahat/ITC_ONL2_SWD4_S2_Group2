
package event.countdown.Screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun TheCalender(navController: NavHostController) {
    val context = LocalContext.current
    var selectedDay by remember { mutableStateOf(LocalDate.now().dayOfMonth) } // Track selected day

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("Calendar", color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(Color.DarkGray)
                )
                Divider(color = Color.Gray, thickness = 1.dp)
            }
        }
    ) { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .background(color = Color.Black)
                .padding(paddingValues)
        ) {
            val today = LocalDate.now()
            val currentMonth = YearMonth.of(today.year, today.month)
            val daysInMonth = currentMonth.lengthOfMonth()
            val firstDayOfMonth = currentMonth.atDay(1).dayOfWeek.value % 7

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "${today.month.getDisplayName(TextStyle.FULL, Locale.getDefault())} ${today.year}",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp),
                    color = Color.White
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach { day ->
                        Text(
                            text = day,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                    }
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(7),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    items(firstDayOfMonth) {
                        Spacer(modifier = Modifier.size(40.dp))
                    }
                    items(daysInMonth) { day ->
                        val dayNumber = day + 1
                        val isToday = dayNumber == today.dayOfMonth
                        val isSelected = selectedDay == dayNumber

                        // 🔹 Animated background color for selected day
                        val backgroundColor by animateColorAsState(
                            targetValue = if (isSelected) Color.Green else Color.Transparent,
                            label = "DaySelectionAnimation"
                        )

                        // 🔹 Border for today's date (if not selected)
                        val borderModifier = if (isToday && !isSelected) Modifier.border(2.dp, Color.Blue, CircleShape) else Modifier

                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .then(borderModifier) // Apply border only to today when it's not selected
                                .background(backgroundColor, shape = CircleShape)
                                .clickable {
                                    selectedDay = dayNumber
                                    Toast.makeText(context, "Selected day: $dayNumber", Toast.LENGTH_SHORT).show()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "$dayNumber",
                                color = Color.White,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewCalendar() {
    val navController = rememberNavController()
    TheCalender(navController)
}
