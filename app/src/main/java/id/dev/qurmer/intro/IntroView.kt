package id.dev.qurmer.intro

import id.dev.qurmer.config.BaseView
import id.dev.qurmer.data.model.IntroDataResponse
import id.dev.qurmer.data.model.SurahAudioResponse

interface IntroView : BaseView {

    fun onResult(result : IntroDataResponse?)

}