package id.dev.qurmer.data.model


import com.google.gson.annotations.SerializedName

data class CheckUsernameResponse(
    @SerializedName("Data")
    var `data`: Data? = null,
    @SerializedName("Error")
    var error: Boolean? = null, // false
    @SerializedName("Message")
    var message: String? = null // success
) {
    data class Data(
        @SerializedName("terdaftar")
        var terdaftar: Boolean? = null, // true
        @SerializedName("username")
        var username: String? = null // naufalmahfudzismail
    )
}