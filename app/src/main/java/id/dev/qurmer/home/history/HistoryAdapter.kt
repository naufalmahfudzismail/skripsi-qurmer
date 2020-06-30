package id.dev.qurmer.home.history

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import id.dev.qurmer.R
import id.dev.qurmer.data.GlobalData.newFormatWithTime
import id.dev.qurmer.data.GlobalData.oldFormatWithTime
import id.dev.qurmer.data.model.HistoryResponse
import kotlinx.android.synthetic.main.card_history.view.*

class HistoryAdapter(val context: Context, val data: List<HistoryResponse.Data>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun binding(data: HistoryResponse.Data) {

            val surah = data.progress?.challenge?.surah
            val level = data.progress?.challenge?.level
            val score =
                data.progress?.challenge?.score!!.toInt() + data.progress?.challenge?.level?.bonusScore!!.toInt()
            val done = data.progress?.isDone

            if (done == "0") {
                view.img_star.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_star_undone
                    )
                )

                view.txt_point.text = "+ 0 XP"
            } else {
                view.txt_point.text = "+ ${score} XP"
            }

            view.txt_surah.text = "Surat ${surah?.nama.toString()}"
            view.txt_title_history.text = "Mengikuti Challenge ${level?.name}"
            view.txt_date.text = newFormatWithTime.format(oldFormatWithTime.parse(data.createdAt!!)!!)


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.card_history, parent, false)
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(data[position])
    }
}