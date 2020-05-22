package id.dev.qurmer.data.database.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user")
class UserTable(
    @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "alamat")
    var alamat: String? = null, // Jalan apel raya
    @ColumnInfo(name = "created_at")
    var createdAt: String? = null, // 2020-05-16 21:12:31
    @ColumnInfo(name = "email")
    var email: String? = null, // naufalismail67@yahoo.com
    @ColumnInfo(name = "gender")
    var gender: String? = null, // Laki-laki
    @ColumnInfo(name = "idUser")
    var idUser: Int? = null, // 1
    @ColumnInfo(name = "nama")
    var nama: String? = null, // naufal
    @ColumnInfo(name = "pekerjaan")
    var pekerjaan: String? = null, // Developer
    @ColumnInfo(name = "tanggalLahir")
    var tanggalLahir: String? = null, // 2000-05-13
    @ColumnInfo(name = "token")
    var token: String? = null, // 2000-05-13
    @ColumnInfo(name = "updatedAt")
    var updatedAt: String? = null // 2020-05-16 21:12:31
)