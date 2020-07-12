package id.dev.qurmer.task

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.TimePickerDialog
import android.content.ComponentName
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import id.dev.qurmer.R
import id.dev.qurmer.config.BaseActivity
import id.dev.qurmer.config.SessionManager
import id.dev.qurmer.data.database.reminder.ReminderTable
import id.dev.qurmer.data.database.reminder.ReminderViewModel
import id.dev.qurmer.task.notification.AlarmHelper
import id.dev.qurmer.task.notification.BootCompleteReceiver
import kotlinx.android.synthetic.main.activity_setting_reminder.*
import kotlinx.android.synthetic.main.dialog_day.view.*
import java.text.SimpleDateFormat
import java.util.*

class SettingReminderActivity : BaseActivity() {


    private var chooseHour: Int? = null
    private var chooseMinute: Int? = null
    private var chooseDay: Int? = 0
    private lateinit var alarmTimePicker: TimePickerDialog


    @SuppressLint("SetTextI18n", "ServiceCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_reminder)

        var timeInMilliSeconds: Long = 0
        val receiver = ComponentName(applicationContext, BootCompleteReceiver::class.java)


        val viewModel = ViewModelProviders.of(this).get(ReminderViewModel::class.java)

        applicationContext.packageManager?.setComponentEnabledSetting(
            receiver,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )


        ll_back_reminder.setOnClickListener {
            onBackPressed()
        }

        rl_time_reminder.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)
            alarmTimePicker = TimePickerDialog(
                this,
                TimePickerDialog.OnTimeSetListener { _, hourOfDay, minuteOfHour ->
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar.set(Calendar.MINUTE, minuteOfHour)
                    calendar.set(Calendar.SECOND, 0)

                    txt_clock.text = "$hourOfDay : $minuteOfHour"

                    val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
                    val formattedDate = sdf.format(calendar.time)
                    val date = sdf.parse(formattedDate)
                    timeInMilliSeconds = date!!.time
                },
                hour,
                minute,
                true
            )
            alarmTimePicker.setTitle("Pilih jam Pengingat mu")
            alarmTimePicker.show()
        }

        rl_day_reminder.setOnClickListener {
            showDayDialog()
        }

        btn_save.setOnClickListener {
            Log.e("HOUR", chooseHour.toString())
            Log.e("MINUTE", chooseMinute.toString())
            if (chooseDay != 0 && timeInMilliSeconds.toInt() != 0 && edt_desc.text.toString() != null) {

                val reminder = ReminderTable(
                    time = timeInMilliSeconds,
                    repeat = 24 * 60 * 1000 * 60 * 7,
                    name = edt_desc.text.toString()
                )
                
                viewModel.insert(reminder)
                SessionManager.getInstance(this).setDescriptionAlarm(edt_desc.text.toString())
                SessionManager.getInstance(this).setTimeAlarm(timeInMilliSeconds)
                AlarmHelper.setAlarm(
                    this,
                    timeInMilliSeconds,
                    AlarmManager.INTERVAL_DAY * 7,
                    edt_desc.text.toString()
                )
                makeToast("Pengingat telah di buat")
                onBackPressed()

            } else {
                makeToast("Silahkan Pilih Hari, waktu dan Tujuan pengingat nya terlebih dahulu")
            }
        }

        btn_cancel.setOnClickListener {
            chooseHour = null
            chooseDay = 0
            chooseMinute = null
            txt_date.text = "Pilih Hari"
            txt_clock.text = "Pilih Waktu"
        }

    }


    private fun showDayDialog() =
        try {
            val factory = LayoutInflater.from(this)
            val dialogView =
                factory.inflate(R.layout.dialog_day, null)
            val dialog = AlertDialog.Builder(this).create()

            dialogView.btn_senin.setOnClickListener {
                dialog.dismiss()
                txt_date.text = "Senin"
                chooseDay = 2
            }

            dialogView.btn_selasa.setOnClickListener {
                dialog.dismiss()
                txt_date.text = "Selasa"
                chooseDay = 3
            }

            dialogView.btn_rabu.setOnClickListener {
                dialog.dismiss()
                txt_date.text = "Rabu"
                chooseDay = 4
            }

            dialogView.btn_kamis.setOnClickListener {
                dialog.dismiss()
                txt_date.text = "Kamis"
                chooseDay = 5
            }

            dialogView.btn_jumat.setOnClickListener {
                dialog.dismiss()
                txt_date.text = "Jumat"
                chooseDay = 6
            }

            dialogView.btn_sabtu.setOnClickListener {
                dialog.dismiss()
                txt_date.text = "Sabtu"
                chooseDay = 7
            }
            dialogView.btn_ahad.setOnClickListener {
                dialog.dismiss()
                txt_date.text = "Ahad"
                chooseDay = 1
            }




            dialog.setCancelable(false)
            dialog.setView(dialogView)
            dialog.show()

        } catch (e: Throwable) {
            Log.e("ERROR DIALOG", e.message.toString())
        }
}