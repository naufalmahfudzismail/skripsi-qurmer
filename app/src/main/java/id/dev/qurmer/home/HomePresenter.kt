package id.dev.qurmer.home

import android.content.Context
import id.dev.qurmer.R
import id.dev.qurmer.config.BasePresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomePresenter(val context: Context, val view: HomeView) : BasePresenter() {

    fun getQuote() = try {

        view.startLoading()

        GlobalScope.launch(Dispatchers.Main) {

            val data = service.getQuoteAsync()
            val result = data.await()

            when (result.code()) {
                500 -> {
                    view.onError(context.getString(R.string.base_server_error))
                }

                200 -> {
                    view.onQuoteResult(result.body())
                }
                else -> {
                    view.onError(context.getString(R.string.base_not_identified))
                }
            }
            view.stopLoading()
        }

    } catch (t: Throwable) {
        view.onError(t.message.toString())
        view.stopLoading()

    }
}