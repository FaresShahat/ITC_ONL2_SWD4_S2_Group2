package event.countdown.Data_Room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: Event)

    @Update
    suspend fun update(event: Event)

    @Delete
    suspend fun delete(event: Event)

    @Query("SELECT * FROM events ORDER BY timestamp ASC")
    fun getAllEvents(): Flow<List<Event>>

    @Query("SELECT * FROM events WHERE id = :eventId")
    suspend fun getEventById(eventId: Int): Event?
}




















//import androidx.room.*
//import kotlinx.coroutines.flow.Flow
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
//    suspend fun delete(event: Event)
//
//    @Query("SELECT * FROM events ORDER BY timestamp ASC")
//    fun getAllEvents(): Flow<List<Event>>
//
//    @Query("SELECT * FROM events WHERE id = :eventId")
//    suspend fun getEventById(eventId: Int): Event?
//}
