package event.countdown.TheNav

import event.countdown.Screens.AddEvent
import event.countdown.Screens.ClockAppScreen
import event.countdown.Screens.TheCalender
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@Composable
fun AppNavHost(navController: NavHostController){
//    val NavController= rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.HomeScreen.route
    ){
        composable(
            route=Screens.HomeScreen.route
        ) {
            ClockAppScreen(
                navController
            )
        }
        composable(
            route=Screens.CalendarScreen.route
        ) {
            TheCalender(
                navController
            )
        }
        composable(
            route=Screens.AddEventScreen.route
        ) {
            AddEvent(
                navController
            )
        }
    }
}