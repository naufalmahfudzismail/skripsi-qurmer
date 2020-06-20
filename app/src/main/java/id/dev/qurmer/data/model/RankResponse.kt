package id.dev.qurmer.data.model


import com.google.gson.annotations.SerializedName

data class RankResponse(
    @SerializedName("Data")
    var data: Data? = null,
    @SerializedName("Error")
    var error: Boolean? = null, // false
    @SerializedName("Message")
    var message: String? = null // success
) {
    data class Data(
        @SerializedName("current_user")
        var currentUser: CurrentUser? = null,
        @SerializedName("rank")
        var rank: List<Rank>? = null
    ) {
        data class CurrentUser(
            @SerializedName("alamat")
            var alamat: String? = null, // Jalan apel raya
            @SerializedName("created_at")
            var createdAt: String? = null, // 2020-05-31 07:21:53
            @SerializedName("email")
            var email: String? = null, // naufalismail67@yahoo.com
            @SerializedName("email_verified_at")
            var emailVerifiedAt: Any? = null, // null
            @SerializedName("foto_profil")
            var fotoProfil: Any? = null, // null
            @SerializedName("gender")
            var gender: String? = null, // Laki-laki
            @SerializedName("google_id")
            var googleId: Any? = null, // null
            @SerializedName("id")
            var id: Int? = null, // 2
            @SerializedName("nama")
            var nama: String? = null, // naufal
            @SerializedName("pekerjaan")
            var pekerjaan: String? = null, // Developer
            @SerializedName("progress")
            var progress: List<Progress>? = null,
            @SerializedName("rank")
            var rank: Int? = null, // 1
            @SerializedName("tanggal_lahir")
            var tanggalLahir: String? = null, // 2000-05-13
            @SerializedName("updated_at")
            var updatedAt: String? = null, // 2020-05-31 07:21:53
            @SerializedName("username")
            var username: String? = null // naufalismail67
        ) {
            data class Progress(
                @SerializedName("challenge")
                var challenge: Challenge? = null,
                @SerializedName("challenge_id")
                var challengeId: String? = null, // 10
                @SerializedName("created_at")
                var createdAt: String? = null, // 2020-05-31 07:23:07
                @SerializedName("id")
                var id: Int? = null, // 3
                @SerializedName("is_done")
                var isDone: String? = null, // 0
                @SerializedName("updated_at")
                var updatedAt: String? = null, // 2020-05-31 07:23:07
                @SerializedName("user_id")
                var userId: String? = null // 2
            ) {
                data class Challenge(
                    @SerializedName("created_at")
                    var createdAt: String? = null, // 2020-05-29 14:35:29
                    @SerializedName("daily")
                    var daily: String? = null, // 1
                    @SerializedName("id")
                    var id: Int? = null, // 10
                    @SerializedName("level_id")
                    var levelId: String? = null, // 1
                    @SerializedName("score")
                    var score: String? = null, // 1000
                    @SerializedName("surah_id")
                    var surahId: String? = null, // 5
                    @SerializedName("time")
                    var time: String? = null, // 15
                    @SerializedName("updated_at")
                    var updatedAt: String? = null // 2020-05-29 14:35:29
                )
            }
        }

        data class Rank(
            @SerializedName("created_at")
            var createdAt: String? = null, // 2020-05-31 07:21:53
            @SerializedName("id")
            var id: Int? = null, // 1
            @SerializedName("progress")
            var progress: List<Progress>? = null,
            @SerializedName("total_score")
            var totalScore: String? = null, // 0
            @SerializedName("updated_at")
            var updatedAt: String? = null, // 2020-05-31 10:15:56
            @SerializedName("user")
            var user: User? = null,
            @SerializedName("user_id")
            var userId: String? = null // 2
        ) {
            data class Progress(
                @SerializedName("challenge")
                var challenge: Challenge? = null,
                @SerializedName("challenge_id")
                var challengeId: String? = null, // 10
                @SerializedName("created_at")
                var createdAt: String? = null, // 2020-05-31 07:23:07
                @SerializedName("id")
                var id: Int? = null, // 3
                @SerializedName("is_done")
                var isDone: String? = null, // 0
                @SerializedName("updated_at")
                var updatedAt: String? = null, // 2020-05-31 07:23:07
                @SerializedName("user_id")
                var userId: String? = null // 2
            ) {
                data class Challenge(
                    @SerializedName("created_at")
                    var createdAt: String? = null, // 2020-05-29 14:35:29
                    @SerializedName("daily")
                    var daily: String? = null, // 1
                    @SerializedName("id")
                    var id: Int? = null, // 10
                    @SerializedName("level_id")
                    var levelId: String? = null, // 1
                    @SerializedName("score")
                    var score: String? = null, // 1000
                    @SerializedName("surah_id")
                    var surahId: String? = null, // 5
                    @SerializedName("time")
                    var time: String? = null, // 15
                    @SerializedName("updated_at")
                    var updatedAt: String? = null // 2020-05-29 14:35:29
                )
            }

            data class User(
                @SerializedName("alamat")
                var alamat: String? = null, // Jalan apel raya
                @SerializedName("created_at")
                var createdAt: String? = null, // 2020-05-31 07:21:53
                @SerializedName("email")
                var email: String? = null, // naufalismail67@yahoo.com
                @SerializedName("email_verified_at")
                var emailVerifiedAt: Any? = null, // null
                @SerializedName("foto_profil")
                var fotoProfil: Any? = null, // null
                @SerializedName("gender")
                var gender: String? = null, // Laki-laki
                @SerializedName("google_id")
                var googleId: Any? = null, // null
                @SerializedName("id")
                var id: Int? = null, // 2
                @SerializedName("nama")
                var nama: String? = null, // naufal
                @SerializedName("pekerjaan")
                var pekerjaan: String? = null, // Developer
                @SerializedName("progress")
                var progress: List<Progres?>? = null,
                @SerializedName("rank")
                var rank: Int? = null, // 1
                @SerializedName("tanggal_lahir")
                var tanggalLahir: String? = null, // 2000-05-13
                @SerializedName("updated_at")
                var updatedAt: String? = null, // 2020-05-31 07:21:53
                @SerializedName("username")
                var username: String? = null // naufalismail67
            ) {
                data class Progres(
                    @SerializedName("challenge")
                    var challenge: Challenge? = null,
                    @SerializedName("challenge_id")
                    var challengeId: String? = null, // 10
                    @SerializedName("created_at")
                    var createdAt: String? = null, // 2020-05-31 07:23:07
                    @SerializedName("id")
                    var id: Int? = null, // 3
                    @SerializedName("is_done")
                    var isDone: String? = null, // 0
                    @SerializedName("updated_at")
                    var updatedAt: String? = null, // 2020-05-31 07:23:07
                    @SerializedName("user_id")
                    var userId: String? = null // 2
                ) {
                    data class Challenge(
                        @SerializedName("created_at")
                        var createdAt: String? = null, // 2020-05-29 14:35:29
                        @SerializedName("daily")
                        var daily: String? = null, // 1
                        @SerializedName("id")
                        var id: Int? = null, // 10
                        @SerializedName("level_id")
                        var levelId: String? = null, // 1
                        @SerializedName("score")
                        var score: String? = null, // 1000
                        @SerializedName("surah_id")
                        var surahId: String? = null, // 5
                        @SerializedName("time")
                        var time: String? = null, // 15
                        @SerializedName("updated_at")
                        var updatedAt: String? = null // 2020-05-29 14:35:29
                    )
                }
            }
        }
    }
}