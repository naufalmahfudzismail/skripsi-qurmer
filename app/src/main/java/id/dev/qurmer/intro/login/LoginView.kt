package id.dev.qurmer.intro.login

import id.dev.qurmer.config.BaseView
import id.dev.qurmer.data.model.LoginResponse

interface LoginView : BaseView {

    fun onResult(result : LoginResponse?)
}