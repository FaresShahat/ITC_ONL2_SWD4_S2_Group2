package com.example.mvvmandcroforproject.Data

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity("Events")
data class Event(
    @PrimaryKey
    val name:String,
    val startTime:Int?=null,
    val endTime:Int?=null,
    val date:String?=null
)
