package id.dev.qurmer.data.repository

import id.dev.qurmer.data.model.*
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

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("register")
    fun registerAsync(
        @Field("email") email: String,
        @Field("nama") nama: String,
        @Field("tanggal_lahir") tanggal: String,
        @Field("gender") gender: String,
        @Field("pekerjaan") work : String,
        @Field("password") password: String,
        @Field("username") username: String
    ): Deferred<Response<RegisterDataResponse>>

    @Headers("Accept: application/json")
    @GET("user")
    fun getUserAsync(
        @Header("Authorization") token: String
    ): Deferred<Response<UserResponse>>

    @Headers("Accept: application/json")
    @GET("rank")
    fun getRankAsync(
        @Header("Authorization") token: String
    ): Deferred<Response<RankResponse>>


    @Headers("Accept: application/json")
    @GET("username/{username}")
    fun checkSurahNameAsync(
        @Path("username") username: String
    ): Deferred<Response<CheckUsernameResponse>>


    @Headers("Accept: application/json")
    @GET("quote")
    fun getQuoteAsync(): Deferred<Response<QuoteDataResponse>>


}