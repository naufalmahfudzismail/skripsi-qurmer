package id.dev.qurmer.data.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class IntroDataResponse(
    @SerializedName("Data")
    var data: Data? = null,
    @SerializedName("Error")
    var error: Boolean? = null, // false
    @SerializedName("Message")
    var message: String? = null // success
) : Serializable {
    data class Data(
        @SerializedName("audio")
        var audio: List<Audio>? = null,
        @SerializedName("ayat")
        var ayat: List<Ayat>? = null
    ) : Serializable {
        data class Audio(
            @SerializedName("created_at")
            var createdAt: String? = null, // 2020-05-16 21:04:04
            @SerializedName("file")
            var `file`: String? = null, // 114.mp3
            @SerializedName("id")
            var id: Int? = null, // 1
            @SerializedName("name")
            var name: String? = null, // 114
            @SerializedName("surah")
            var surah: Surah? = null,
            @SerializedName("surah_id")
            var surahId: String? = null, // 1
            @SerializedName("updated_at")
            var updatedAt: String? = null // 2020-05-16 21:04:04
        ) : Serializable {
            data class Surah(
                @SerializedName("badge_id")
                var badgeId: Int? = null, // 1
                @SerializedName("basic_score")
                var basicScore: String? = null, // null
                @SerializedName("created_at")
                var createdAt: String? = null, // 2020-05-16 21:04:03
                @SerializedName("id")
                var id: Int? = null, // 1
                @SerializedName("jumlah_ayat")
                var jumlahAyat: String? = null, // 6
                @SerializedName("nama")
                var nama: String? = null, // An-Nas
                @SerializedName("updated_at")
                var updatedAt: String? = null, // 2020-05-16 21:04:03
                @SerializedName("urutan")
                var urutan: String? = null // 114
            ) : Serializable
        }

        data class Ayat(
            @SerializedName("ayat")
            var ayat: String? = null, // بِسْمِ ٱللَّهِ ٱلرَّحْمَٰنِ ٱلرَّحِيمِ عَمَّ يَتَسَآءَلُونَ
            @SerializedName("created_at")
            var createdAt: String? = null, // 2020-05-16 21:04:03
            @SerializedName("id")
            var id: Int? = null, // 1
            @SerializedName("surah_id")
            var surahId: String? = null, // 37
            @SerializedName("updated_at")
            var updatedAt: String? = null, // 2020-05-16 21:04:03
            @SerializedName("urutan_ayat")
            var urutanAyat: String? = null // 1
        ) : Serializable
    }
}