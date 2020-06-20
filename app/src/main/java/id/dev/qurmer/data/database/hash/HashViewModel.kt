package id.dev.qurmer.data.database.hash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import id.dev.qurmer.data.database.QurmerDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class HashViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: HashRepository

    init {
        val hashDao = QurmerDatabase.getDatabase(application, scope).hashDao()
        repository = HashRepository(hashDao)
    }

    fun getHash(surahId: Int): LiveData<List<HashTable>> = repository.getHash(surahId)
    fun getHashByValue(hashs: IntArray, surahId: Int): LiveData<List<HashTable>> = repository.getHashValue(hashs, surahId)
    fun getSearchHash(hashs: IntArray): LiveData<List<HashTable>> = repository.getHashValue(hashs)
    fun insertHash(ayatTable: HashTable) = repository.insert(ayatTable)
    fun updateHash(ayatTable: HashTable) = repository.update(ayatTable)
    fun deleteHash(ayatTable: HashTable) = repository.delete(ayatTable)
    fun deleteAll() = repository.deleteAll()


    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}