package event.countdown.Screens.Daily_Advices_Screen

import android.content.Context
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

// إنشاء DataStore باستخدام الامتداد على Context
private val Context.dataStore by preferencesDataStore(name = "settings")

class DataStoreManager(private val context: Context) {

    companion object {
        val LAST_ADVICE_TIME_KEY = longPreferencesKey("last_advice_time")
    }

    // دالة لحفظ الوقت
    suspend fun saveLastAdviceTime(timeMillis: Long) {
        context.dataStore.edit { settings ->
            settings[LAST_ADVICE_TIME_KEY] = timeMillis
        }
    }

    // دالة لاسترجاع الوقت
    suspend fun getLastAdviceTime(): Long {
        val preferences = context.dataStore.data.first()
        return preferences[LAST_ADVICE_TIME_KEY] ?: 0L
    }
}