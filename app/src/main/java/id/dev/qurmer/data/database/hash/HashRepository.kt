package id.dev.qurmer.data.database.hash

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HashRepository(private val hashDao: HashDao) {

    fun getHash(surahId: Int): LiveData<List<HashTable>> = hashDao.getHash(surahId)
    fun getHashValue(hash: IntArray, surahId: Int): LiveData<List<HashTable>> = hashDao.getHashByHash(hash, surahId)
    fun getHashValue(hash: IntArray): LiveData<List<HashTable>> = hashDao.getAllHash(hash)

    @WorkerThread
    fun insert(hash: HashTable) = GlobalScope.launch(Dispatchers.IO){
        hashDao.insert(hash)
    }

    fun update(hash: HashTable) = GlobalScope.launch(Dispatchers.IO) {
        hashDao.update(hash)
    }
    fun delete(hash: HashTable) = GlobalScope.launch(Dispatchers.IO) {
        hashDao.delete(hash)
    }

    fun deleteAll() = GlobalScope.launch(Dispatchers.IO) {
        hashDao.deleteAll()
    }

}