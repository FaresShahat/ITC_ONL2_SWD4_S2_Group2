package event.countdown.Data_Room

import androidx.room.*
import java.util.Date

@Entity(tableName = "events")
data class Event(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
   // val description: String,
    //val timestamp: Date,
    val timeInMillis: Long,
    val isFromAppEvent: Boolean = false
)

//@Entity(tableName = "events")
//data class Event(
//    @PrimaryKey(autoGenerate = true) val id: Int = 0,
//    val title: String,
//    val startTime: Long, // التوقيت كمللي ثانية
//    val endTime: Long,
//    val eventDate: String // التاريخ كسلسلة نصية
//)













//import androidx.room.*
//import java.util.Date
//
//@Entity(tableName = "events")
//data class Event(
//    @PrimaryKey(autoGenerate = true) val id: Int = 0,
//    val title: String,
//    val description: String,
//    val timestamp: Date
//)
