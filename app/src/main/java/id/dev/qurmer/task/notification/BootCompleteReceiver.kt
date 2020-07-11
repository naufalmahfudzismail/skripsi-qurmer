package id.dev.qurmer.task.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import id.dev.qurmer.config.SessionManager

class BootCompleteReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action == "android.intaent.action.BOOT_COMPLETED") {
            // ideally we should be fetching the data from a database
            val timeInMilli = SessionManager.getInstance(context).getTimeAlarm()
            val desc = SessionManager.getInstance(context).getDescriptionAlarm()
            AlarmHelper.setAlarm(context, timeInMilli, desc!!)
        }
    }
}