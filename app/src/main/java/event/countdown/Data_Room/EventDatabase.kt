package event.countdown.Data_Room

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Date

@Database(entities = [Event::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class EventDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao

    companion object {
        @Volatile
        private var INSTANCE: EventDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): EventDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EventDatabase::class.java,
                    "event_database"
                )
                    .addCallback(EventDatabaseCallback(scope))  //  ودي بكوم موجوده عند فتح التطبيق تهيئة البيانات الافتراضية
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class EventDatabaseCallback(private val scope: CoroutineScope) :
        RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val eventDao = database.eventDao()
                    eventDao.insert(
                        Event(
                            title = "في الكليه",
                            description = "محاضره الساعة 10 صباحًا",
                            timestamp = Date()
                        )
                    )
                }
            }
        }
    }
}





























//import android.content.Context
//import androidx.room.*
//import androidx.sqlite.db.SupportSQLiteDatabase
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.launch
//import java.util.Date
//
//@Database(entities = [Event::class], version = 1, exportSchema = false)
//@TypeConverters(Converters::class)
//abstract class EventDatabase : RoomDatabase() {
//    abstract fun eventDao(): EventDao
//
//    companion object {
//        @Volatile
//        private var INSTANCE: EventDatabase? = null
//
//        fun getDatabase(context: Context, scope: CoroutineScope): EventDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    EventDatabase::class.java,
//                    "event_database"
//                )
//                    .addCallback(EventDatabaseCallback(scope))  //  ودي بكوم موجوده عند فتح التطبيق تهيئة البيانات الافتراضية
//                    .build()
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
//
//    private class EventDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
//        override fun onCreate(db: SupportSQLiteDatabase) {
//            super.onCreate(db)
//            INSTANCE?.let { database ->
//                scope.launch {
//                    val eventDao = database.eventDao()
//                    eventDao.insert(Event(title = "في الكليه", description = "محاضره الساعة 10 صباحًا", timestamp = Date()))
//                }
//            }
//        }
//    }
//}
