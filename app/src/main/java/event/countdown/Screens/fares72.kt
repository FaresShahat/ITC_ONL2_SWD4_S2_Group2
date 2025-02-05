package event.countdown.Screens

import android.annotation.SuppressLint
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import event.countdown.Model.SpinnerViewModel
import event.countdown.ui.theme.Purple500


@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun fares() {
    val sampleList = mutableListOf("Sample 1", "Sample 2", "Sample 3", "Sample 4", "Sample 5")
    var sampleName: String by remember { mutableStateOf(sampleList[0]) }
    var expanded by remember { mutableStateOf(false) }
    val transitionState = remember {
        MutableTransitionState(expanded).apply {
            targetState = !expanded
        }
    }

    val transition = updateTransition(targetState = transitionState, label = "transition")
    val arrowRotationDegree by transition.animateFloat({
        tween(durationMillis = 300)
    }, label = "rotationDegree") {
        if (expanded) 180f else 0f
    }

    val context = LocalContext.current
    val viewModel: SpinnerViewModel = viewModel()
    val dateTime = viewModel.time.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth().background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(Purple500)
                .padding(15.dp)
        ) {
            Text(
                text = "Spinner & DateTime",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Column {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Spinner",
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .clickable {
                            expanded = !expanded
                        }
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = sampleName,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .height(30.dp)
                    )
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "Spinner",
                        modifier = Modifier.rotate(arrowRotationDegree)
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false})

                    { sampleList.forEach { data ->
                        DropdownMenuItem(
                            onClick = {
                                expanded = false
                                sampleName = data})
                        {
                            Text(text = data)
                        }
                    }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Calender Date & Time Picker",
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(20.dp))

                TextButton(
                    onClick = {
                        viewModel.selectDateTime(context)
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(Purple500)
                        .padding(5.dp)
                ) {
                    Text(text = "Select Date", color = Color.White)
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(text = dateTime.value ?: "No Time Set")
            }
        }
    }
}

fun DropdownMenuItem(onClick: () -> Unit, interactionSource: @Composable () -> Unit) {
    TODO("Not yet implemented")
}




//package event.countdown.Screens
//
//import android.annotation.SuppressLint
//import androidx.compose.foundation.Canvas
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.drawscope.Stroke
//import androidx.compose.ui.graphics.nativeCanvas
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import java.text.SimpleDateFormat
//import java.util.*
//import kotlin.math.cos
//import kotlin.math.sin
//
//@OptIn(ExperimentalMaterial3Api::class)
//@SuppressLint("UnusedTransitionTargetStateParameter", "SimpleDateFormat")
//@Composable
//fun fares2() {
//    val context = LocalContext.current
//    var is24HourFormat by remember { mutableStateOf(true) }
//    val currentDate = remember {
//        SimpleDateFormat("dd MMMM yyyy - EEEE", Locale("ar")).format(Date())
//    }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Sectograph", fontWeight = FontWeight.Bold) },
//                actions = {
//                    IconButton(onClick = { /* TODO: Add settings logic */ }) {
//                        Icon(Icons.Default.MoreVert, contentDescription = "Menu")
//                    }
//                }
//            )
//        },
//        floatingActionButton = {
//            FloatingActionButton(
//                onClick = { /* TODO: Handle Add Event */ },
//                containerColor = Color(0xFF6200EA),
//                shape = CircleShape
//            ) {
//                Icon(Icons.Default.Add, contentDescription = "Add Event", tint = Color.White)
//            }
//        }
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.Black)
//                .padding(paddingValues),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // Row for Date Navigation and Display
//            Row(
//                horizontalArrangement = Arrangement.SpaceBetween,
//                modifier = Modifier.fillMaxWidth().padding(16.dp)
//            ) {
//                IconButton(onClick = { /* TODO: Go to previous day */ }) {
//                    Icon(Icons.Default.ArrowBack, contentDescription = "Previous Day", tint = Color.White)
//                }
//                Text(currentDate, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
//                IconButton(onClick = { /* TODO: Go to next day */ }) {
//                    Icon(Icons.Default.ArrowForward, contentDescription = "Next Day", tint = Color.White)
//                }
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // Clock Visualization with Accurate Angle
//            val calendar = Calendar.getInstance()
//            val hours = calendar.get(Calendar.HOUR_OF_DAY)
//            val minutes = calendar.get(Calendar.MINUTE)
//            CircularClock(hours, minutes)
//
//            Spacer(modifier = Modifier.height(20.dp))
//
//            // Button Row for 12/24 Hour Format
//            Row(
//                horizontalArrangement = Arrangement.Center,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Button(onClick = { is24HourFormat = !is24HourFormat }) {
//                    Text(if (is24HourFormat) "12/24" else "24/12")
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun CircularClock(hours: Int, minutes: Int) {
//    val totalMinutes = hours * 60 + minutes
//    val angle = (totalMinutes.toFloat() / (12 * 60)) * 360f // تحويل الوقت إلى زاوية بالنسبة لدائرة 360°
//
//    Canvas(modifier = Modifier.size(200.dp)) {
//        // Draw outer circle
//        drawCircle(color = Color.Gray, style = Stroke(width = 8f))
//
//        // Draw red line for current time
//        drawLine(
//            color = if (hours >= 6 && hours < 18) Color.Red else Color.Cyan, // صباحًا أحمر، مساءً أزرق
//            start = center,
//            end = Offset(
//                x = center.x + 80 * cos(Math.toRadians((angle - 90).toDouble())).toFloat(),
//                y = center.y + 80 * sin(Math.toRadians((angle - 90).toDouble())).toFloat()
//            ),
//            strokeWidth = 4f
//        )
//
//        // Display the current time in the center
//        drawContext.canvas.nativeCanvas.apply {
//            drawText(
//                String.format("%02d:%02d", hours, minutes),
//                center.x - 40,
//                center.y + 10,
//                android.graphics.Paint().apply {
//                    color = android.graphics.Color.WHITE
//                    textSize = 40f
//                    textAlign = android.graphics.Paint.Align.CENTER
//                }
//            )
//        }
//    }
//}