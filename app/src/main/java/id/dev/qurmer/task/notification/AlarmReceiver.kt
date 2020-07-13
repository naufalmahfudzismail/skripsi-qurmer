package id.dev.qurmer.task.notification

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import id.dev.qurmer.MainActivity
import id.dev.qurmer.R


class AlarmReceiver : BroadcastReceiver() {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("ALARM", "onReceive")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = "Reminder"
            val descriptionText = "Description Detail"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel("Qurmar Reminder", name, importance)
            mChannel.description = descriptionText
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }

        // Create the notification to be shown
        val builder = NotificationCompat.Builder(context, "Qurmar Reminder")
        builder.setContentTitle("Pengigat dari Qurmar")
        builder.setContentText(intent.getStringExtra("desc"))
        builder.setSmallIcon(R.drawable.logo_qurmar)
        builder.setAutoCancel(false)
        builder.setContentIntent(
            PendingIntent.getActivity(
                context,
                0,
                Intent(
                    context,
                    MainActivity::class.java
                ),
                0
            )
        )
        builder.priority = NotificationCompat.PRIORITY_HIGH

        // Get the Notification manager service
        val am = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Generate an Id for each notification
        val id = System.currentTimeMillis() / 1000

        var alarmUri: Uri? = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        }
        val ringtone = MediaPlayer.create(context, alarmUri)
        ringtone.start()

        // Show a notification
        am.notify(id.toInt(), builder.build())
    }
}