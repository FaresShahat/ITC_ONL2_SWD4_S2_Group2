package com.plcoding.alarmmanagerguide.ui.theme.AlarmManager.EventDetailActivity

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import event.countdown.AlarmManager.Reciver.EventAlarmReceiver

fun cancelEventNotification(eventId: Int, context: Context) {
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager

    if (notificationManager != null) {
        notificationManager.cancel(eventId)
        Log.d("cancelEventNotification", "تم إلغاء الإشعار للحدث ID: $eventId")
    } else {
        Log.e("cancelEventNotification", "فشل في الحصول على NotificationManager!")
    }

    val intent = Intent(context, EventAlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context, eventId, intent,
        PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
    )

    if (pendingIntent != null && alarmManager != null) {
        alarmManager.cancel(pendingIntent)
        Log.d("cancelEventNotification", "تم إلغاء التنبيه للحدث ID: $eventId")
    } else {
        Log.e("cancelEventNotification", "لم يتم العثور على PendingIntent أو AlarmManager!")
    }
}

















//import android.app.AlarmManager
//import android.app.NotificationManager
//import android.app.PendingIntent
//import android.content.Context
//import android.content.Intent
//import event.countdown.AlarmManager.Reciver.EventAlarmReceiver
//
//fun cancelEventNotification(eventId: Int, context: Context) {
//    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//    notificationManager.cancel(eventId)
//
//    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//    val intent = Intent(context, EventAlarmReceiver::class.java)
//    val pendingIntent = PendingIntent.getBroadcast(context, eventId, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
//    alarmManager.cancel(pendingIntent)
//}
//
