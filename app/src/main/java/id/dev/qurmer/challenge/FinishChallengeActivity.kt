package id.dev.qurmer.challenge

import android.os.Bundle
import id.dev.qurmer.R
import id.dev.qurmer.config.BaseActivity
import id.dev.qurmer.data.model.ChallengeResponse
import id.dev.qurmer.home.challenge.DailyChallengeActivity
import kotlinx.android.synthetic.main.activity_finisih_challenge.*

class FinishChallengeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finisih_challenge)

        val numTry = intent.getIntExtra("try", 0)
        val numAyah = intent.getIntExtra("ayat", 0)
        val time = intent.getStringExtra("time")
        val static = intent.getBooleanExtra("static", true)

        val data = intent.getSerializableExtra("data") as ChallengeResponse.Data.Challenge

        txt_num_try.text = numTry.toString()
        txt_num_ayat.text = numAyah.toString()
        txt_time.text = time.toString()

        val totalScore = data.score!!.toInt() + data.level?.bonusScore!!.toInt()
        txt_score.text = "$totalScore XP"

        btn_next.setOnClickListener {
            if (static) startActivityClearPreviousActivity<ChallengeActivity>()
            else startActivityClearPreviousActivity<DailyChallengeActivity>()
        }


    }
}