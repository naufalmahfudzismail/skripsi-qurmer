package id.dev.qurmer.rank

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import id.dev.qurmer.R
import id.dev.qurmer.data.model.RankResponse.Data.Rank
import kotlinx.android.synthetic.main.card_rank.view.*

class LeaderBoardAdapter(val context: Context, val data: List<Rank>) :
    RecyclerView.Adapter<LeaderBoardAdapter.ViewHolder>() {

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        fun binding(rank: Rank, position: Int) {

            view.txt_rank.text = (position + 4).toString()
            view.txt_name.text = rank.user?.nama
            view.txt_point.text = rank.totalScore

            if (rank.user?.gender == "Perempuan") {

                view.img_profile.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_avatar_cewe
                    )
                )

            } else {
                view.img_profile.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.mask_group
                    )
                )
            }

            val progress = rank.progress
            if (progress!!.isEmpty()) view.txt_level.text = "Level 1"
            else {
                val lvl1 = progress.filter { it.level == "1" }
                val lvl2 = progress.filter { it.level == "2" }
                when {
                    lvl1.size == 6 -> view.txt_level.text = "Level 2"
                    lvl2.size == 9 -> view.txt_level.text = "Level 3"
                    else -> {
                        progress.forEach {
                            view.txt_level.text = "Level ${it.level}"
                        }
                    }
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_rank, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding(data[position], position)
    }

}