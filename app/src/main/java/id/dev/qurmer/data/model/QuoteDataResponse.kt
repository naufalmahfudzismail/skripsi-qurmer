package id.dev.qurmer.data.model


import com.google.gson.annotations.SerializedName

data class QuoteDataResponse(
    @SerializedName("Data")
    var quotes: List<Quote>? = null,
    @SerializedName("Error")
    var error: Boolean? = null, // false
    @SerializedName("Message")
    var message: String? = null // success
) {
    data class Quote(
        @SerializedName("created_at")
        var createdAt: String? = null, // 2020-05-28 11:36:18
        @SerializedName("description")
        var description: String? = null, // null
        @SerializedName("id")
        var id: Int? = null, // 1
        @SerializedName("image")
        var image: String? = null, //
        @SerializedName("image_url")
        var imageUrl: String? = null, // http://qurmer.skripsi-tik.xyz/image/quote/BYvCgOVDqcbvvkmY41HTr7K8alalwEdf-Banner Daily C1.png
        @SerializedName("title")
        var title: String? = null, // test
        @SerializedName("updated_at")
        var updatedAt: String? = null // 2020-05-28 11:36:18
    )
}