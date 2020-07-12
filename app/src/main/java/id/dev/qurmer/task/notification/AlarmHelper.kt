package id.dev.qurmer.task.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build

object AlarmHelper {

    fun setAlarm(context: Context, time: Long, repeat : Long,  desc: String) {

        val broadcastIntent = Intent(
            context
            , AlarmReceiver::class.java
        ).putExtra("desc", desc)

        val intent = PendingIntent.getBroadcast(
            context,
            0,
            broadcastIntent,
            0
        )

        // Setting up AlarmManager
        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (System.currentTimeMillis() < time) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmMgr.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    time,
                    repeat,
                    intent
                )
            } else {
                alarmMgr.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    time,
                    repeat,
                    intent
                )
            }
        }
    }
}