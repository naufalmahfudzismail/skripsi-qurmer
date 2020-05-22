package id.dev.qurmer.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import id.dev.qurmer.data.database.ayat.AyatDao
import id.dev.qurmer.data.database.ayat.AyatTable
import id.dev.qurmer.data.database.surah.SurahDao
import id.dev.qurmer.data.database.surah.SurahTable
import id.dev.qurmer.data.database.user.UserDao
import id.dev.qurmer.data.database.user.UserTable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    version = 1, exportSchema = false,
    entities = [SurahTable::class, AyatTable::class, UserTable::class]
)
abstract class QurmerDatabase : RoomDatabase() {

    abstract fun surahDao(): SurahDao
    abstract fun ayatDao(): AyatDao
    abstract fun userDao() : UserDao

    companion object {

        @Volatile
        internal var INSTANCE: QurmerDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): QurmerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QurmerDatabase::class.java,
                    "qurmer_database.db"
                )
                    .addCallback(QurmerDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class QurmerDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)

            INSTANCE?.let {
                scope.launch(Dispatchers.IO) {
                }
            }
        }
    }
}