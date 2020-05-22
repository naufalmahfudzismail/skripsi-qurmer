package id.dev.qurmer.data.database.user

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import id.dev.qurmer.data.database.QurmerDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    var user: LiveData<UserTable>
    var userRepository: UserRepository

    init {
        val userDao = QurmerDatabase.getDatabase(application, scope).userDao()
        userRepository = UserRepository(userDao)
        user = userRepository.user
    }

    fun insert(user: UserTable) = userRepository.insert(user)
    fun update(user: UserTable) = userRepository.update(user)
    fun delete(user: UserTable) = userRepository.delete(user)
    fun deleteAll() = userRepository.deleteAll()

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}