package id.dev.qurmer.data.database.surah

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SurahDao {
    @Query("SELECT * from surah ORDER BY id ASC")
    fun getAll(): LiveData<List<SurahTable>>

    @Insert
    fun insert(surahTable: SurahTable)

    @Query("SELECT * FROM surah WHERE surahBadgeId = :badgeId ")
    fun selectPerBadge(badgeId: Int) : LiveData<List<SurahTable>>

    @Query("DELETE FROM surah")
    fun deleteAll()

    @Update
    fun update(surahTable: SurahTable)

    @Delete
    fun delete(surahTable: SurahTable)
}