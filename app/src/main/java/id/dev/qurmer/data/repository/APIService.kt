package id.dev.qurmer.data.repository

import id.dev.qurmer.data.model.IntroDataResponse
import id.dev.qurmer.data.model.LoginResponse
import id.dev.qurmer.data.model.SurahAudioResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface APIService {

    @Headers("Accept: application/json")
    @GET("surah-audio")
    fun getAudioSurahAsync(): Deferred<Response<IntroDataResponse>>


    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("login")
    fun loginAsync(
        @Field("email") email: String,
        @Field("password") password: String
    ): Deferred<Response<LoginResponse>>

}