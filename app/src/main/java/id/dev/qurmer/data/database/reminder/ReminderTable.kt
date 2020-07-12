package id.dev.qurmer.data.database.reminder

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminder")
class ReminderTable(
    @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "time")
    var time: Long? = null, // 1
    @ColumnInfo(name = "repeat")
    var repeat: Long? = null,
    @ColumnInfo(name = "name")
    var name: String? = null,
    @ColumnInfo(name = "isActive")
    var isActive : Boolean = true
)