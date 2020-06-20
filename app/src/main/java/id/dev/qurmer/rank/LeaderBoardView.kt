package id.dev.qurmer.rank

import id.dev.qurmer.config.BaseView
import id.dev.qurmer.data.model.RankResponse

interface LeaderBoardView : BaseView {

    fun onResult(result: RankResponse?)
}