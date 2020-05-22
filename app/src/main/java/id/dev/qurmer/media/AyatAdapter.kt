package id.dev.qurmer.media

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.dev.qurmer.R
import id.dev.qurmer.data.database.ayat.AyatTable
import kotlinx.android.synthetic.main.card_ayat_audio_player.view.*

class AyatAdapter(val context: Context, val ayats: List<AyatTable>) :
    RecyclerView.Adapter<AyatAdapter.ViewHolder>() {

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun binding(ayat: AyatTable) {
            view.txt_ayat.text = ayat.ayat
            view.txt_number_ayat.text = ayat.orderNumber.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.card_ayat_audio_player, parent, false)
        )
    }

    override fun getItemCount(): Int = ayats.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(ayats[position])
    }


}