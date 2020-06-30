package id.dev.qurmer.home.history

import id.dev.qurmer.config.BaseView
import id.dev.qurmer.data.model.HistoryResponse

interface HistoryView : BaseView {

    fun onResult(result : HistoryResponse?)
    fun onEmpty()
}