package id.dev.qurmer.data.database.ayat

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AyatRepository(private val ayatDao: AyatDao) {

    fun getAyat(ayatServerId: Int): LiveData<List<AyatTable>> = ayatDao.getAll(ayatServerId)

    @WorkerThread
    fun insert(ayat: AyatTable) = GlobalScope.launch(Dispatchers.IO){
        ayatDao.insert(ayat)
    }

    fun update(ayat: AyatTable) = GlobalScope.launch(Dispatchers.IO) {
        ayatDao.update(ayat)
    }
    fun delete(ayat: AyatTable) = GlobalScope.launch(Dispatchers.IO) {
        ayatDao.delete(ayat)
    }

    fun deleteAll() = GlobalScope.launch(Dispatchers.IO) {
        ayatDao.deleteAll()
    }
}