package id.dev.qurmer.config

import android.util.Log

interface BaseView {
    fun startLoading()
    fun stopLoading()
    fun onError(error : String){
        Log.e("ErrorView", error)
    }
    fun onUnAuthorized()
}