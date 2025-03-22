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
