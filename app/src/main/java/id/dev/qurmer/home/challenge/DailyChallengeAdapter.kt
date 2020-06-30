package id.dev.qurmer.home.challenge

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.dev.qurmer.R
import id.dev.qurmer.data.model.ChallengeResponse.Data.Challenge
import kotlinx.android.synthetic.main.card_daily_challenge.view.*

class DailyChallengeAdapter(
    val context: Context,
    var datas: List<Challenge>,
    val listener: (Challenge) -> Unit
) :
    RecyclerView.Adapter<DailyChallengeAdapter.ViewHolder>() {
    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        fun binding(data: Challenge) {
            val score =
                data.score!!.toInt() + data.level?.bonusScore!!.toInt()

            view.txt_title_challenge.text = data.level?.name.toString()
            view.txt_surah.text = "Surat ${data.surah?.nama.toString()}"
            view.txt_point.text = "$score XP"

            view.ll_card_daily.setOnClickListener {
                listener(data)
            }

            view.setOnClickListener {
                listener(data)
            }
        }
    }


    fun updateData(challenge: List<Challenge>) {
        datas = challenge
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.card_daily_challenge, parent, false)
        )
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(datas[position])
    }
}