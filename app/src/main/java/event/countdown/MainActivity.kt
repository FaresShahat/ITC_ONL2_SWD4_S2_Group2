package event.countdown

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import event.countdown.Model.AddEventViewModel
import event.countdown.TheNav.AppNavHost
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            EventCountdownTheme {
//                Evennt()
//
//
//
//            }
            val navController = rememberNavController()
            val viewModel: AddEventViewModel = viewModel()
            AppNavHost(navController, viewModel)
         //   AppNavHost(navController,viewModel)
        }
    }
}
