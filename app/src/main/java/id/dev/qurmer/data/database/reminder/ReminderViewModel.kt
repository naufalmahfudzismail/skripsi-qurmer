package id.dev.qurmer.data.database.reminder

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import id.dev.qurmer.data.database.QurmerDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class ReminderViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: ReminderRepository

    init {
        val dao = QurmerDatabase.getDatabase(application, scope).reminderDao()
        repository = ReminderRepository(dao)
    }

    val allReminder = repository.allReminder

    fun insert(reminderTable: ReminderTable) = repository.insert(reminderTable)
    fun update(reminderTable: ReminderTable) = repository.update(reminderTable)
    fun delete(reminderTable: ReminderTable) = repository.delete(reminderTable)
    fun deleteAll() = repository.deleteAll()



}