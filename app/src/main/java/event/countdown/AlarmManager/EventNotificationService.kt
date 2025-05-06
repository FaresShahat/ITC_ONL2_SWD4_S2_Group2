package com.plcoding.alarmmanagerguide.ui.theme.AlarmManager


import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.plcoding.alarmmanagerguide.ui.theme.AlarmManager.EventDetailActivity.EventDetailActivity
import event.countdown.AlarmManager.Reciver.EventActionReceiver
import event.countdown.R

class EventNotificationService {
    @SuppressLint("NotificationPermission")
    fun showNotification(context: Context, eventId: Int) {
        val channelId = "event_notifications"
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // إنشاء قناة الإشعارات مع صوت
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val channel = NotificationChannel(channelId, "Event Reminders", NotificationManager.IMPORTANCE_HIGH).apply {
                setSound(soundUri, AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_NOTIFICATION).build())
            }
            notificationManager.createNotificationChannel(channel)
        }

        val openIntent = Intent(context, EventDetailActivity::class.java).apply {
            putExtra("EVENT_ID", eventId)
        }
        val openPendingIntent = PendingIntent.getActivity(context, eventId, openIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val snoozeIntent = Intent(context, EventActionReceiver::class.java).apply {
            action = "ACTION_SNOOZE"
            putExtra("EVENT_ID", eventId)
        }
        val snoozePendingIntent = PendingIntent.getBroadcast(context, eventId, snoozeIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val cancelIntent = Intent(context, EventActionReceiver::class.java).apply {
            action = "ACTION_CANCEL"
            putExtra("EVENT_ID", eventId)
        }
        val cancelPendingIntent = PendingIntent.getBroadcast(context, eventId, cancelIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("تنبيه الحدث")
            .setContentText("اضغط هنا لعرض التفاصيل")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
            .setAutoCancel(true)
            .setContentIntent(openPendingIntent)
            .addAction(R.drawable.ic_snooze, "تأجيل", snoozePendingIntent)
            .addAction(R.drawable.ic_cancel, "إلغاء", cancelPendingIntent)
            .build()

        notificationManager.notify(eventId, notification)
    }
}



//import android.annotation.SuppressLint
//import android.app.NotificationChannel
//import android.app.NotificationManager
//import android.app.PendingIntent
//import android.content.Context
//import android.content.Intent
//import android.media.AudioAttributes
//import android.media.RingtoneManager
//import android.os.Build
//import androidx.core.app.NotificationCompat
//import com.plcoding.alarmmanagerguide.ui.theme.AlarmManager.EventDetailActivity.EventDetailActivity
//import event.countdown.R
//
//class EventNotificationService {
//    @SuppressLint("NotificationPermission", "ObsoleteSdkInt")
//    fun showNotification(context: Context, eventId: Int) {
//        val channelId = "event_notifications"
//        val notificationManager =
//            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//        // إنشاء قناة الإشعارات مع صوت
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
//            val channel = NotificationChannel(
//                channelId,
//                "Event Reminders",
//                NotificationManager.IMPORTANCE_HIGH
//            ).apply {
//                setSound(
//                    soundUri,
//                    AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_NOTIFICATION).build()
//                )
//                enableVibration(true) // تفعيل الاهتزاز
//                enableLights(true) // تفعيل إضاءة
//            }
//            notificationManager.createNotificationChannel(channel)
//        }
//
//
//        val openIntent = Intent(context, EventDetailActivity::class.java).apply {
//            putExtra("EVENT_ID", eventId)
//        }
//        val openPendingIntent = PendingIntent.getActivity(
//            context, eventId, openIntent,
//            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//        )
//        val snoozeTimes = listOf(5, 10, 15, 30, 60) // إضافة خيارات متعددة
//        val actions = snoozeTimes.map { minutes ->
//            val snoozeIntent = Intent(context, EventActionReceiver::class.java).apply {
//                action = "ACTION_SNOOZE_$minutes"
//                putExtra("EVENT_ID", eventId)
//                val snoozeIntent = Intent(context, EventActionReceiver::class.java).apply {
//                    action = "ACTION_SNOOZE"
//                    putExtra("EVENT_ID", eventId)
//                }
//                val snoozePendingIntent = PendingIntent.getBroadcast(
//                    context, eventId, snoozeIntent,
//                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//                )
//
//                val cancelIntent = Intent(context, EventActionReceiver::class.java).apply {
//                    action = "ACTION_CANCEL"
//                    putExtra("EVENT_ID", eventId)
//                }
//                val cancelPendingIntent = PendingIntent.getBroadcast(
//                    context, eventId, cancelIntent,
//                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//                )
//
//                val notification = NotificationCompat.Builder(context, channelId)
//                    .setSmallIcon(R.drawable.ic_notification)
//                    .setContentTitle("تنبيه الحدث")
//                    .setContentText("اضغط هنا لعرض التفاصيل")
//                    .setPriority(NotificationCompat.PRIORITY_HIGH)
//                    .setAutoCancel(true)
//                    .setContentIntent(openPendingIntent)
//                    .addAction(R.drawable.add, "تأجيل", snoozePendingIntent)
//                    .addAction(R.drawable.ic_cancel, "إلغاء", cancelPendingIntent)
//                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//                    .build()
//
//                notificationManager.notify(eventId, notification)
//            }
//        }
//    }
//}

//
//import android.annotation.SuppressLint
//import android.app.NotificationChannel
//import android.app.NotificationManager
//import android.app.PendingIntent
//import android.content.Context
//import android.content.Intent
//import android.media.AudioAttributes
//import android.net.Uri
//import android.os.Build
//import androidx.core.app.NotificationCompat
//import event.countdown.MainActivity
//import event.countdown.R
//
//
//class EventNotificationService {
//
//    @SuppressLint("NotificationPermission")
//    fun showNotification(context: Context, eventId: Int) {
//        val channelId = "event_notifications"
//        createNotificationChannel(context, channelId)
//
//        val intent = Intent(context, MainActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        }
//        val pendingIntent = PendingIntent.getActivity(
//            context, eventId, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//        )
//
//        // 🔊 تحديد الصوت المخصص
//        val soundUri: Uri = Uri.parse("android.resource://${context.packageName}/raw/notification_sound")
//
//        // 📳 تخصيص نمط الاهتزاز (ميللي ثانية)
//        val vibrationPattern = longArrayOf(0, 500, 200, 500) // اهتزاز لمدة 500ms ثم توقف 200ms ثم اهتزاز آخر
//
//        val notification = NotificationCompat.Builder(context, channelId)
//            .setSmallIcon(R.drawable.ic_notification)
//            .setContentTitle("📅 تذكير بالحدث")
//            .setContentText("🕒 حان وقت الحدث المجدول!")
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setContentIntent(pendingIntent)
//            .setAutoCancel(true)
//            .setSound(soundUri) // 📌 تعيين الصوت المخصص هنا
//            .setVibrate(vibrationPattern) // 📳 تعيين الاهتزاز المخصص
//            .setLights(0xFF00FF00.toInt(), 300, 1000) // 💡 لون LED أخضر، يعمل 300ms ويتوقف 1000ms
//
//        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.notify(eventId, notification.build())
//    }
//
//    @SuppressLint("ObsoleteSdkInt")
//    private fun createNotificationChannel(context: Context, channelId: String) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val soundUri: Uri = Uri.parse("android.resource://${context.packageName}/raw/notification_sound")
//            val attributes = AudioAttributes.Builder()
//                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
//                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
//                .build()
//
//            val vibrationPattern = longArrayOf(0, 500, 200, 500)
//
//            val channel = NotificationChannel(
//                channelId,
//                "إشعارات الأحداث",
//                NotificationManager.IMPORTANCE_HIGH
//            ).apply {
//                setSound(soundUri, attributes) // 🔊 إضافة الصوت المخصص
//                enableVibration(true)
//                vibrationPattern?.let { setVibrationPattern(it) } // 📳 تخصيص الاهتزاز
//                enableLights(true)
//                lightColor = 0xFF00FF00.toInt() // 💡 تحديد لون LED أخضر
//            }
//
//            val notificationManager = context.getSystemService(NotificationManager::class.java)
//            notificationManager.createNotificationChannel(channel)
//        }
//    }
//}

