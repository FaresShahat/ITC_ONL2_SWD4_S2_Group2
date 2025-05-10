package com.example.mvvmandcroforproject.Data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Upsert
    suspend fun insertEvent(event: Event)

    @Delete
    suspend fun deleteEvent(event: Event)

    @Query("SELECT * FROM Events ORDER BY name ASC")
    fun getEventsOrderedByName(): Flow<List<Event>>
    @Query("SELECT * FROM Events ORDER BY date ASC")
    fun getEventsOrderedByDate(): Flow<List<Event>>

}