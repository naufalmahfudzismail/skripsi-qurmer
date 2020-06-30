package id.dev.qurmer.data.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ChallengeResponse(
    @SerializedName("Data")
    var data: Data? = null,
    @SerializedName("Error")
    var error: Boolean? = null, // false
    @SerializedName("Message")
    var message: String? = null // success
) : Serializable {
    data class Data(
        @SerializedName("challenge")
        var challenge: List<Challenge>? = null,
        @SerializedName("progress")
        var progress: List<Progress>? = null
    ) : Serializable {
        data class Challenge(
            @SerializedName("created_at")
            var createdAt: String? = null, // 2020-06-21 15:16:44
            @SerializedName("daily")
            var daily: String? = null, // 0
            @SerializedName("id")
            var id: Int? = null, // 1
            @SerializedName("level")
            var level: Level? = null,
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
            var updatedAt: String? = null // 2020-06-21 15:16:44
        ) : Serializable {
            data class Level(
                @SerializedName("bonus_score")
                var bonusScore: String? = null, // 1000
                @SerializedName("created_at")
                var createdAt: String? = null, // 2020-06-21 15:16:44
                @SerializedName("id")
                var id: Int? = null, // 1
                @SerializedName("level")
                var level: String? = null, // 1
                @SerializedName("name")
                var name: String? = null, // Susun Ayat
                @SerializedName("updated_at")
                var updatedAt: String? = null // 2020-06-21 15:16:44
            ) : Serializable

            data class Surah(
                @SerializedName("badge_id")
                var badgeId: String? = null, // 1
                @SerializedName("basic_score")
                var basicScore: Any? = null, // null
                @SerializedName("created_at")
                var createdAt: String? = null, // 2020-06-21 15:16:43
                @SerializedName("id")
                var id: Int? = null, // 1
                @SerializedName("jumlah_ayat")
                var jumlahAyat: String? = null, // 6
                @SerializedName("nama")
                var nama: String? = null, // An-Nas
                @SerializedName("updated_at")
                var updatedAt: String? = null, // 2020-06-21 15:16:43
                @SerializedName("urutan")
                var urutan: String? = null // 114
            ) : Serializable
        }

        data class Progress(
            @SerializedName("level")
            var level: String? = null, // 1
            @SerializedName("order_level")
            var orderLevel: Int? = null, // 1
            @SerializedName("surah")
            var surah: Surah? = null
        ) : Serializable {
            data class Surah(
                @SerializedName("badge_id")
                var badgeId: String? = null, // 1
                @SerializedName("basic_score")
                var basicScore: Any? = null, // null
                @SerializedName("created_at")
                var createdAt: String? = null, // 2020-06-21 15:16:43
                @SerializedName("id")
                var id: Int? = null, // 1
                @SerializedName("jumlah_ayat")
                var jumlahAyat: String? = null, // 6
                @SerializedName("nama")
                var nama: String? = null, // An-Nas
                @SerializedName("updated_at")
                var updatedAt: String? = null, // 2020-06-21 15:16:43
                @SerializedName("urutan")
                var urutan: String? = null // 114
            ) : Serializable
        }
    }
}