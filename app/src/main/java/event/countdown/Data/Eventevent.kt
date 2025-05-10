package com.example.mvvmandcroforproject.Data

sealed interface Eventevent{
    object SaveEvent:Eventevent

    data class SetName(val name:String):Eventevent
    data class SetStartTime(val startTime:Int):Eventevent
    data class SetEndTim(val endTim:Int):Eventevent
    data class SetDate(val date:String):Eventevent

    object ShowDialog:Eventevent
    object HideDialog:Eventevent

    data class sortEvents(val sortType:SortType):Eventevent
    data class DeleteEvents(val event:Event):Eventevent
}