
package event.countdown.Screens


import androidx.compose.runtime.Composable
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun TheCalender(navController: NavHostController){
    val context = LocalContext.current
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
    ) {PaddingValues->
        Column(Modifier.fillMaxSize().background(color = Color.Black).padding(PaddingValues)) {
            val today = LocalDate.now()
            val currentMonth = YearMonth.of(today.year, today.month)
            val daysInMonth = currentMonth.lengthOfMonth()
            val firstDayOfMonth = currentMonth.atDay(1).dayOfWeek.value % 7

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = today.month.getDisplayName(
                        TextStyle.FULL,
                        Locale.getDefault()
                    ) + " " + today.year,
                    style = MaterialTheme.typography.headlineMedium,
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
                ){
                    items(firstDayOfMonth) {
                        Spacer(modifier = Modifier.size(40.dp))
                    }
                    //daysInMonth [0==>28,29,30]
                    items(daysInMonth) { day ->
                        val isToday = day + 1 == today.dayOfMonth
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(
                                    if (isToday) Color.Blue else Color.Transparent,
                                    shape = CircleShape
                                )
                                .clickable {
                                    //To handel the Data
                                    Toast.makeText(context , "Selected day: ${day + 1}", Toast.LENGTH_SHORT).show()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${day + 1}",
                                color = Color.White,//if (isToday) Color.White else Color.Black
                                fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }
        }
    }
}




//@SuppressLint("UnusedTransitionTargetStateParameter")
//@Composable
//fun DateTimePicker() {
//    val context = LocalContext.current
//    val viewModel: SpinnerViewModel = viewModel()
//    val dateTime = viewModel.time.observeAsState()
//
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(Color.White),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center,
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(Color(0xFF6200EE))
//                .padding(15.dp)
//        ) {
//            Text(
//                text = "Date & Time Picker",
//                fontWeight = FontWeight.Bold,
//                fontSize = 20.sp,
//                color = Color.White
//            )
//        }
//        Spacer(modifier = Modifier.height(10.dp))
//
//        Spacer(modifier = Modifier.height(20.dp))
//        TextButton(
//            onClick = {
//                viewModel.selectDateTime(context)
//            },
//            modifier = Modifier
//                .clip(RoundedCornerShape(10.dp))
//                .background(Color(0xFF6200EE))
//                .padding(5.dp)
//        ) {
//            Text(text = "Select Date", color = Color.White)
//        }
//        Spacer(modifier = Modifier.height(10.dp))
//        Text(text = dateTime.value ?: "No Time Set")
//    }
//}

@Preview(
    showSystemUi = true
)
@Composable
fun out(){
    val navController = rememberNavController()
    TheCalender(navController)
}