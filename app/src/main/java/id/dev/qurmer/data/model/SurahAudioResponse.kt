package id.dev.qurmer.data.model


import com.google.gson.annotations.SerializedName

data class SurahAudioResponse(
    @SerializedName("Data")
    var data: List<AudioSurah>? = null,
    @SerializedName("Error")
    var error: Boolean? = null, // false
    @SerializedName("Message")
    var message: String? = null // success
) {
    data class AudioSurah(
        @SerializedName("created_at")
        var createdAt: String? = null, // 2020-05-16 21:04:04
        @SerializedName("file")
        var file: String? = null, // 114.mp3
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
    ) {
        data class Surah(
            @SerializedName("badge_id")
            var badgeId: Int? = null, // 1
            @SerializedName("basic_score")
            var basicScore: Any? = null, // null
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
        )
    }
}