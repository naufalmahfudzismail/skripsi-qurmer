package id.dev.qurmer.task

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import id.dev.qurmer.R
import id.dev.qurmer.config.BaseActivity
import id.dev.qurmer.data.database.reminder.ReminderViewModel
import kotlinx.android.synthetic.main.activity_reminder.*

class ReminderActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)

        val reminderViewModel = ViewModelProviders.of(this).get(ReminderViewModel::class.java)

        reminderViewModel.allReminder.observe(this, Observer {
            val size = it.size

            if (size == 0) {
                ll_empty_activity.visibility = View.VISIBLE
                rl_not_empty.visibility = View.GONE
            } else {
                ll_empty_activity.visibility = View.GONE
                rl_not_empty.visibility = View.VISIBLE

                val adapter = ReminderAdapter(this, it) { reminder ->
                    val isActive = reminder.isActive
                    reminder.isActive = !isActive
                    reminderViewModel.update(reminder)
                }

                rv_reminder.layoutManager = LinearLayoutManager(this)
                rv_reminder.itemAnimator = DefaultItemAnimator()
                rv_reminder.adapter = adapter

                add_reminder.setOnClickListener {
                    startActivityWithIntent<SettingReminderActivity>()
                }

            }

        })

        ll_back_reminder.setOnClickListener {
            onBackPressed()
        }

        btn_make_reminder.setOnClickListener {
            startActivityWithIntent<SettingReminderActivity>()
        }
    }
}