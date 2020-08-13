package id.dev.qurmer.challenge.play

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import id.dev.qurmer.R
import id.dev.qurmer.challenge.ChallengeActivity
import id.dev.qurmer.challenge.ChallengePresenter
import id.dev.qurmer.challenge.ChallengeView
import id.dev.qurmer.challenge.FinishChallengeActivity
import id.dev.qurmer.challenge.adapter.AyatAnswerChallengeAdapter
import id.dev.qurmer.challenge.adapter.AyatChallengeAdapter
import id.dev.qurmer.config.BaseActivity
import id.dev.qurmer.config.DialogChallengeListener
import id.dev.qurmer.data.database.ayat.AyatTable
import id.dev.qurmer.data.database.ayat.AyatViewModel
import id.dev.qurmer.data.model.AfterChallengeResponse
import id.dev.qurmer.data.model.ChallengeResponse
import id.dev.qurmer.data.model.JoinChallengeResponse
import id.dev.qurmer.utils.BroadcastCountdownService
import kotlinx.android.synthetic.main.activity_play_ground_level_one.*
import java.util.concurrent.TimeUnit

class PlayGroundLevelOneActivity : BaseActivity(), ChallengeView {


    private var answers: MutableList<AyatTable> = mutableListOf()
    private var original: MutableList<AyatTable> = mutableListOf()
    private var shuffeled: MutableList<AyatTable> = mutableListOf()

    private lateinit var answerAdapter: AyatAnswerChallengeAdapter
    private lateinit var presenter: ChallengePresenter
    private lateinit var ayatAdapter: AyatChallengeAdapter
    private lateinit var ayatViewModel: AyatViewModel

    private var numberOfTry = 0
    private var numberOfAyat = 0

    private var timeChallenge: String? = null
    private var progressId: String? = null

    private lateinit var answerAyat: AyatTable

