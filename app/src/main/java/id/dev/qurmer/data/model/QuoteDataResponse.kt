package id.dev.qurmer.data.model


import com.google.gson.annotations.SerializedName

data class QuoteDataResponse(
    @SerializedName("Data")
    var `data`: Data? = null,
    @SerializedName("Error")
    var error: Boolean? = null, // false
    @SerializedName("Message")
    var message: String? = null // success
) {
    data class Data(
        @SerializedName("quote")
        var quotes: List<Quote>? = null,
        @SerializedName("user")
        var user: User? = null
    ) {
        data class Quote(
            @SerializedName("created_at")
            var createdAt: String? = null, // 2020-06-02 21:28:03
            @SerializedName("description")
            var description: Any? = null, // null
            @SerializedName("id")
            var id: Int? = null, // 1
            @SerializedName("image")
            var image: String? = null, // XTIZinDVfoK45NBKpXRF4HjBVGGLwJzk.png
            @SerializedName("image_url")
            var imageUrl: String? = null, // http://qurmer.skripsi-tik.xyz/image/quote/XTIZinDVfoK45NBKpXRF4HjBVGGLwJzk.png
            @SerializedName("title")
            var title: String? = null, // Keutamaan Menghafal Al-Qur'an
            @SerializedName("updated_at")
            var updatedAt: String? = null // 2020-06-02 21:28:03
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
            @SerializedName("tanggal_lahir")
            var tanggalLahir: String? = null, // 0004-04-04
            @SerializedName("updated_at")
            var updatedAt: String? = null, // 2020-06-15 05:40:16
            @SerializedName("username")
            var username: String? = null // bambang_nax
        )
    }
}