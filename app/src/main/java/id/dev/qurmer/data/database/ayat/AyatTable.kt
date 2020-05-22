package id.dev.qurmer.data.database.ayat

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "ayat")
class AyatTable(
    @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "surahServerId")
    var surahServerId: Int? = null, // 1
    @ColumnInfo(name = "ayatServerId")
    var ayatServerId: Int? = null, // 1
    @ColumnInfo(name = "orderNumber")
    var orderNumber: Int? = null, // 1
    @ColumnInfo(name = "surahId")
    var ayat: String? = null // 1
) : Serializable