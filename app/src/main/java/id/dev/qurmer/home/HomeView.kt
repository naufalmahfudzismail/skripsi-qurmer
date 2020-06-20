package id.dev.qurmer.home

import id.dev.qurmer.config.BaseView
import id.dev.qurmer.data.model.ChallengeDataResponse
import id.dev.qurmer.data.model.QuoteDataResponse

interface HomeView : BaseView {

    fun onQuoteResult(result : QuoteDataResponse?)
    fun onChallengeDaily(result : ChallengeDataResponse?)

}