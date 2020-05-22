package id.dev.qurmer.intro.login

import android.content.Context
import id.dev.qurmer.R
import id.dev.qurmer.config.BasePresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginPresenter(val context: Context, val view: LoginView) : BasePresenter() {


    fun login(email: String, password: String) = try {
        view.startLoading()
        GlobalScope.launch(Dispatchers.Main) {
            val data = service.loginAsync(email, password)
            val result = data.await()
            when (result.code()) {
                200 -> {
                    view.onResult(result = result.body())
                }
                404 -> {
                    view.onError(context.getString(R.string.base_not_found))
                }
                500 -> {
                    view.onError(context.getString(R.string.base_server_error))
                }
                else -> {
                    view.onError(context.getString(R.string.base_not_identified))
                }
            }
            view.stopLoading()
        }

    } catch (e: Throwable) {
        view.stopLoading()
        view.onError(e.message.toString())
    }
}