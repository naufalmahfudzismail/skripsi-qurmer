package id.dev.qurmer.data.database.user

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Query("SELECT * from user LIMIT 1")
    fun getUser(): LiveData<UserTable>

    @Insert
    fun insert(user: UserTable)

    @Query("DELETE FROM user")
    fun deleteAll()

    @Update
    fun update(user: UserTable)

    @Delete
    fun delete(user: UserTable)
}