package id.dev.qurmer.rank

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.dev.qurmer.R
import id.dev.qurmer.data.model.RankResponse.Data.Rank
import kotlinx.android.synthetic.main.card_rank.view.*

class LeaderBoardAdapter(val context: Context, val data: List<Rank>) :
    RecyclerView.Adapter<LeaderBoardAdapter.ViewHolder>() {

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun binding(rank: Rank, position: Int) {

            view.txt_rank.text = (position + 4).toString()
            view.txt_name.text = rank.user?.nama
            view.txt_point.text = rank.totalScore

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