package com.plcoding.alarmmanagerguide.ui.theme.AlarmManager

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import event.countdown.AlarmManager.Reciver.EventAlarmReceiver
import java.util.Date

object EventScheduler {
    @SuppressLint("MissingPermission")
    fun scheduleEventAlarm(context: Context, eventId: Int, timeInMillis: Long) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, EventAlarmReceiver::class.java).apply {
            putExtra("EVENT_ID", eventId)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            eventId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // التأكد أن الوقت لم يمر
        if (timeInMillis > System.currentTimeMillis()) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                timeInMillis,
                pendingIntent
            )
            Log.d("EventScheduler", "تم ضبط الإشعار للحدث ID: $eventId عند: ${Date(timeInMillis)}")
        } else {
            Log.e("EventScheduler", "لم يتم ضبط الإشعار، التوقيت غير صالح!")
        }
    }
}
