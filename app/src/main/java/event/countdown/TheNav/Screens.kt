package event.countdown.TheNav

sealed class Screens(val route:String){
    data object  HomeScreen:Screens(route = "HomeScreen")
    data object  CalendarScreen:Screens(route="CalendarScreen")
    data object  AddEventScreen:Screens(route="AddEventScreen")
    object InterfaceElementColor : Screens("InterfaceElementColor")
    object Setting : Screens("Setting")
    object InterfaceColoring : Screens("InterfaceColoring")
    object SuggestOrProblem : Screens("SuggestOrProblem")
    object ApplicationSupport : Screens("ApplicationSupport")
    object AboutTheApp : Screens("AboutTheApp")
}