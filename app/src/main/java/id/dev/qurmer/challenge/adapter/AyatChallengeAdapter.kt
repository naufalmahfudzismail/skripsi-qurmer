package id.dev.qurmer.challenge.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.dev.qurmer.R
import id.dev.qurmer.data.database.ayat.AyatTable
import kotlinx.android.synthetic.main.card_ayat_challenge.view.*

class AyatChallengeAdapter(
    val context: Context,
    var ayats: MutableList<AyatTable>,
    val listener: (AyatTable, Int) -> Unit
) :
    RecyclerView.Adapter<AyatChallengeAdapter.ViewHolder>() {


    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun binding(ayat: AyatTable, position: Int) {
            view.txt_ayat.text = ayat.ayat
            view.setOnClickListener {
                listener(ayat, position)
                remove(ayat)
            }
        }
    }


    fun remove(ayat: AyatTable) {
        ayats.remove(ayat)
        notifyDataSetChanged()
    }

    fun restore(datas: List<AyatTable>) {
        Log.e("Size", datas.size.toString())
        ayats = datas as MutableList<AyatTable>
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.card_ayat_challenge, parent, false)
        )
    }

    override fun getItemCount(): Int = ayats.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(ayats[position], position)
    }


}