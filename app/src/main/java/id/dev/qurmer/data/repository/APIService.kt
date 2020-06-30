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
        @Field("pekerjaan") work: String,
        @Field("password") password: String,
        @Field("username") username: String
    ): Deferred<Response<RegisterDataResponse>>

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("challenge-join")
    fun joinChallengeAsync(
        @Header("Authorization") token: String,
        @Field("challenge_id") challengeId: String
    ): Deferred<Response<JoinChallengeResponse>>

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("challenge-done")
    fun afterChallengeAsync(
        @Header("Authorization") token: String,
        @Field("challenge_id") challengeId: String,
        @Field("progress_id") progressId: String
    ): Deferred<Response<AfterChallengeResponse>>


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
    @GET("challenge")
    fun getStaticChallengeAsync(
        @Header("Authorization") token: String
    ): Deferred<Response<ChallengeResponse>>

    @Headers("Accept: application/json")
    @GET("challenge-daily")
    fun getDailyChallengeAsync(
        @Header("Authorization") token: String
    ): Deferred<Response<ChallengeResponse>>

    @Headers("Accept: application/json")
    @GET("history")
    fun getHistoryAsync(
        @Header("Authorization") token: String
    ): Deferred<Response<HistoryResponse>>


    @Headers("Accept: application/json")
    @GET("quote")
    fun getQuoteAsync(@Header("Authorization") token: String): Deferred<Response<QuoteDataResponse>>


}