package event.countdown.Data_Room

import androidx.room.*
import androidx.room.Dao

@Dao
interface EventDao {
    @Insert
    suspend fun insert(event: Event): Long

    @Query("SELECT * FROM events WHERE id = :eventId")
    suspend fun getEvent(eventId: Int): Event?

    @Query("SELECT * FROM events")
    suspend fun getAllEvents(): List<Event>

    @Query("DELETE FROM events WHERE id = :eventId")
    suspend fun delete(eventId: Int)

    @Query("UPDATE events SET timeInMillis = :newTime WHERE id = :eventId")
    suspend fun updateTime(eventId: Int, newTime: Long)

}



































//
//import androidx.room.*
//
//@Dao
//interface EventDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insert(event: Event)
//
//    @Update
//    suspend fun update(event: Event)
//
//    @Delete
//    suspend fun delete(event: Int)
//
//  //  @Query("SELECT * FROM events ORDER BY timestamp ASC")
//    //fun getAllEvents(): Flow<List<Event>>
//
//    @Query("SELECT * FROM events WHERE id = :eventId")
//    suspend fun getEventById(eventId: Int): Event?
//    fun updateTime(eventId: Int, newTime: Long)
//    fun getEvent(eventId: Int): Event?
//    abstract fun getAllEvents(): Any
//}





















