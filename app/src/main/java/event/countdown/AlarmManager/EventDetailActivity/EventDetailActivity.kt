package com.plcoding.alarmmanagerguide.ui.theme.AlarmManager.EventDetailActivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.plcoding.alarmmanagerguide.ui.eventdetail.EventDetailScreen
import event.countdown.Data_Room.EventDao
import event.countdown.Data_Room.EventDatabase

class EventDetailActivity : ComponentActivity() {
    private lateinit var eventDao: EventDao
    private var eventId: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        eventId = intent?.getIntExtra("EVENT_ID", -1) ?: -1
        if (eventId == -1) {
            finish()
            return
        }

        eventDao = EventDatabase.getInstance(this).eventDao()

        setContent {
            EventDetailScreen(eventId, eventDao, onClose = { finish() })
        }
    }
}
