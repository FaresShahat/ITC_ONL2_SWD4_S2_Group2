@file:Suppress("UnusedMaterial3ScaffoldPaddingParameter")

package event.countdown.Screens

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
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
import androidx.compose.foundation.border


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TheCalender(navController: NavHostController) {
    val context = LocalContext.current
    var displayedMonth by remember { mutableStateOf(YearMonth.now()) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0D1B2A),
                        Color(0xFF1B263B)
                    )
                )
            )
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Calendar",
                            color = Color.White,
                            fontSize = 20.sp
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                val currentDate = LocalDate.now()
                val firstDay = displayedMonth.atDay(1)
                val daysInMonth = displayedMonth.lengthOfMonth()
                val firstDayOfMonthIndex = (firstDay.dayOfWeek.value % 7)
                val displayMonthName = firstDay.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
                val displayYear = firstDay.year

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(onClick = { displayedMonth = displayedMonth.minusMonths(1) }) {
                        Icon(
                            imageVector = Icons.Default.ChevronLeft,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                    Text(
                        text = "$displayMonthName $displayYear",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    IconButton(onClick = { displayedMonth = displayedMonth.plusMonths(1) }) {
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach {
                        Text(
                            text = it,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                LazyVerticalGrid(columns = GridCells.Fixed(7), modifier = Modifier.padding(top = 8.dp)) {
                    items(firstDayOfMonthIndex) {
                        Spacer(modifier = Modifier.size(50.dp))
                    }
                    items(daysInMonth) { index ->
                        val dayNumber = index + 1
                        val isToday = (
                                dayNumber == currentDate.dayOfMonth &&
                                        displayedMonth.year == currentDate.year &&
                                        displayedMonth.month == currentDate.month
                                )
                        val isSelected = (
                                selectedDate.year == displayedMonth.year &&
                                        selectedDate.month == displayedMonth.month &&
                                        selectedDate.dayOfMonth == dayNumber
                                )
                        val targetColor = if (isSelected) Color(0xFF00BCD4) else Color.Transparent
                        val backgroundColor by animateColorAsState(targetValue = targetColor)
                        val borderColor = if (isToday && !isSelected) Color.Blue else Color.Transparent

                        Card(
                            modifier = Modifier
                                .padding(4.dp)
                                .size(50.dp)
                                .clickable {
                                    selectedDate = LocalDate.of(
                                        displayedMonth.year,
                                        displayedMonth.month,
                                        dayNumber
                                    )
                                    Toast.makeText(context, "Selected day: $dayNumber", Toast.LENGTH_SHORT).show()
                                },
                            shape = CircleShape,
                            colors = CardDefaults.cardColors(containerColor = backgroundColor),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape)
                                    .border(width = 2.dp, color = borderColor, shape = CircleShape),
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
}

@Preview(showSystemUi = true)
@Composable
fun PreviewCalendar() {
    val navController = rememberNavController()
    TheCalender(navController)
}
