package id.dev.qurmer.data.database.surah

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import id.dev.qurmer.data.database.QurmerDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class SurahViewModel(application: Application) : AndroidViewModel(application) {
    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: SurahRepository
    val allSurah: LiveData<List<SurahTable>>


    init {
        val surahDao = QurmerDatabase.getDatabase(application, scope).surahDao()
        repository = SurahRepository(surahDao)
        allSurah = repository.all
    }

    fun surahPerBadge(badgeId: Int): LiveData<List<SurahTable>> = repository.selectPerBadge(badgeId)
    fun getSurahById(surahId : Int) : LiveData<SurahTable> = repository.getSurahById(surahId)

    fun insert(surahTable: SurahTable) {
        Log.e("INSERT", surahTable.surahName.toString())
        repository.insert(surahTable)
    }

    fun update(surahTable: SurahTable) = repository.update(surahTable)
    fun delete(surahTable: SurahTable) = repository.delete(surahTable)
    fun deleteAll() = repository.deleteAll()


    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

}