package id.dev.qurmer.home.challenge

import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import id.dev.qurmer.MainActivity
import id.dev.qurmer.R
import id.dev.qurmer.challenge.ChallengePresenter
import id.dev.qurmer.challenge.ChallengeView
import id.dev.qurmer.challenge.OverviewChallengeActivity
import id.dev.qurmer.config.BaseActivity
import id.dev.qurmer.data.model.ChallengeResponse
import kotlinx.android.synthetic.main.activity_daily_challenge.*
import java.util.*

class DailyChallengeActivity : BaseActivity(), ChallengeView {


    private lateinit var presenter: ChallengePresenter
    private lateinit var adapter: DailyChallengeAdapter

    private var challenges: MutableList<ChallengeResponse.Data.Challenge> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_challenge)

        presenter = ChallengePresenter(this, this)
        presenter.getDailyChallenge(getTokenWithBearer())

        ll_back_daily.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onResult(result: ChallengeResponse?) {

        if (result != null) {
            val challenge = result.data?.challenge
            val lvl1 = challenge?.filter { it.level?.level == "1" }
            lvl1?.let { challenges.addAll(it) }

            adapter = DailyChallengeAdapter(this, lvl1!!) {
                startActivityWithIntent<OverviewChallengeActivity>(
                    "data" to it, "level" to 1,
                    "static" to false
                )
            }

            rv_daily_challenge.layoutManager = GridLayoutManager(this, 2)
            rv_daily_challenge.itemAnimator = DefaultItemAnimator()
            rv_daily_challenge.adapter = adapter

            initSearch()

        }
    }

    override fun startLoading() {
        viewLoading()
    }

    override fun stopLoading() {
        hideLoading()
    }

    override fun onUnAuthorized() {

    }

    private fun initSearch() {
        search_surah.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {
                val data = challenges.filter {
                    newText?.toLowerCase(Locale.ROOT)
                        ?.let { it1 -> it.surah!!.nama?.toLowerCase(Locale.ROOT)?.contains(it1) }!!
                }
                adapter.updateData(data)
                return false
            }

        })
    }

    override fun onBackPressed() {
        startActivityClearPreviousActivity<MainActivity>()
    }


}