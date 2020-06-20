package id.dev.qurmer.intro.register

import android.content.Context
import id.dev.qurmer.R
import id.dev.qurmer.config.BasePresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterPresenter(val context: Context, val view: RegisterView) : BasePresenter() {

    fun register(
        email: String, name: String,
        date: String,
        password: String, gender: String, username: String, work : String
    ) = try {
        view.startLoading()
        GlobalScope.launch(Dispatchers.IO) {

            val data = service.registerAsync(
                email = email,
                nama = name,
                tanggal = date,
                password = password,
                gender = gender,
                work = work,
                username = username
            )

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


    fun checkUserName(username: String) = try {

        view.startLoading()
        GlobalScope.launch(Dispatchers.Main) {


            val data = service.checkSurahNameAsync(username)
            val result = data.await()

            when (result.code()) {
                200 -> {
                    view.onResultCheck(result.body())
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