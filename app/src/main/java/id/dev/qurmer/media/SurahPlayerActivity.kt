package id.dev.qurmer.media

import android.annotation.SuppressLint
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.core.net.toFile
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import id.dev.qurmer.R
import id.dev.qurmer.config.BaseActivity
import id.dev.qurmer.data.database.ayat.AyatViewModel
import id.dev.qurmer.data.database.surah.SurahTable
import kotlinx.android.synthetic.main.activity_surah_player.*
import java.io.File
import java.util.concurrent.TimeUnit

@Suppress("DEPRECATION")
class SurahPlayerActivity : BaseActivity() {


    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var surah: SurahTable
    private val myHandler: Handler = Handler()
    private val forwardTime = 5000
    private val backwardTime = 5000
    private var oneTimeOnly = 0

    private var startTime = 0.0
    private var finalTime = 0.0

    private lateinit var ayatAdapter: AyatAdapter
    private lateinit var ayatViewModel: AyatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_surah_player)

        surah = intent.getSerializableExtra("surah") as SurahTable
        ayatViewModel = ViewModelProviders.of(this).get(AyatViewModel::class.java)

        ayatViewModel.getAyat(surah.surahId!!).observe(this, Observer {
            ayatAdapter = AyatAdapter(this, it)
            rv_ayat.layoutManager = LinearLayoutManager(this)
            rv_ayat.itemAnimator = DefaultItemAnimator()
            rv_ayat.adapter = ayatAdapter
        })

        val uri = Uri.fromFile(File(surah.surahPath!!))

        Log.e("FILE", uri.toFile().absolutePath)

        mediaPlayer = MediaPlayer().apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            setDataSource(
                applicationContext,
                uri
            ) //to set media source and send the object to the initialized state
            prepare() //to send the object to prepared state
        }

        init()
        player()

    }

    @SuppressLint("SetTextI18n")
    fun init() {

        finalTime = mediaPlayer.duration.toDouble()
        startTime = mediaPlayer.currentPosition.toDouble()

        if (oneTimeOnly == 0) {
            seekBar_audio.max = finalTime.toInt()
            oneTimeOnly = 1
        }


        txt_title_audio.text = "Surat ${surah.surahName}"

        txt_total_time.text = String.format(
            "%d:%d",
            TimeUnit.MILLISECONDS.toMinutes(finalTime.toLong()),
            TimeUnit.MILLISECONDS.toSeconds(finalTime.toLong()) -
                    TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(
                            finalTime.toLong()
                        )
                    )
        )

        txt_current_time.text = String.format(
            "%d:%d",
            TimeUnit.MILLISECONDS.toMinutes(startTime.toLong()),
            TimeUnit.MILLISECONDS.toSeconds(startTime.toLong()) -
                    TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(
                            startTime.toLong()
                        )
                    )
        )


        seekBar_audio.progress = startTime.toInt()

        seekBar_audio.isClickable = false
        btn_play_pause_audio.isEnabled = false


    }

    private fun player() {

        btn_play_pause_audio.setOnClickListener {
            mediaPlayer.pause()
            btn_play_pause_audio.isEnabled = false
            btn_back_audio.isEnabled = true

        }

        btn_back_audio.setOnClickListener {
            mediaPlayer.start()
            myHandler.postDelayed(updateSongTime, 100)
            btn_play_pause_audio.isEnabled = true
            btn_back_audio.isEnabled = false

        }

        btn_next_audio.setOnClickListener {

        }
    }

    private val updateSongTime: Runnable = object : Runnable {
        @SuppressLint("DefaultLocale")
        override fun run() {
            startTime = mediaPlayer.currentPosition.toDouble()
            txt_current_time.text = String.format(
                "%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(startTime.toLong()),
                TimeUnit.MILLISECONDS.toSeconds(startTime.toLong()) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(startTime.toLong()))
            )
            seekBar_audio.progress = startTime.toInt()
            myHandler.postDelayed(this, 100)
        }
    }
}
