package id.dev.qurmer.challenge

import android.content.Context
import id.dev.qurmer.R
import id.dev.qurmer.config.BasePresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class ChallengePresenter(val context: Context, val view: ChallengeView) : BasePresenter() {

    fun getStaticChallenge(token: String) = try {
        view.startLoading()
        GlobalScope.launch(Dispatchers.Main) {
            val data = service.getStaticChallengeAsync(token)
            val result = data.await()
            when (result.code()) {
                500 -> {
                    view.onError(context.getString(R.string.base_server_error))
                    view.stopLoading()
                }
                404 -> {
                    view.onError(context.getString(R.string.base_not_found))
                    view.stopLoading()
                }
                200 -> {
                    view.onResult(result = result.body())
                    view.stopLoading()
                }
                else -> {
                    view.onError(context.getString(R.string.base_not_identified))
                    view.stopLoading()
                }
            }
        }
    } catch (e: SocketTimeoutException) {
        view.onError(e.message.toString())
        view.stopLoading()
    } catch (e: Throwable) {
        view.onError(e.message.toString())
        view.stopLoading()
    }


    fun getDailyChallenge(token: String) = try {
        view.startLoading()
        GlobalScope.launch(Dispatchers.Main) {
            val data = service.getDailyChallengeAsync(token)
            val result = data.await()
            when (result.code()) {
                500 -> {
                    view.onError(context.getString(R.string.base_server_error))
                    view.stopLoading()
                }
                404 -> {
                    view.onError(context.getString(R.string.base_not_found))
                    view.stopLoading()
                }
                200 -> {
                    view.onResult(result = result.body())
                    view.stopLoading()
                }
                else -> {
                    view.onError(context.getString(R.string.base_not_identified))
                    view.stopLoading()
                }
            }
        }
    } catch (e: SocketTimeoutException) {
        view.onError(e.message.toString())
        view.stopLoading()
    } catch (e: Throwable) {
        view.onError(e.message.toString())
        view.stopLoading()
    }


    fun joinChallenge(token: String, challengeId: String) = try {
        view.startLoading()
        GlobalScope.launch(Dispatchers.IO) {
            val data = service.joinChallengeAsync(token, challengeId)
            val result = data.await()
            when (result.code()) {
                500 -> {
                    view.onError(context.getString(R.string.base_server_error))
                    view.stopLoading()
                }
                404 -> {
                    view.onError(context.getString(R.string.base_not_found))
                    view.stopLoading()
                }
                200 -> {
                    view.onJoinChallenge(result = result.body())
                    view.stopLoading()
                }
                else -> {
                    view.onError(context.getString(R.string.base_not_identified))
                    view.stopLoading()
                }
            }
        }
    } catch (e: SocketTimeoutException) {
        view.onError(e.message.toString())
        view.stopLoading()
    } catch (e: Throwable) {
        view.onError(e.message.toString())
        view.stopLoading()
    }

    fun afterChallenge(
        token: String,
        challengeId: String,
        progressId: String,
        jumlahSalah: String,
        totalSalah: String
    ) = try {
        view.startLoading()
        GlobalScope.launch(Dispatchers.IO) {
            val data =
                service.afterChallengeAsync(token, challengeId, progressId, jumlahSalah, totalSalah)
            val result = data.await()
            when (result.code()) {
                500 -> {
                    view.onError(context.getString(R.string.base_server_error))
                    view.stopLoading()
                }
                404 -> {
                    view.onError(context.getString(R.string.base_not_found))
                    view.stopLoading()
                }
                200 -> {
                    view.onAfterChallenge(result = result.body())
                    view.stopLoading()
                }
                else -> {
                    view.onError(context.getString(R.string.base_not_identified))
                    view.stopLoading()
                }
            }
        }
    } catch (e: SocketTimeoutException) {
        view.onError(e.message.toString())
        view.stopLoading()
    } catch (e: Throwable) {
        view.onError(e.message.toString())
        view.stopLoading()
    }
}