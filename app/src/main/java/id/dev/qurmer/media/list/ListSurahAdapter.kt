package id.dev.qurmer.media.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.dev.qurmer.R
import id.dev.qurmer.data.database.surah.SurahTable
import kotlinx.android.synthetic.main.card_surah_player.view.*
import kotlinx.android.synthetic.main.card_surah_player_reverse.view.*

class ListSurahAdapter(
    val context: Context,
    private var listSurah: List<SurahTable>,
    val listener: (SurahTable) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun binding(surah: SurahTable, position: Int) {
            view.txt_surat.text = surah.surahName.toString()
            view.btn_choose_audio.setOnClickListener {
                listener(surah)
            }
        }
    }

    inner class OddViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun binding(surah: SurahTable, position: Int) {
            view.txt_surat_reserve.text = surah.surahName.toString()

            view.btn_choose_audio_reverse.setOnClickListener {
                listener(surah)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> {
                OddViewHolder(
                    LayoutInflater.from(context)
                        .inflate(R.layout.card_surah_player_reverse, parent, false)
                )
            }
            else -> {
                ViewHolder(
                    LayoutInflater.from(context).inflate(R.layout.card_surah_player, parent, false)
                )
            }
        }

    }

    fun updateData(list: MutableList<SurahTable>) {
        listSurah = list
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 2 != 0) {
            1
        } else {
            2
        }
    }

    override fun getItemCount(): Int = listSurah.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position % 2 != 0) {
            (holder as OddViewHolder).binding(listSurah[position], position)
        } else {
            (holder as ViewHolder).binding(listSurah[position], position)
        }
    }
}