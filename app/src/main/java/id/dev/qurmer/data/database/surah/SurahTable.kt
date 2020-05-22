package id.dev.qurmer.data.database.surah

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "surah")
class SurahTable (
    @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "surahId")
    var surahId: Int? = null, // 1
    @ColumnInfo(name = "surahBadgeId")
    var surahBadgeId: Int? = null, // 1
    @ColumnInfo(name = "surahPath")
    var surahPath: String? = null,
    @ColumnInfo(name = "surahName")
    var surahName: String? = null,
    @ColumnInfo(name = "surahAudioId")
    var surahAudioId: Int? = null,
    @ColumnInfo(name = "surahAudioName")
    var surahAudioName: String? = null
) : Serializable {
}