package id.dev.qurmer.intro.register

import id.dev.qurmer.config.BaseView
import id.dev.qurmer.data.model.CheckUsernameResponse
import id.dev.qurmer.data.model.RegisterDataResponse

interface RegisterView : BaseView {

    fun onResult(result : RegisterDataResponse?)

    fun onResultCheck(result : CheckUsernameResponse?)


}