package id.dev.qurmer.data.database.surah

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SurahRepository(private val surahDao: SurahDao) {

    val all: LiveData<List<SurahTable>> = surahDao.getAll()

    fun selectPerBadge(badgeId : Int) : LiveData<List<SurahTable>> = surahDao.selectPerBadge(badgeId)


    @WorkerThread
    fun insert(surah: SurahTable) = GlobalScope.launch(Dispatchers.IO){
        surahDao.insert(surah)
    }

    fun update(surah: SurahTable) = GlobalScope.launch(Dispatchers.IO) {
        surahDao.update(surah)
    }
    fun delete(surah: SurahTable) = GlobalScope.launch(Dispatchers.IO) {
        surahDao.delete(surah)
    }

    fun deleteAll() = GlobalScope.launch(Dispatchers.IO) {
        surahDao.deleteAll()
    }

}