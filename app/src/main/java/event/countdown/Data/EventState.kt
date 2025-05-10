package com.example.mvvmandcroforproject.Data

data class EventState(
    val events:List<Event> = emptyList(),
    val name:String?=null,
    val startTime:Int?=null,
    val endTime:Int?=null,
    val date:String?=null,
    val sate:String?=null,
    val isAddingContact:Boolean=false,
    val sortType: SortType=SortType.DATE
)
