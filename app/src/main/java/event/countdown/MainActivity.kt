package event.countdown

import android.app.Activity
import android.os.Bundle
import android.view.inputmethod.InputBinding
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.plcoding.alarmmanagerguide.ui.theme.AlarmManager.EventNotificationService
import event.countdown.Screens.Evennt
import event.countdown.Screens.fares
import event.countdown.TheNav.AppNavHost
import event.countdown.ui.theme.EventCountdownTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
       //    EventCountdownTheme {
         //    TestNotificationScreen()
          // }
          val navController = rememberNavController()
            AppNavHost(navController)
        }
    }
}


@Composable
fun TestNotificationScreen() {
    val context = LocalContext.current

    Button(
        onClick = {
            val eventId = Random.nextInt(1000)
            EventNotificationService().showNotification(context, eventId)
        },
        modifier = Modifier.padding(16.dp)
    ) {
        Text("اختبار الإشعار الآن")
    }
    }


