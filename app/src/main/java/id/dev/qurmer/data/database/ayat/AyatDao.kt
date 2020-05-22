package id.dev.qurmer.data.database.ayat

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface AyatDao {

    @Query("SELECT * from ayat where surahServerId = :surahServerId ORDER BY orderNumber ASC")
    fun getAll(surahServerId: Int): LiveData<List<AyatTable>>

    @Insert
    fun insert(ayat: AyatTable)
    
    @Query("DELETE FROM surah")
    fun deleteAll()

    @Update
    fun update(ayat: AyatTable)

    @Delete
    fun delete(ayat: AyatTable)
}