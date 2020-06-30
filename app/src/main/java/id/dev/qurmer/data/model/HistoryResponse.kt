package id.dev.qurmer.data.model


import com.google.gson.annotations.SerializedName

data class HistoryResponse(
    @SerializedName("Data")
    var `data`: List<Data>? = null,
    @SerializedName("Error")
    var error: Boolean? = null, // false
    @SerializedName("Message")
    var message: String? = null // success
) {
    data class Data(
        @SerializedName("activity_id")
        var activityId: String? = null, // 26
        @SerializedName("activity_name")
        var activityName: String? = null, // progress
        @SerializedName("created_at")
        var createdAt: String? = null, // 2020-06-28 12:01:14
        @SerializedName("history_key")
        var historyKey: Any? = null, // null
        @SerializedName("id")
        var id: Int? = null, // 20
        @SerializedName("progress")
        var progress: Progress? = null,
        @SerializedName("updated_at")
        var updatedAt: String? = null, // 2020-06-28 12:01:14
        @SerializedName("user_id")
        var userId: String? = null // 7
    ) {
        data class Progress(
            @SerializedName("challenge")
            var challenge: Challenge? = null,
            @SerializedName("challenge_id")
            var challengeId: String? = null, // 16
            @SerializedName("created_at")
            var createdAt: String? = null, // 2020-06-28 12:01:14
            @SerializedName("id")
            var id: Int? = null, // 26
            @SerializedName("is_done")
            var isDone: String? = null, // 0
            @SerializedName("updated_at")
            var updatedAt: String? = null, // 2020-06-28 12:01:14
            @SerializedName("user_id")
            var userId: String? = null // 7
        ) {
            data class Challenge(
                @SerializedName("created_at")
                var createdAt: String? = null, // 2020-06-21 15:16:44
                @SerializedName("daily")
                var daily: String? = null, // 0
                @SerializedName("id")
                var id: Int? = null, // 16
                @SerializedName("level")
                var level: Level? = null,
                @SerializedName("level_id")
                var levelId: String? = null, // 1
                @SerializedName("score")
                var score: String? = null, // 1000
                @SerializedName("surah")
                var surah: Surah? = null,
                @SerializedName("surah_id")
                var surahId: String? = null, // 4
                @SerializedName("time")
                var time: String? = null, // 5
                @SerializedName("updated_at")
                var updatedAt: String? = null // 2020-06-21 15:16:44
            ) {
                data class Level(
                    @SerializedName("bonus_score")
                    var bonusScore: String? = null, // 1500
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
                )

                data class Surah(
                    @SerializedName("badge_id")
                    var badgeId: String? = null, // 1
                    @SerializedName("created_at")
                    var createdAt: String? = null, // 2020-06-21 15:16:43
                    @SerializedName("id")
                    var id: Int? = null, // 4
                    @SerializedName("jumlah_ayat")
                    var jumlahAyat: String? = null, // 5
                    @SerializedName("nama")
                    var nama: String? = null, // Al-lahab
                    @SerializedName("updated_at")
                    var updatedAt: String? = null, // 2020-06-21 15:16:43
                    @SerializedName("urutan")
                    var urutan: String? = null // 111
                )
            }
        }
    }
}