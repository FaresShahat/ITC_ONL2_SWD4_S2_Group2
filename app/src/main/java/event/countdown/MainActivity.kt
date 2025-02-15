package event.countdown

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import event.countdown.TheNav.AppNavHost
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            EventCountdownTheme {
//                Evennt()
            val navController = rememberNavController()
           AppNavHost(navController)
        }
    }

   // private fun AppNavHost(navController: NavHostController) {

}


// val viewModel: AddEventViewModel = viewModel()
//AppNavHost(navController, viewModel)