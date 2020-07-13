package id.dev.qurmer.task.notification

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import id.dev.qurmer.data.database.reminder.ReminderViewModel


class BootCompleteReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action == "android.intaent.action.BOOT_COMPLETED") {
            // ideally we should be fetching the data from a database
            /*val timeInMilli = SessionManager.getInstance(context).getTimeAlarm()
            val desc = SessionManager.getInstance(context).getDescriptionAlarm()*/

            val viewModel = ViewModelProviders.of(context as FragmentActivity).get(ReminderViewModel::class.java)
            viewModel.allReminder.observe(context, Observer {
                it.forEach {reminder ->
                    AlarmHelper.setAlarm(context, reminder.time!!, reminder.repeat!!,  reminder.name!!, reminder.id!!.toInt() )
                }
            })

        }
    }
}