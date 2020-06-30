package id.dev.qurmer.data.model


import com.google.gson.annotations.SerializedName

data class JoinChallengeResponse(
    @SerializedName("Data")
    var `data`: Data? = null,
    @SerializedName("Error")
    var error: Boolean? = null, // false
    @SerializedName("Message")
    var message: String? = null // success
) {
    data class Data(
        @SerializedName("challenge_id")
        var challengeId: String? = null, // 1
        @SerializedName("challenge_score")
        var challengeScore: Int? = null, // 2000
        @SerializedName("history_id")
        var historyId: Int? = null, // 10
        @SerializedName("is_done")
        var isDone: Boolean? = null, // false
        @SerializedName("progress_id")
        var progressId: Int? = null, // 16
        @SerializedName("user_id")
        var userId: Int? = null // 7
    )
}