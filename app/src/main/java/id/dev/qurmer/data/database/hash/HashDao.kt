package id.dev.qurmer.data.database.hash

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface HashDao {
    @Query("SELECT * from hashs where surahId =  :surah ")
    fun getHash(surah: Int): LiveData<List<HashTable>>

    @Query("SELECT * from hashs where hash IN(:values) and surahId = :surahId")
    fun getHashByHash(values: IntArray, surahId : Int): LiveData<List<HashTable>>

    @Query("SELECT * from hashs where hash IN(:values)")
    fun getAllHash(values: IntArray): LiveData<List<HashTable>>

    @Insert
    fun insert(hash: HashTable)

    @Query("DELETE FROM hashs")
    fun deleteAll()

    @Update
    fun update(ayat: HashTable)

    @Delete
    fun delete(ayat: HashTable)

}