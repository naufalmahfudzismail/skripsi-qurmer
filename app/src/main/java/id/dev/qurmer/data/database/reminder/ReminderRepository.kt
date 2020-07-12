package id.dev.qurmer.data.database.reminder

import androidx.annotation.WorkerThread
import id.dev.qurmer.data.database.hash.HashTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ReminderRepository (private val reminderDao: ReminderDao) {

    val allReminder = reminderDao.getReminder()

    @WorkerThread
    fun insert(reminderTable: ReminderTable) = GlobalScope.launch(Dispatchers.IO){
        reminderDao.insert(reminderTable)
    }

    fun update(reminderTable: ReminderTable) = GlobalScope.launch(Dispatchers.IO) {
        reminderDao.update(reminderTable)
    }
    fun delete(reminderTable: ReminderTable) = GlobalScope.launch(Dispatchers.IO) {
        reminderDao.delete(reminderTable)
    }

    fun deleteAll() = GlobalScope.launch(Dispatchers.IO) {
        reminderDao.deleteAll()
    }



}