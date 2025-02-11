package event.countdown.TheNav

sealed class Screens(val route:String){
    data object  HomeScreen:Screens(route = "HomeScreen")
    data object  CalendarScreen:Screens(route="CalendarScreen")
    data object  AddEventScreen:Screens(route="AddEventScreen")
    data object  SettingScreen:Screens(route="SettingScreen")
}