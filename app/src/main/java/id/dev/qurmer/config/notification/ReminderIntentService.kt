package id.dev.qurmer.config.notification

import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import id.dev.qurmer.MainActivity
import id.dev.qurmer.R
import id.dev.qurmer.config.BaseActivity

@SuppressLint("NewApi")
class ReminderIntentService : IntentService("Qurmar Reminder"){


    private var NOTIFICATION_DAILY_ID = 2
    private val dailyNotificationChannelId = "daily"

    private fun createDailyChannel(): NotificationChannel {
        val importance = NotificationManager.IMPORTANCE_HIGH
        val notificationChannel = NotificationChannel(
            BaseActivity.NOTIFICATION_CHANNEL_ID,
            "Qurmar Reminder",
            importance
        )
        notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        notificationChannel.enableVibration(true)
        notificationChannel.setShowBadge(true)
        notificationChannel.enableLights(true)
        return notificationChannel
    }

    override fun onHandleIntent(intent: Intent?) {

        val notificationDailyManager =
            NotificationManagerCompat.from(this)
        val channelDaily = createDailyChannel()
        val dailyNotification = getDailyNotification("Klik untuk memperbarui aktifitas harian mu")

        notificationDailyManager.createNotificationChannel(channelDaily)
        notificationDailyManager.notify(NOTIFICATION_DAILY_ID, dailyNotification!!)

    }


    private fun getDailyNotification(content: String): Notification? {
        val builder = NotificationCompat.Builder(this, dailyNotificationChannelId)
        builder.setContentTitle("Pemeritahuan dari Qurmar")
        builder.setContentText(content)
        builder.setSmallIcon(R.drawable.logo_qurmar)
        builder.setAutoCancel(true)
        builder.setContentIntent(
            PendingIntent.getActivity(
                this,
                0,
                Intent(
                    this,
                    MainActivity::class.java
                ),
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        )
        builder.setChannelId(BaseActivity.NOTIFICATION_CHANNEL_ID)
        return builder.build()
    }
}