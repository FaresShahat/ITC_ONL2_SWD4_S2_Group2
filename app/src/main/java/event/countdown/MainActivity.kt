package event.countdown

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import event.countdown.Screens.Evennt
import event.countdown.Screens.fares
import event.countdown.ui.theme.EventCountdownTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EventCountdownTheme {
                Evennt()



                }
            }
        }
    }

