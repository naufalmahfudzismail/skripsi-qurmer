package id.dev.qurmer.challenge

import android.os.Bundle
import id.dev.qurmer.R
import id.dev.qurmer.challenge.play.PlayGroundLevelOneActivity
import id.dev.qurmer.challenge.play.PlayGroundLevelTwoActivity
import id.dev.qurmer.config.BaseActivity
import id.dev.qurmer.data.model.ChallengeResponse
import kotlinx.android.synthetic.main.activity_overview_challenge.*

class OverviewChallengeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview_challenge)


        val challenge =
            intent.getSerializableExtra("data") as ChallengeResponse.Data.Challenge

        val level = intent.getIntExtra("level", 1)

        val static = intent.getBooleanExtra("static", true)

        txt_surah_game.text = challenge.surah?.nama.toString()
        txt_challenge_title.text = challenge.level?.name.toString()
        val totalScore = challenge.score!!.toInt() + challenge.level?.bonusScore!!.toInt()

        txt_point_info.text = "Point $totalScore XP"

        btn_play_challenge.setOnClickListener {
            when (level) {
                1 -> {
                    startActivityWithIntent<PlayGroundLevelOneActivity>(
                        "data" to challenge,
                        "level" to level,
                        "static" to static
                    )
                }

                2 -> {
                    startActivityWithIntent<PlayGroundLevelTwoActivity>(
                        "data" to challenge,
                        "level" to level,
                        "static" to static
                    )
                }

                3 -> {
                    startActivityWithIntent<PlayGroundLevelTwoActivity>(
                        "data" to challenge,
                        "level" to level,
                        "static" to static
                    )
                }
            }

        }


    }
}