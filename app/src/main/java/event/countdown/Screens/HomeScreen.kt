@file:Suppress("UNUSED_EXPRESSION")


package event.countdown.Screens
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import event.countdown.R
import event.countdown.TheNav.Screens
import kotlinx.coroutines.delay
import java.util.Calendar
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClockAppScreen(
//    onItemClick: (Any?) -> Unit
    navController: NavHostController
) {
    var time by remember { mutableStateOf(Calendar.getInstance()) }

    LaunchedEffect(Unit) {
        while (true) {
            time = Calendar.getInstance()
            delay(1000)
        }
    }
    val hours = time.get(Calendar.HOUR) * 30f
    val minutes = time.get(Calendar.MINUTE) * 6f
    val seconds = time.get(Calendar.SECOND) * 6f
    val TheDay=time.get(Calendar.DAY_OF_MONTH)
    val TheDate="%02d - %02d - %02d".format(time.get(Calendar.DAY_OF_MONTH), /**time.get(Calendar.MONTH)*/2,time.get(Calendar.YEAR))
    val formattedTime = "%02d:%02d".format(time.get(Calendar.HOUR), time.get(Calendar.MINUTE))
    var showDialog by remember { mutableStateOf(false) }
    val events = remember { mutableStateMapOf<String, MutableList<String>>() }
    var selectedDate by remember { mutableStateOf(Calendar.getInstance().time) }
    val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("ar"))

    //***************************************************
    var currentDate by remember { mutableStateOf(LocalDate.now()) }
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    LaunchedEffect(Unit) {
        while (true) {
            time = Calendar.getInstance()
            delay(1000)
        }
    }

//    val formattedTime = "%02d:%02d".format(time.get(Calendar.HOUR), time.get(Calendar.MINUTE))

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screens.AddEventScreen.route) }) {
                Icon(
                    modifier=Modifier.shadow(100.dp).size(60.dp).background(color =Color.Cyan),
                    painter = painterResource(id = R.drawable.add),
                    contentDescription = "FAB Action"
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize().background(Color.Black)
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 🟡 App Bar

            TopAppBar(
                title = { Text("Spectrograph", color = Color.White) },
                navigationIcon = { IconButton(modifier = Modifier.size(60.dp),onClick = {

                }) { Icon(painterResource(id = R.drawable.menu_icon), "Menu", tint = Color.White) } },
                actions = { IconButton(onClick = {}) { Icon(painterResource(id = R.drawable.vertical_ellipsis_icon), "Settings" , tint = Color.White)} },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.DarkGray)
            )

            // 🟡 Date + Sun Icon
            Row(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
//            Spacer(modifier = Modifier.width(170.dp))
                Text("2 - ${time.get(Calendar.DAY_OF_MONTH)}", color = Color.White, fontSize = 18.sp)
                Spacer(modifier = Modifier.width(10.dp))
//            Box(
//                Modifier.clip(CircleShape).background(color = Color.Blue)
//            ){
//            Icon(painterResource(id = R.drawable.calendar), "Sun", tint = Color.White, modifier = Modifier.size(40.dp))
//            }
                IconButton(
                    onClick = { navController.navigate(Screens.CalendarScreen.route)},
                    modifier = Modifier.size(35.dp).clip(CircleShape).background(color =Color.Blue)
                ) { Icon(painterResource(id = R.drawable.calendar), "Right", tint = Color.Cyan) }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 🟡 Clock UI
            Box(contentAlignment = Alignment.Center) {
                Canvas(modifier = Modifier.size(250.dp)) {
                    val centerX = size.width / 2
                    val centerY = size.height / 2
                    val radius = size.minDimension / 2

                    drawCircle(color = Color.DarkGray, radius = radius)

                    // Draw numbers & ticks
                    for (i in 1..12) {
                        val angle = Math.toRadians((i * 30 - 90).toDouble())
                        val x = centerX + Math.cos(angle) * (radius * 0.85)
                        val y = centerY + Math.sin(angle) * (radius * 0.85)

                        drawContext.canvas.nativeCanvas.drawText(
                            i.toString(),
                            x.toFloat(),
                            y.toFloat(),
                            Paint().apply {
                                textSize = 64f
                                color = android.graphics.Color.WHITE
                                textAlign = Paint.Align.CENTER
                            }
                        )
                    }

                    // Hour Hand
                    rotate(degrees = hours, pivot = center) {
                        drawLine(Color.White, center, center.copy(y = centerY - radius * 0.5f), strokeWidth = 8f)
                    }

                    // Minute Hand
                    rotate(degrees = minutes, pivot = center) {
                        drawLine(Color.LightGray, center, center.copy(y = centerY - radius * 0.7f), strokeWidth = 6f)
                    }

                    // Second Hand (Red)
                    rotate(degrees = seconds, pivot = center) {
                        drawLine(Color.Red, center, center.copy(y = centerY - radius * 0.9f), strokeWidth = 3f)
                        drawCircle(Color.Red, radius = 8f, center = center.copy(y = centerY - radius * 0.9f))
                    }

                    // Center Circle
                    drawCircle(color = Color.White, radius = 10f, center = center)
                }

                // Digital Time in the Middle
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color.Blue),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = formattedTime, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 🟡 Navigation Arrows
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                IconButton(onClick = {}) { Icon(painterResource(id = R.drawable.ic_launcher_foreground), "Left", tint = Color.White) }
                IconButton(onClick = {}) { Icon(painterResource(id = R.drawable.wall_clock), "Right", tint = Color.White) }
            }

            Spacer(modifier = Modifier.height(20.dp))

//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceEvenly
//            ) {
//                FloatingActionButton(
//                    onClick = {},
//                    modifier = Modifier.padding(16.dp),
//                    containerColor = Color.DarkGray
//                ) {
//                    Icon(
//                        painterResource(id = R.drawable.left_arrow),
//                        "Calendar",
//                        tint = Color.White
//                    )
//                }
//                FloatingActionButton(
//                    onClick = {}, containerColor = Color.DarkGray
//                )
//                {
//                    Icon(
//                        painterResource(id = R.drawable.right_arrow),
//                        "Add Task",
//                        tint = Color.White,
//                        modifier = Modifier.size(40.dp)
//                    )
//                }
//
//            }
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween // Space between text and arrow
            ) {
                IconButton(
                    onClick = {
                        currentDate = currentDate.minusDays(1)
                    },
                    modifier = Modifier.size(25.dp)
                ) { Icon(painterResource(id = R.drawable.left_arrow), "Right", tint = Color.White) }
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = currentDate.format(formatter),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                IconButton(
                    onClick = {
                        currentDate = currentDate.plusDays(1)
                    },
                    modifier = Modifier.size(25.dp)
                ) { Icon(painterResource(id = R.drawable.right_arrow), "Right", tint = Color.White) }
            }
//        floatingActionButton = @androidx.compose.runtime.Composable {
//            FloatingActionButton(
//                onClick = { showDialog = true },
//                containerColor = MaterialTheme.colorScheme.primary,
//                shape = CircleShape
//            ) {
//                Text("+", fontSize = 24.sp, color = Color.White, fontWeight = FontWeight.Bold)
//            }
//        }
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
    }
}
