package id.dev.qurmer.task

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.dev.qurmer.R
import id.dev.qurmer.data.database.reminder.ReminderTable
import kotlinx.android.synthetic.main.card_reminder.view.*
import java.text.SimpleDateFormat
import java.util.*

class ReminderAdapter(
    val context: Context,
    val reminders: List<ReminderTable>,
    val listener: (ReminderTable) -> Unit
) :
    RecyclerView.Adapter<ReminderAdapter.ViewHolder>() {

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {


        fun binding(reminderTable: ReminderTable) {

            view.txt_desc.text = reminderTable.name
            view.txt_time.text = convertLongToTime(reminderTable.time!!)

            view.switch_on.isChecked = reminderTable.isActive
            view.switch_on.setOnCheckedChangeListener { buttonView, isChecked ->
                listener(reminderTable)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.card_reminder,
                parent,
                false
            )
        )
    }

    @SuppressLint("SimpleDateFormat")
    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("HH:mm")
        return format.format(date)
    }

    override fun getItemCount(): Int = reminders.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(reminders[position])
    }
}