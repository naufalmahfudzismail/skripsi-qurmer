package id.dev.qurmer.data.model


import com.google.gson.annotations.SerializedName

data class ChallengeDataResponse(
    @SerializedName("Data")
    var challenge: List<Challenge>? = null,
    @SerializedName("Error")
    var error: Boolean? = null, // false
    @SerializedName("Message")
    var message: String? = null // success
) {
    data class Challenge(
        @SerializedName("created_at")
        var createdAt: String? = null, // 2020-05-26 08:26:44
        @SerializedName("daily")
        var daily: String? = null, // 1
        @SerializedName("id")
        var id: Int? = null, // 1
        @SerializedName("level_id")
        var levelId: String? = null, // 1
        @SerializedName("score")
        var score: String? = null, // 1000
        @SerializedName("surah")
        var surah: Surah? = null,
        @SerializedName("surah_id")
        var surahId: String? = null, // 1
        @SerializedName("time")
        var time: String? = null, // 5
        @SerializedName("updated_at")
        var updatedAt: String? = null // 2020-05-26 08:26:44
    ) {
        data class Surah(
            @SerializedName("badge_id")
            var badgeId: String? = null, // 1
            @SerializedName("basic_score")
            var basicScore: Any? = null, // null
            @SerializedName("created_at")
            var createdAt: String? = null, // 2020-05-26 07:47:01
            @SerializedName("id")
            var id: Int? = null, // 1
            @SerializedName("jumlah_ayat")
            var jumlahAyat: String? = null, // 6
            @SerializedName("nama")
            var nama: String? = null, // An-Nas
            @SerializedName("updated_at")
            var updatedAt: String? = null, // 2020-05-26 07:47:01
            @SerializedName("urutan")
            var urutan: String? = null // 114
        )
    }
}