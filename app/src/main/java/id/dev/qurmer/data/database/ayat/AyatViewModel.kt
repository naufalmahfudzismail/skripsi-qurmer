package id.dev.qurmer.data.database.ayat

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import id.dev.qurmer.data.database.QurmerDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class AyatViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)
    private var ayatRepository: AyatRepository

    init {
        val ayatDao = QurmerDatabase.getDatabase(application, scope).ayatDao()
        ayatRepository = AyatRepository(ayatDao)
    }

    fun getAyat(surahId: Int): LiveData<List<AyatTable>> = ayatRepository.getAyat(surahId)
    fun insertAyat(ayatTable: AyatTable) = ayatRepository.insert(ayatTable)
    fun updateAyat(ayatTable: AyatTable) = ayatRepository.update(ayatTable)
    fun deleteAyat(ayatTable: AyatTable) = ayatRepository.delete(ayatTable)
    fun deleteAll() = ayatRepository.deleteAll()


    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

}