package id.dev.qurmer.config.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationDailyAlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val service = Intent(context, ReminderIntentService::class.java)
        context.startService(service)
    }

}