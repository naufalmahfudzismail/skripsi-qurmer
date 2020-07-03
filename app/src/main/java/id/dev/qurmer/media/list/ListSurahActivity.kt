package id.dev.qurmer.media.list

import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import id.dev.qurmer.R
import id.dev.qurmer.config.BaseActivity
import id.dev.qurmer.data.database.surah.SurahTable
import id.dev.qurmer.data.database.surah.SurahViewModel
import id.dev.qurmer.media.SurahPlayerActivity
import id.dev.qurmer.memorize.MemorizeActivity
import kotlinx.android.synthetic.main.activity_list_surah.*
import java.util.*

class ListSurahActivity : BaseActivity() {


    private var surahs: MutableList<SurahTable> = mutableListOf()
    private lateinit var adapter: ListSurahAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_surah)

        val type = intent.getIntExtra("type", 0)

        val surahViewModel = ViewModelProviders.of(this).get(SurahViewModel::class.java)
        surahViewModel.allSurah.observe(this, Observer {


            surahs.addAll(it)
            adapter = ListSurahAdapter(this, it) { surah ->
                if (type == 1) {
                    startActivityWithIntent<SurahPlayerActivity>("surah" to surah)
                } else {

                    makeToast("Akan Segara Datang")
                    /*startActivityWithIntent<MemorizeActivity>("surah" to surah)*/
                }
            }
            rv_list_surat.layoutManager = LinearLayoutManager(this)
            rv_list_surat.itemAnimator = DefaultItemAnimator()
            rv_list_surat.adapter = adapter

        })

        initSearch()


    }


    private fun initSearch() {
        search_surah.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {
                val data = surahs.filter {
                    newText?.toLowerCase(Locale.ROOT)
                        ?.let { it1 -> it.surahName?.toLowerCase(Locale.ROOT)?.contains(it1) }!!
                }
                adapter.updateData(data as MutableList<SurahTable>)
                return false
            }

        })
    }
}
