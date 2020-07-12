package id.dev.qurmer.data.database.reminder
import androidx.lifecycle.LiveData
import androidx.room.*
import id.dev.qurmer.data.database.hash.HashTable


@Dao
interface ReminderDao {

    @Query("SELECT * FROM reminder")
    fun getReminder() : LiveData<List<ReminderTable>>

    @Insert
    fun insert(reminderTable: ReminderTable)

    @Query("DELETE FROM reminder")
    fun deleteAll()

    @Update
    fun update(reminder: ReminderTable)

    @Delete
    fun delete(reminder: ReminderTable)

}