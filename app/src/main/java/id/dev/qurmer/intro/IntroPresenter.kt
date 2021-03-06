package id.dev.qurmer.intro

import android.content.Context
import id.dev.qurmer.R
import id.dev.qurmer.config.BasePresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class IntroPresenter(val context: Context, private val view: IntroView) : BasePresenter() {

    fun getAudioSurah() = try {
        view.startLoading()
        GlobalScope.launch(Dispatchers.Main) {
            val data = service.getAudioSurahAsync()
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
                    view.stopLoading()
                    view.onResult(result = result.body())
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
    }catch (e: Exception) {
        view.onError(e.message.toString())
        view.stopLoading()
    }
}