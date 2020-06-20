package id.dev.qurmer.data.database.hash

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "hashs")
class HashTable(
    @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "surahId")
    var surahId: Int? = null, // 1
    @ColumnInfo(name = "hash")
    var hash: Int? = null,
    @ColumnInfo(name = "time")
    var time: Int? = null
) {
}