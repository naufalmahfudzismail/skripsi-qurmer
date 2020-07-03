package id.dev.qurmer.media

import android.annotation.SuppressLint
import android.content.ComponentName
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import id.dev.qurmer.R
import id.dev.qurmer.config.BackgroundAudioService
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

    private var isPause = true

    private lateinit var ayatAdapter: AyatAdapter
    private lateinit var ayatViewModel: AyatViewModel
    private lateinit var mediaBrowserCompat: MediaBrowserCompat
    private lateinit var mediaControllerCompat: MediaControllerCompat

    private fun mediaBrowserCompatConnectionCallback(path: String, bundle: Bundle) =
        object : MediaBrowserCompat.ConnectionCallback() {
            override fun onConnected() {
                super.onConnected()
                try {
                    mediaControllerCompat = MediaControllerCompat(
                        this@SurahPlayerActivity,
                        mediaBrowserCompat.sessionToken
                    )
                    mediaControllerCompat.registerCallback(mediaControlCompatCallback)
                    mediaControllerCompat.transportControls.playFromMediaId(
                        path, bundle
                    )

                } catch (e: Throwable) {

                }
            }
        }

    private val mediaControlCompatCallback = object : MediaControllerCompat.Callback() {
        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            super.onPlaybackStateChanged(state)
            if (state == null) {
                return
            }

            when (state.state) {
                PlaybackStateCompat.STATE_PLAYING -> {
                    isPause = false
                }
                PlaybackStateCompat.STATE_PAUSED -> {
                    isPause = true
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_surah_player)

        surah = intent.getSerializableExtra("surah") as SurahTable
        ayatViewModel = ViewModelProviders.of(this).get(AyatViewModel::class.java)

        val bundle = Bundle()
        bundle.putString("title", surah.surahName!!)

        mediaBrowserCompat = MediaBrowserCompat(
            this, ComponentName(this, BackgroundAudioService::class.java),
            mediaBrowserCompatConnectionCallback(surah.surahPath!!, bundle), bundle
        )

        mediaBrowserCompat.connect()

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


    }

    private fun player() {

        btn_play_pause_audio.setOnClickListener {
            isPause = if (!isPause) {
                mediaPlayer.pause()
                btn_play_pause_audio.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_audio_play
                    )
                )

                /*if (mediaControllerCompat.playbackState.state == PlaybackStateCompat.STATE_PLAYING) {
                    mediaControllerCompat.transportControls.pause()
                }*/
                true
            } else {
                mediaPlayer.start()
                myHandler.postDelayed(updateSongTime, 100)
                //mediaControllerCompat.transportControls.play()
                btn_play_pause_audio.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_audio_pause
                    )
                )
                false
            }
        }

        btn_back_audio.setOnClickListener {
            mediaPlayer.reset()
            myHandler.postDelayed(updateSongTime, 100)
            btn_play_pause_audio.isEnabled = true
            btn_back_audio.isEnabled = false

        }

        btn_next_audio.setOnClickListener {
            //mediaPlayer.stop()
        }
    }

    private val updateSongTime: Runnable = object : Runnable {
        @SuppressLint("DefaultLocale")
        override fun run() {
            startTime = mediaPlayer.currentPosition.toDouble()
            txt_current_time.text = String.format(
                "%d:%d",
                TimeUnit.MILLISECONDS.toMinutes(startTime.toLong()),
                TimeUnit.MILLISECONDS.toSeconds(startTime.toLong()) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(startTime.toLong()))
            )
            seekBar_audio.progress = startTime.toInt()
            myHandler.postDelayed(this, 100)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
      /*  if (mediaControllerCompat.playbackState.state == PlaybackStateCompat.STATE_PLAYING) {
            mediaControllerCompat.transportControls.pause()
        }
        mediaBrowserCompat.disconnect()*/

        mediaPlayer.stop()

    }


}
