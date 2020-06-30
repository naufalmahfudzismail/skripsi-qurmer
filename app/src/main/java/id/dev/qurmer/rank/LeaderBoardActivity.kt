package id.dev.qurmer.rank

import android.os.Bundle
import androidx.core.content.ContextCompat
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

        txt_user_rank.text = result.data!!.currentUser?.rank.toString()
        txt_user_name.text = result.data!!.currentUser?.nama.toString()
        txt_user_point.text = result.data!!.currentUser?.score?.totalScore.toString()
        txt_user_level.text = getLevelUser(result.data!!.currentUser?.progress)

        img_profile_first.let {
            if (topThree[0].user?.gender == "Perempuan") {
                it.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_avatar_cewe))
            } else {
                it.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.mask_group))
            }
        }


        img_profile_second.let {
            if (topThree[1].user?.gender == "Perempuan") {
                it.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_avatar_cewe))
            } else {
                it.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.mask_group))
            }
        }


        img_profile_third.let {
            if (topThree[2].user?.gender == "Perempuan") {
                it.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_avatar_cewe))
            } else {
                it.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.mask_group))
            }
        }

        txt_level_first.text = getLevel(topThree[0].progress)
        txt_level_second.text = getLevel(topThree[1].progress)
        txt_level_third.text = getLevel(topThree[2].progress)


        data.removeAt(0)
        data.removeAt(0)
        data.removeAt(0)

        val adapter = LeaderBoardAdapter(this, data)
        rv_rank.layoutManager = LinearLayoutManager(this)
        rv_rank.itemAnimator = DefaultItemAnimator()
        rv_rank.adapter = adapter
    }

    private fun getLevel(progress: List<RankResponse.Data.Rank.Progress>?): String {
        var level = ""
        when {
            progress == null -> {
                level = "Level 1"
            }
            progress.isEmpty() -> level = "Level 1"
            else -> {
                val lvl1 = progress.filter { it.level == "1" }
                val lvl2 = progress.filter { it.level == "2" }
                when {
                    lvl1.size == 6 -> level = "Level 2"
                    lvl2.size == 9 -> level = "Level 3"
                    else -> {
                        progress.forEach {
                            level = "Level ${it.level}"
                        }
                    }
                }
            }
        }
        return level
    }

    private fun getLevelUser(progress: List<RankResponse.Data.CurrentUser.Progress>?): String {
        var level = ""
        when {
            progress == null -> {
                level = "Level 1"
            }
            progress.isEmpty() -> level = "Level 1"
            else -> {
                val lvl1 = progress.filter { it.level == "1" }
                val lvl2 = progress.filter { it.level == "2" }
                when {
                    lvl1.size == 6 -> level = "Level 2"
                    lvl2.size == 9 -> level = "Level 3"
                    else -> {
                        progress.forEach {
                            level = "Level ${it.level}"
                        }
                    }
                }
            }
        }
        return level
    }

    override fun startLoading() {
        viewLoading()
    }

    override fun stopLoading() {
        hideLoading()
    }

    override fun onUnAuthorized() {

    }
}