package event.countdown.Screens.Daily_Advices_Screen

import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar

@Composable
fun DailyAdviceScreen(navController: NavHostController) {
    val context = LocalContext.current
    val dataStoreManager = remember { DataStoreManager(context) }
    val scope = rememberCoroutineScope()

    val adviceList = listOf(
        "النجاح هو الانتقال من فشل إلى فشل دون أن تفقد حماسك.",
        "لا تخشى من التنازل عن الجيد للحصول على الأفضل.",
        "أنت لا تحتاج إلى رؤية السلم كله، فقط ابدأ بتسلق الدرجة الأولى.",
        // ... باقي النصائح
        "كن أنت البطل الذي تنتظره."
    )

    // دوال مساعدة لتنسيق الوقت
    fun formatTime(millis: Long): String {
        val seconds = (millis / 1000) % 60
        val minutes = (millis / (1000 * 60)) % 60
        val hours = (millis / (1000 * 60 * 60)) % 24
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    fun calculateInitialTime(lastTime: Long): String {
        val currentTime = Calendar.getInstance().timeInMillis
        val twentyFourHours = 24 * 60 * 60 * 1000L
        val nextTime = lastTime + twentyFourHours
        val remaining = nextTime - currentTime
        return if (remaining <= 0) "00:00:00" else formatTime(remaining)
    }

    // تعريف المتغيرات بدون استخدام rememberSaveable للوقت الذي نريد حفظه بشكل دائم
    var currentAdviceIndex by rememberSaveable { mutableStateOf(0) }
    var lastAdviceTime by remember { mutableStateOf(0L) }
    var canShowNewAdvice by remember { mutableStateOf(false) }
    var timeRemaining by remember { mutableStateOf("00:00:00") }

    // عند بدء الشاشة، استرجع القيمة المخزنة من DataStore
    LaunchedEffect(Unit) {
        val storedTime = dataStoreManager.getLastAdviceTime()
        lastAdviceTime = if (storedTime == 0L) {
            // في حال عدم وجود قيمة محفوظة مسبقاً، نحفظ الوقت الحالي
            val now = Calendar.getInstance().timeInMillis
            scope.launch { dataStoreManager.saveLastAdviceTime(now) }
            now
        } else {
            storedTime
        }
        timeRemaining = calculateInitialTime(lastAdviceTime)
    }

    // تحديث العد التنازلي كل ثانية
    LaunchedEffect(lastAdviceTime) {
        while (true) {
            delay(1000)
            val currentTime = Calendar.getInstance().timeInMillis
            val twentyFourHours = 24 * 60 * 60 * 1000L
            val nextTime = lastAdviceTime + twentyFourHours
            val remaining = nextTime - currentTime

            if (remaining <= 0) {
                canShowNewAdvice = true
                timeRemaining = "00:00:00"
            } else {
                timeRemaining = formatTime(remaining)
            }
        }
    }

    // دالة لتحديث النصيحة والوقت عند الضغط على الزر
    fun updateAdvice() {
        currentAdviceIndex = (currentAdviceIndex + 1) % adviceList.size
        val now = Calendar.getInstance().timeInMillis
        lastAdviceTime = now
        canShowNewAdvice = false

        // حفظ الوقت الجديد في DataStore
        scope.launch { dataStoreManager.saveLastAdviceTime(now) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "نصيحة اليوم",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = adviceList[currentAdviceIndex],
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 24.dp),
            textAlign = TextAlign.Center
        )

        Button(
            onClick = { updateAdvice() },
            enabled = canShowNewAdvice,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("الحصول على نصيحة جديدة")
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "الوقت المتبقي للنصيحة القادمة:",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )

            Text(
                text = timeRemaining,
                style = MaterialTheme.typography.headlineSmall,
                color = if (canShowNewAdvice) Color.Green else MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            if (canShowNewAdvice) {
                Text(
                    text = "يمكنك الآن الحصول على نصيحة جديدة!",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Green,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
//package event.countdown.Screens
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Button
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.rememberNavController
//import java.util.Calendar
//
//@Composable
//fun DailyAdviceScreen(navController: NavHostController) {
//
//    var currentAdviceIndex by remember { mutableStateOf(0) }
//    var lastAdviceTime by remember { mutableStateOf(0L) }
//    var canShowNewAdvice by remember { mutableStateOf(true) }
//
//    fun updateAdvice() {
//        val currentTime = Calendar.getInstance().timeInMillis
//        val twentyFourHoursInMillis = 24 * 60 * 60 * 1000L
//
//        if (lastAdviceTime == 0L || currentTime - lastAdviceTime >= twentyFourHoursInMillis) {
//            currentAdviceIndex = (currentAdviceIndex + 1) % adviceList.size
//            lastAdviceTime = currentTime
//            canShowNewAdvice = false
//        }
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.Center, // هذا يضع المحتوى في منتصف الصفحة عمودياً
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = "نصيحة اليوم",
//            fontSize = 24.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color.Black,
//            modifier = Modifier.padding(bottom = 16.dp)
//        )
//
//        Text(
//            text = adviceList[currentAdviceIndex],
//            fontSize = 18.sp,
//            color = Color.DarkGray,
//            modifier = Modifier.padding(bottom = 24.dp),
//            textAlign = TextAlign.Center // جعل النص في منتصف العنصر أفقيًا
//        )
//
//        Button(
//            onClick = { updateAdvice() },
//            enabled = canShowNewAdvice
//        ) {
//            Text("نصيحه اخرى")
//        }
//
//        if (!canShowNewAdvice) {
//            Text(
//                text = "يمكنك رؤية النصيحة التالية بعد 24 ساعة",
//                color = Color.Gray,
//                modifier = Modifier.padding(top = 8.dp)
//            )
//        }
//    }
//}
//
//@Preview(
//    showBackground = true,
//    showSystemUi = true
//)
//@Composable
//fun DailyAdviceScreenPreview() {
//    DailyAdviceScreen(navController = rememberNavController())
//}

