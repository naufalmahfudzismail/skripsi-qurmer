package id.dev.qurmer.home.history

import android.content.Context
import id.dev.qurmer.R
import id.dev.qurmer.config.BasePresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class HistoryPresenter(val context: Context, val view: HistoryView) : BasePresenter() {

    fun getHistory(token: String) = try {

        view.startLoading()

        GlobalScope.launch(Dispatchers.Main) {

            val data = service.getHistoryAsync(token)
            val result = data.await()

            if (result.body()?.data == null) {
                view.onEmpty()
                view.stopLoading()
            } else {
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
        }

    } catch (e: SocketTimeoutException) {
        view.onError(e.message.toString())
        view.stopLoading()
    } catch (e: Throwable) {
        view.onError(e.message.toString())
        view.stopLoading()
    }
}