package id.dev.qurmer.profile

import id.dev.qurmer.config.BaseView
import id.dev.qurmer.data.model.UserResponse

interface ProfileView : BaseView{
    fun onResult(result : UserResponse?)
}