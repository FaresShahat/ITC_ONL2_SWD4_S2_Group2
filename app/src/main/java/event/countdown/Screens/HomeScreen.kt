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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import event.countdown.R
import event.countdown.TheNav.Screens
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClockAppScreen(navController: NavHostController) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = false
        )
    }

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
        val formattedTime = "%02d:%02d".format(time.get(Calendar.HOUR), time.get(Calendar.MINUTE))

        val currentDay = time.get(Calendar.DAY_OF_MONTH)
        val TheDate = "%02d - %02d - %02d".format(
            time.get(Calendar.DAY_OF_MONTH),
            3,
            time.get(Calendar.YEAR)
        )

        var showDialog by remember { mutableStateOf(false) }
        val events = remember { mutableStateMapOf<String, MutableList<String>>() }
        var selectedDate by remember { mutableStateOf(Calendar.getInstance().time) }
        val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("ar"))

        var currentDate by remember { mutableStateOf(LocalDate.now()) }
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        // 🔹 Drawer State & Coroutine Scope
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(modifier = Modifier.width(250.dp)) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Event Count down (Free)",
                        modifier = Modifier.padding(16.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Divider()

                    // Drawer Menu Items
                    DrawerItem(icon = R.drawable.calendar, text = "Calendar") {
                        scope.launch { drawerState.close() }
                        navController.navigate(Screens.CalendarScreen.route)
                    }
                    DrawerItem(icon = R.drawable.palette, text = "Interface Element Color") {
                        scope.launch { drawerState.close() }
                        navController.navigate(Screens.InterfaceElementColor.route)
                    }
                    DrawerItem(icon = R.drawable.settings, text = "Settings") {
                        scope.launch { drawerState.close() }
                        navController.navigate(Screens.Setting.route)
                    }
                    DrawerItem(icon = R.drawable.sync, text = "Interface Coloring") {
                        scope.launch { drawerState.close() }
                        navController.navigate(Screens.InterfaceColoring.route)
                    }
                    DrawerItem(icon = R.drawable.help, text = "Suggest or Problem") {
                        scope.launch { drawerState.close() }
                        navController.navigate(Screens.SuggestOrProblem.route)
                    }
                    DrawerItem(icon = R.drawable.support, text = "Application Support") {
                        scope.launch { drawerState.close() }
                        navController.navigate(Screens.ApplicationSupport.route)
                    }
                    DrawerItem(icon = R.drawable.info, text = "About the App") {
                        scope.launch { drawerState.close() }
                        navController.navigate(Screens.AboutTheApp.route)
                    }
                }
            }
        ){
            Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Spectrograph",
                                color = Color.White,
                                fontSize = 20.sp
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(painterResource(id = R.drawable.menu_icon), contentDescription = "Menu", tint = Color.White)
                            }
                        },
                        actions = {
                            IconButton(onClick = { }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.vertical_ellipsis_icon),
                                    contentDescription = "Settings",
                                    tint = Color.White
                                )
                            }
                        },
                        colors = TopAppBarDefaults.smallTopAppBarColors(
                            containerColor = Color.Transparent
                        )
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { navController.navigate(Screens.AddEventScreen.route) },
                        shape = CircleShape,
                        containerColor = Color(0xFFFF006E),
                        contentColor = Color.White
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.add),
                            contentDescription = "Add Event",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = TheDate,
                        color = Color.White,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(
                            onClick = { navController.navigate(Screens.CalendarScreen.route) },
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF0ABDE3))
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.calendar),
                                contentDescription = "Calendar",
                                tint = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Box(contentAlignment = Alignment.Center) {
                        Canvas(modifier = Modifier.size(250.dp)) {
                            val centerX = size.width / 2
                            val centerY = size.height / 2
                            val radius = size.minDimension / 2

                            drawCircle(color = Color(0xFF2C2C2C), radius = radius)

                            for (i in 1..12) {
                                val angle = Math.toRadians((i * 30 - 90).toDouble())
                                val x = centerX + Math.cos(angle) * (radius * 0.85)
                                val y = centerY + Math.sin(angle) * (radius * 0.85)

                                drawContext.canvas.nativeCanvas.drawText(
                                    i.toString(),
                                    x.toFloat(),
                                    y.toFloat(),
                                    Paint().apply {
                                        textSize = 50f
                                        color = android.graphics.Color.WHITE
                                        textAlign = Paint.Align.CENTER
                                    }
                                )
                            }

                            rotate(degrees = hours, pivot = center) {
                                drawLine(
                                    Color.White,
                                    center,
                                    center.copy(y = centerY - radius * 0.5f),
                                    strokeWidth = 8f
                                )
                            }
                            rotate(degrees = minutes, pivot = center) {
                                drawLine(
                                    Color.LightGray,
                                    center,
                                    center.copy(y = centerY - radius * 0.7f),
                                    strokeWidth = 6f
                                )
                            }
                            rotate(degrees = seconds, pivot = center) {
                                drawLine(
                                    Color.Red,
                                    center,
                                    center.copy(y = centerY - radius * 0.9f),
                                    strokeWidth = 3f
                                )
                                drawCircle(
                                    Color.Red,
                                    radius = 6f,
                                    center = center.copy(y = centerY - radius * 0.9f)
                                )
                            }
                            drawCircle(color = Color.White, radius = 8f, center = center)
                        }

                        Box(
                            modifier = Modifier
                                .size(70.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFFF006E)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = formattedTime,
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        IconButton(onClick = { }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                                contentDescription = "Left",
                                tint = Color.White
                            )
                        }
                        IconButton(onClick = { }) {
                            Icon(
                                painter = painterResource(id = R.drawable.wall_clock),
                                contentDescription = "Clock Icon",
                                tint = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            onClick = { currentDate = currentDate.minusDays(1) },
                            modifier = Modifier.size(25.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.left_arrow),
                                contentDescription = "Left",
                                tint = Color.White
                            )
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = currentDate.format(formatter),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        }
                        IconButton(
                            onClick = { currentDate = currentDate.plusDays(1) },
                            modifier = Modifier.size(25.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.right_arrow),
                                contentDescription = "Right",
                                tint = Color.White
                            )
                        }
                    }

                    if (showDialog) {
                        AddEventDialog(
                            onAddEvent = { event ->
                                val currentDateKey = dateFormat.format(selectedDate)
                                events.getOrPut(currentDateKey) { mutableListOf() }.add(event)
                                showDialog = false
                            },
                            onCancel = { showDialog = false },
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DrawerItem(icon: Int, text: String, onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Icon(painter = painterResource(id = icon), contentDescription = null, tint = Color.Black, modifier = Modifier.size(30.dp))
        Spacer(modifier = Modifier.width(10.dp))
        Text(text, fontSize = 16.sp, color = Color.Black)
    }
}