    private lateinit var data: ChallengeResponse.Data.Challenge
    var time = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_ground_level_one)

        data = intent.getSerializableExtra("data") as ChallengeResponse.Data.Challenge

        BroadcastCountdownService.TIME = data.time!!.toLong() * 60000

        presenter = ChallengePresenter(this, this)
        presenter.joinChallenge(getTokenWithBearer(), data.id.toString())

    }


    @SuppressLint("SetTextI18n")
    override fun onJoinChallenge(result: JoinChallengeResponse?) = runOnUiThread {

        super.onJoinChallenge(result)

        if (result != null) {
            ayatViewModel = ViewModelProviders.of(this).get(AyatViewModel::class.java)
            progressId = result.data?.progressId.toString()
            time = data.time!!.toInt()
            title_challenge.text = data.level?.name
            title_surah.text = "Surah ${data.surah?.nama}"

            answerAdapter =
                AyatAnswerChallengeAdapter(this)

            rv_soal.layoutManager = LinearLayoutManager(this)
            rv_soal.addItemDecoration(
                DividerItemDecoration(
                    this,
                    DividerItemDecoration.VERTICAL
                )
            )
            rv_soal.itemAnimator = DefaultItemAnimator()
            rv_soal.adapter = answerAdapter


            if (data.level?.id == 1) {
                startService(
                    Intent(this, BroadcastCountdownService::class.java).putExtra(
                        "time",
                        time.toLong()
                    )
                )
                data.surah?.id?.let { scrambleGame(it) }
            } else {
                startService(
                    Intent(this, BroadcastCountdownService::class.java).putExtra(
                        "time",
                        time.toLong()
                    )
                )
                data.surah?.id?.let {
                    nextAyatGame(it)
                }

            }

        }
    }

    override fun onAfterChallenge(result: AfterChallengeResponse?) = runOnUiThread {
        super.onAfterChallenge(result)

        if (result != null && result.data?.progressStatus!!) {
            val static = intent.getBooleanExtra("static", true)
            startActivityClearPreviousActivity<FinishChallengeActivity>(
                "data" to data,
                "try" to numberOfTry,
                "ayat" to numberOfAyat,
                "time" to timeChallenge,
                "static" to static
            )
        }

    }


    private fun scrambleGame(surahId: Int) {
        ayatViewModel.getAyat(surahId).observe(this, Observer {

            original.addAll(it)
            shuffeled.addAll(it.shuffled())

            val size = original.size

            ayatAdapter =
                AyatChallengeAdapter(
                    this, shuffeled
                ) { ayat, position ->
                    numberOfAyat++
                    number_of_ayat.text = numberOfAyat.toString()
                    answerAdapter.addAyat(ayat)

                    if (answerAdapter.getSize() == size) {
                        val finalResult = scrambleCheck(original, answerAdapter.ayats)
                        if (finalResult) {
                            unregisterReceiver(br)
                            presenter.afterChallenge(
                                getTokenWithBearer(),
                                data.id.toString(),
                                progressId.toString(),
                                numberOfTry.toString(),
                                (numberOfTry * data.wrongScore!!.toInt()).toString()
                            )
                        } else {
                            unregisterReceiver(br)
                            showDialogChallenge(
                                "Gagal",
                                "Tantangan gagal, ingin mengulang?",
                                object : DialogChallengeListener {
                                    override fun onPositiveClicked(dialog: Dialog) {
                                        dialog.dismiss()
                                        numberOfAyat = 0
                                        numberOfTry++

                                        number_of_ayat.text = numberOfAyat.toString()
                                        txt_number_of_try.text = numberOfTry.toString()


                                        ayatAdapter.restore(original.shuffled())
                                        answerAdapter.removeAll()

                                        registerReceiver(
                                            br, IntentFilter(BroadcastCountdownService.COUNTDOWN_BR)
                                        )

                                    }

                                    override fun onNegativeClicked(dialog: Dialog) {
                                        unregisterReceiver(br)
                                        finish()
                                        startActivityClearPreviousActivity<ChallengeActivity>()
                                    }

                                })
                        }
                    }
                }

            rv_jawaban.layoutManager = LinearLayoutManager(this)
            rv_jawaban.addItemDecoration(
                DividerItemDecoration(
                    this,
                    DividerItemDecoration.VERTICAL
                )
            )
            rv_jawaban.itemAnimator = DefaultItemAnimator()
            rv_jawaban.adapter = ayatAdapter

            btn_replay.setOnClickListener { view ->
                numberOfAyat = 0
                number_of_ayat.text = numberOfAyat.toString()
                ayatAdapter.restore(original.shuffled())
                answerAdapter.removeAll()
            }

        })
    }

    private fun pickUpAyat(ayats : List<AyatTable>){
        original.addAll(ayats)
        val tempAyat = original[original.lastIndex]
        original.remove(tempAyat)

        shuffeled.addAll(original.shuffled())
        original.forEachIndexed { index, ayat ->
            if (ayat.id == shuffeled[0].id) {
                val answerIndex = index + 1
                answerAyat = ayats[answerIndex]
            }
        }

        answerAdapter.addAyat(shuffeled[0])
        shuffeled.removeAt(0)
        shuffeled.add(tempAyat)

    }


    private fun nextAyatGame(surahId: Int) {
        txt_info_soal.text = "Pilihan Ayat acak, Pilih Ayat Selanjut nya"

        ayatViewModel.getAyat(surahId).observe(this, Observer { ayats ->

            pickUpAyat(ayats)

            ayatAdapter = AyatChallengeAdapter(
                this, shuffeled.shuffled() as MutableList<AyatTable>
            ) { ayat, position ->

                if (ayat == answerAyat) {
                    //jawaban bener
                    presenter.afterChallenge(
                        getTokenWithBearer(),
                        data.id.toString(),
                        progressId.toString(),
                        numberOfTry.toString(),
                        (numberOfTry * data.wrongScore!!.toInt()).toString()
                    )
                } else {
                    //jawaban salah
                    unregisterReceiver(br)
                    showDialogChallenge(
                        "Gagal",
                        "Tantangan gagal, ingin mengulang?",
                        object : DialogChallengeListener {
                            override fun onPositiveClicked(dialog: Dialog) {
                                dialog.dismiss()
                                numberOfTry++

                                txt_number_of_try.text = numberOfTry.toString()
                                ayatAdapter.restore(shuffeled.shuffled())

                                registerReceiver(
                                    br,
                                    IntentFilter(BroadcastCountdownService.COUNTDOWN_BR)
                                )

                            }

                            override fun onNegativeClicked(dialog: Dialog) {
                                unregisterReceiver(br)
                                finish()
                                startActivityClearPreviousActivity<ChallengeActivity>()
                            }

                        })
                }
            }

            rv_jawaban.layoutManager = LinearLayoutManager(this)
            rv_jawaban.addItemDecoration(
                DividerItemDecoration(
                    this,
                    DividerItemDecoration.VERTICAL
                )
            )
            rv_jawaban.itemAnimator = DefaultItemAnimator()
            rv_jawaban.adapter = ayatAdapter

            btn_replay.setOnClickListener { view ->
                makeToast("Peninjau ulang tidak di perkenankan dalam tantangan ini")
            }


        })
    }


    override fun onResult(result: ChallengeResponse?) {

    }

    override fun startLoading() {
        viewLoading()
    }

    override fun stopLoading() {
        hideLoading()
    }

    override fun onUnAuthorized() {

    }


    private fun scrambleCheck(
        original: List<AyatTable>,
        answers: List<AyatTable>
    ): Boolean {
        var result = true
        original.forEachIndexed { index, ayat ->
            if (ayat.id != answers[index].id) result = false
        }
        return result
    }


    private fun updateGUI(intent: Intent?) {
        if (intent?.extras != null) {
            val millis = intent.getLongExtra("countdown", 0)
            val times = String.format(
                "%d,%d",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
            )

            val time = times.split(",")

            txt_minutes.text = time[0]
            txt_seconds.text = time[1]

            timeChallenge = "${time[0]} : ${time[1]}"
            Log.i("waktu", times)

            if (times == "0,0") {
                showDialogChallenge(
                    "Waktu Habis",
                    "Maaf Waktu Tantangan sudah habis, apakah kamu ingin mengulang?",
                    object : DialogChallengeListener {
                        override fun onPositiveClicked(dialog: Dialog) {
                            finish()
                            startActivityClearPreviousActivity<ChallengeActivity>()
                        }

                        override fun onNegativeClicked(dialog: Dialog) {
                            finish()
                            startActivityClearPreviousActivity<ChallengeActivity>()
                        }

                    })
            }
        }
    }

    private
    val br = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, intent: Intent?) {
            intent!!.putExtra("time", time)
            updateGUI(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(br, IntentFilter(BroadcastCountdownService.COUNTDOWN_BR))
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(br)
    }

    override fun onStop() {
        try {
            unregisterReceiver(br)
        } catch (e: Exception) {
            // Receiver was probably already stopped in onPause()
        }
        super.onStop()
    }

    override fun onDestroy() {
        stopService(Intent(this, BroadcastCountdownService::class.java))
        super.onDestroy()
    }


}