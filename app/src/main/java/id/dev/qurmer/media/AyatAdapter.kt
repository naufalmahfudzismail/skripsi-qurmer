package id.dev.qurmer.media

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.dev.qurmer.R
import id.dev.qurmer.data.database.ayat.AyatTable
import kotlinx.android.synthetic.main.card_ayat_audio_player.view.*
import kotlinx.android.synthetic.main.card_ayat_audio_player_second.view.*

class AyatAdapter(val context: Context, val ayats: List<AyatTable>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun binding(ayat: AyatTable) {

            if (ayat.ayat!!.contains("بِسْمِ ٱللَّهِ ٱلرَّحْمَٰنِ ٱلرَّحِيمِ")) {
                ayat.ayat!!.replace(
                    "بِسْمِ اللَّهِ الرَّحْمَنِ الرَّحِيم",
                    ""
                )
            }

            view.txt_ayat.text = ayat.ayat
            view.txt_number_ayat.text = ayat.orderNumber.toString()
        }
    }

    inner class OddViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun binding(ayat: AyatTable) {
            view.txt_ayat_second.text = ayat.ayat
            view.txt_number_ayat_second.text = ayat.orderNumber.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> {
                OddViewHolder(
                    LayoutInflater.from(context)
                        .inflate(R.layout.card_ayat_audio_player_second, parent, false)
                )
            }
            else -> {
                ViewHolder(
                    LayoutInflater.from(context)
                        .inflate(R.layout.card_ayat_audio_player, parent, false)
                )
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 2 != 0) {
            1
        } else {
            2
        }
    }

    override fun getItemCount(): Int = ayats.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (position % 2 != 0) {
            (holder as AyatAdapter.OddViewHolder).binding(ayats[position])
        } else {
            (holder as AyatAdapter.ViewHolder).binding(ayats[position])
        }
    }


}