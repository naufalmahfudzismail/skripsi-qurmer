package id.dev.qurmer.challenge.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.dev.qurmer.R
import id.dev.qurmer.data.database.ayat.AyatTable
import kotlinx.android.synthetic.main.card_ayat_answer_challenge.view.*

class AyatAnswerChallengeAdapter(val context: Context) :
    RecyclerView.Adapter<AyatAnswerChallengeAdapter.ViewHolder>() {

    var ayats : MutableList<AyatTable> = mutableListOf()

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun binding(ayat: AyatTable, position: Int) {
            view.txt_ayat.text = ayat.ayat
            view.txt_number_ayat.text = (position + 1).toString()
        }
    }


    fun addAyat(ayat : AyatTable){
        ayats.add(ayat)
        notifyItemInserted(ayats.lastIndex)
    }

    fun removeAll(){
        ayats.clear()
        notifyDataSetChanged()
    }

    fun removeLast(){
        ayats.removeAt(ayats.lastIndex)
        notifyItemRemoved(ayats.lastIndex)
    }


    fun getSize() : Int = ayats.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.card_ayat_answer_challenge, parent, false)
        )
    }

    override fun getItemCount(): Int = ayats.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(ayats[position], position)
    }


}