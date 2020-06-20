package id.dev.qurmer.data.model


import com.google.gson.annotations.SerializedName

data class RegisterDataResponse(
    @SerializedName("Data")
    var `data`: Data? = null,
    @SerializedName("Error")
    var error: Boolean? = null, // false
    @SerializedName("Message")
    var message: String? = null // success
) {
    data class Data(
        @SerializedName("alamat")
        var alamat: String? = null, // Jalan apel raya
        @SerializedName("email")
        var email: String? = null, // naufalmahfudzismail@gmail.com
        @SerializedName("foto_profil")
        var fotoProfil: Any? = null, // null
        @SerializedName("gender")
        var gender: String? = null, // Laki-laki
        @SerializedName("google_id")
        var googleId: Any? = null, // null
        @SerializedName("nama")
        var nama: String? = null, // naufal
        @SerializedName("password")
        var password: String? = null, // $2y$10$bjIUcGKEPeMliY4imasiBeyAeIfnueHBZEHHSUCkwnISsghk8vOBi
        @SerializedName("pekerjaan")
        var pekerjaan: String? = null, // Developer
        @SerializedName("tanggal_lahir")
        var tanggalLahir: String? = null, // 2000-05-13
        @SerializedName("user")
        var user: User? = null
    ) {
        data class User(
            @SerializedName("alamat")
            var alamat: String? = null, // Jalan apel raya
            @SerializedName("created_at")
            var createdAt: String? = null, // 2020-05-29 14:35:49
            @SerializedName("email")
            var email: String? = null, // naufalmahfudzismail@gmail.com
            @SerializedName("gender")
            var gender: String? = null, // Laki-laki
            @SerializedName("id")
            var id: Int? = null, // 1
            @SerializedName("nama")
            var nama: String? = null, // naufal
            @SerializedName("pekerjaan")
            var pekerjaan: String? = null, // Developer
            @SerializedName("tanggal_lahir")
            var tanggalLahir: String? = null, // 2000-05-13
            @SerializedName("updated_at")
            var updatedAt: String? = null // 2020-05-29 14:35:49
        )
    }
}