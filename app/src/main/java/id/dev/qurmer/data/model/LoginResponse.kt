package id.dev.qurmer.data.model


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("Data")
    var data: Data? = null,
    @SerializedName("Error")
    var error: Boolean? = null, // false
    @SerializedName("Message")
    var message: String? = null // success
) {
    data class Data(
        @SerializedName("token")
        var token: String? = null, // eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjU3MzM1MzQ5MDE4MmFkNWViN2JhM2UwZmZkM2M5YjVjODYzNjZiNzc2ZjkzY2E1NDBkN2FiN2ZkZWVmNDRiYmJlNjk3ZTk1ZjM1MDg2N2NjIn0.eyJhdWQiOiIxIiwianRpIjoiNTczMzUzNDkwMTgyYWQ1ZWI3YmEzZTBmZmQzYzliNWM4NjM2NmI3NzZmOTNjYTU0MGQ3YWI3ZmRlZWY0NGJiYmU2OTdlOTVmMzUwODY3Y2MiLCJpYXQiOjE1ODk2MzkxOTksIm5iZiI6MTU4OTYzOTE5OSwiZXhwIjoxNjIxMTc1MTk5LCJzdWIiOiIxIiwic2NvcGVzIjpbXX0.YoVZ_H9NnB68K6bwdQy_UhF9posB84zJrrS8NqyoTfRcRR6epLntRp-KybwPtlcOQqrJD8ByYBW1lQ5kUtWHw7jvEOjO8CIa-TpBu7p_5udGFN08YcHglIXXuzzjroatFxphb7yddnPfWlGmfSp-6OpNpl_jxjY-Onhh2RhYfwxug8-69rQ7qUVJjsUXNdD7XAVQRtCIRRlDjnlzUSI1FW7kfseiuTn2HH-PEkct386nLX3zMuiuRemX7buESEI_-ky_8CUv7-AxVGf2yUp323nm3uSNRTT5SZ_eUyqj6FdqU_Q2mZ8npiLew3kNVXPM2_3ycHV5OKIMXqJHnevYQybkHFze_1O8w0lzFdJrPFczDQFOVUos8zItJb9AFdJxwaWHnqGEfGcvoxNVNytTxfAiSJarNhQoZRPe6Fdvh4BChpWD55j6TSy8uJTOIDLUmlq4ioJThT0uT3GWhOgEFoSn6CV3naskpOVCz98PaiVgFfXWNWdgjLpu5JTUr7w26wC8xWEEaOZBO8Z0BqcernzLkyBYp2fCmx8iQmow3n7zvfMHiOvp7_p5m6UhphIf-Jl0G0KJycTjWYrtr_FJ3OkabDMk7gko786K0lt35sCcw-5ksTo1v7t5sG3JYgjMefIqr_3p_QbYsbQumcspM-jg1Tg4Q0M4jo-AK_5S-_s
        @SerializedName("user")
        var user: User? = null
    ) {
        data class User(
            @SerializedName("alamat")
            var alamat: String? = null, // Jalan apel raya
            @SerializedName("created_at")
            var createdAt: String? = null, // 2020-05-16 21:12:31
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
            var id: Int? = null, // 1
            @SerializedName("nama")
            var nama: String? = null, // naufal
            @SerializedName("pekerjaan")
            var pekerjaan: String? = null, // Developer
            @SerializedName("tanggal_lahir")
            var tanggalLahir: String? = null, // 2000-05-13
            @SerializedName("updated_at")
            var updatedAt: String? = null // 2020-05-16 21:12:31
        )
    }
}