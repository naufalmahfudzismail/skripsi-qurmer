package id.dev.qurmer.data.model


import com.google.gson.annotations.SerializedName

data class AfterChallengeResponse(
    @SerializedName("Data")
    var `data`: Data? = null,
    @SerializedName("Error")
    var error: Boolean? = null, // false
    @SerializedName("Message")
    var message: String? = null // success
) {
    data class Data(
        @SerializedName("final_score")
        var finalScore: Int? = null, // 2000
        @SerializedName("progress_status")
        var progressStatus: Boolean? = null // true
    )
}