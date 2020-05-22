package id.dev.qurmer.data.database.user

import androidx.annotation.WorkerThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserRepository(private val userDao: UserDao) {

    val user = userDao.getUser()

    @WorkerThread
    fun insert(user: UserTable) = GlobalScope.launch(Dispatchers.IO) {
        userDao.insert(user)
    }

    fun update(user: UserTable) = GlobalScope.launch(Dispatchers.IO) {
        userDao.update(user)
    }

    fun delete(user: UserTable) = GlobalScope.launch(Dispatchers.IO) {
        userDao.delete(user)
    }

    fun deleteAll() = GlobalScope.launch(Dispatchers.IO) {
        userDao.deleteAll()
    }
}