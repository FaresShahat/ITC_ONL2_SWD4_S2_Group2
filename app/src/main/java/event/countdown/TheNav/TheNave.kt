package event.countdown.TheNav

import event.countdown.Screens.AddEvent
import event.countdown.Screens.ClockAppScreen
import event.countdown.Screens.TheCalender
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import event.countdown.Data.EventViewModel
import event.countdown.Model.ClockViewModel
import event.countdown.Screens.AboutTheApp
import event.countdown.Screens.ApplicationSupport
import event.countdown.Screens.Daily_Advices_Screen.DailyAdviceScreen
import event.countdown.Screens.InterfaceColoring
import event.countdown.Screens.InterfaceElementColor
import event.countdown.Screens.SettingScreen
import event.countdown.Screens.SuggestOrProblem


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
            val viewModel: EventViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()
            ClockAppScreen(
                navController,
                state = state,
                onEvent = viewModel::onEvent
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
            val viewModel: EventViewModel = hiltViewModel()
            AddEvent(
                navController,
                viewModel = viewModel
            )
        }
        composable(route = Screens.InterfaceElementColor.route) { InterfaceElementColor(navController) }
        composable(route = Screens.Setting.route) { SettingScreen(navController) }
        composable(route = Screens.InterfaceColoring.route) { InterfaceColoring(navController) }
        composable(route = Screens.SuggestOrProblem.route) { SuggestOrProblem(navController) }
        composable(route = Screens.ApplicationSupport.route) { ApplicationSupport(navController) }
        composable(route = Screens.AboutTheApp.route) { AboutTheApp(navController) }
        composable(route = Screens.DailyAdviceScreen.route) { DailyAdviceScreen( navController) }
    }
}