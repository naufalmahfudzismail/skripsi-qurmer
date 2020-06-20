package id.dev.qurmer.profile

import android.content.Context
import id.dev.qurmer.R
import id.dev.qurmer.config.BasePresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class ProfilePresenter(val context: Context, val view: ProfileView) : BasePresenter() {


    fun getUser(token: String) = try {

        view.startLoading()

        GlobalScope.launch(Dispatchers.IO) {

            val data = service.getUserAsync(token)
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
}