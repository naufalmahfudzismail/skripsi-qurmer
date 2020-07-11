package id.dev.qurmer.task

import android.os.Bundle
import id.dev.qurmer.R
import id.dev.qurmer.config.BaseActivity
import kotlinx.android.synthetic.main.activity_reminder.*

class ReminderActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)

        btn_make_reminder.setOnClickListener {
            startActivityWithIntent<SettingReminderActivity>()
        }
    }
}