package id.dev.qurmer.rank

import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import id.dev.qurmer.R
import id.dev.qurmer.config.BaseActivity
import id.dev.qurmer.data.model.RankResponse
import kotlinx.android.synthetic.main.activity_leader_board.*

class LeaderBoardActivity : BaseActivity(), LeaderBoardView {

    private lateinit var presenter: LeaderBoardPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_board)

        presenter = LeaderBoardPresenter(this, this)
        presenter.getRank(getTokenWithBearer())
    }

    override fun onResult(result: RankResponse?) {

        val data = result?.data?.rank as MutableList<RankResponse.Data.Rank>
        val topThree = arrayOf(data[0], data[1], data[2])

        txt_name_first.text = topThree[0].user?.nama
        txt_point_first.text = topThree[0].totalScore

        txt_name_second.text = topThree[1].user?.nama
        txt_point_second.text = topThree[1].totalScore

        txt_name_third.text = topThree[2].user?.nama
        txt_point_third.text = topThree[2].totalScore


        data.removeAt(0)
        data.removeAt(0)
        data.removeAt(0)

        val adapter = LeaderBoardAdapter(this, data)
        rv_rank.layoutManager = LinearLayoutManager(this)
        rv_rank.itemAnimator = DefaultItemAnimator()
        rv_rank.adapter = adapter
    }

    override fun startLoading() {
        viewLoading()
    }

    override fun stopLoading() {
        hideLoading()
    }

    override fun onUnAuthorized() {
        TODO("Not yet implemented")
    }
}