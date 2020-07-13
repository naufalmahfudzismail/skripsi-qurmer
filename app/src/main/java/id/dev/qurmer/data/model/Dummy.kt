package id.dev.qurmer.data.model


import com.google.gson.annotations.SerializedName

data class Dummy(
    @SerializedName("Data")
    var data: Data? = null,
    @SerializedName("Error")
    var error: Boolean? = null, // false
    @SerializedName("Message")
    var message: String? = null // success
) {
    data class Data(
        @SerializedName("daily")
        var daily: List<Daily>? = null,
        @SerializedName("progress")
        var progress: List<Progress>? = null,
        @SerializedName("score")
        var score: Score? = null,
        @SerializedName("user")
        var user: User? = null
    ) {
        data class Daily(
            @SerializedName("challenge")
            var challenge: Challenge? = null,
            @SerializedName("challenge_id")
            var challengeId: String? = null, // 95
            @SerializedName("created_at")
            var createdAt: String? = null, // 2020-06-30 10:37:12
            @SerializedName("id")
            var id: Int? = null, // 60
            @SerializedName("is_done")
            var isDone: String? = null, // 1
            @SerializedName("try")
            var tryX: String? = null, // 0
            @SerializedName("updated_at")
            var updatedAt: String? = null, // 2020-06-30 10:37:22
            @SerializedName("user_id")
            var userId: String? = null // 7
        ) {
            data class Challenge(
                @SerializedName("created_at")
                var createdAt: String? = null, // 2020-07-02 19:47:11
                @SerializedName("daily")
                var daily: String? = null, // 1
                @SerializedName("id")
                var id: Int? = null, // 95
                @SerializedName("level_id")
                var levelId: String? = null, // 2
                @SerializedName("score")
                var score: String? = null, // 2000
                @SerializedName("surah_id")
                var surahId: String? = null, // 18
                @SerializedName("time")
                var time: String? = null, // 1
                @SerializedName("updated_at")
                var updatedAt: String? = null, // 2020-07-02 19:47:11
                @SerializedName("wrong_score")
                var wrongScore: String? = null // 100
            )
        }

        data class Progress(
            @SerializedName("level")
            var level: String? = null, // 1
            @SerializedName("order_level")
            var orderLevel: Int? = null, // 1
            @SerializedName("surah")
            var surah: Surah? = null
        ) {
            data class Surah(
                @SerializedName("badge_id")
                var badgeId: String? = null, // 1
                @SerializedName("created_at")
                var createdAt: String? = null, // 2020-06-21 15:16:43
                @SerializedName("id")
                var id: Int? = null, // 3
                @SerializedName("jumlah_ayat")
                var jumlahAyat: String? = null, // 4
                @SerializedName("nama")
                var nama: String? = null, // Al-Ikhlas
                @SerializedName("updated_at")
                var updatedAt: String? = null, // 2020-06-21 15:16:43
                @SerializedName("urutan")
                var urutan: String? = null // 112
            )
        }

        data class Score(
            @SerializedName("created_at")
            var createdAt: String? = null, // 2020-06-15 05:40:16
            @SerializedName("id")
            var id: Int? = null, // 9
            @SerializedName("total_score")
            var totalScore: String? = null, // 21000
            @SerializedName("updated_at")
            var updatedAt: String? = null, // 2020-07-03 09:06:25
            @SerializedName("user_id")
            var userId: String? = null // 7
        )

        data class User(
            @SerializedName("alamat")
            var alamat: Any? = null, // null
            @SerializedName("created_at")
            var createdAt: String? = null, // 2020-06-15 05:40:16
            @SerializedName("email")
            var email: String? = null, // bambang123@gmail.com
            @SerializedName("email_verified_at")
            var emailVerifiedAt: Any? = null, // null
            @SerializedName("foto_profil")
            var fotoProfil: Any? = null, // null
            @SerializedName("gender")
            var gender: String? = null, // Laki Laki
            @SerializedName("google_id")
            var googleId: Any? = null, // null
            @SerializedName("id")
            var id: Int? = null, // 7
            @SerializedName("nama")
            var nama: String? = null, // bambang
            @SerializedName("pekerjaan")
            var pekerjaan: Any? = null, // null
            @SerializedName("rank")
            var rank: Int? = null, // 3
            @SerializedName("tanggal_lahir")
            var tanggalLahir: String? = null, // 0004-04-04
            @SerializedName("updated_at")
            var updatedAt: String? = null, // 2020-06-15 05:40:16
            @SerializedName("username")
            var username: String? = null // bambang_nax
        )
    }
}