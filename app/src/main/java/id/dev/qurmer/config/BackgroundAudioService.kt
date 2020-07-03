package id.dev.qurmer.config

import android.app.PendingIntent
import android.content.*
import android.graphics.BitmapFactory
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.PowerManager
import android.os.ResultReceiver
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.text.TextUtils
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.media.MediaBrowserServiceCompat
import androidx.media.session.MediaButtonReceiver
import id.dev.qurmer.R
import java.io.File
import java.io.IOException


class BackgroundAudioService : MediaBrowserServiceCompat(), MediaPlayer.OnCompletionListener,
    AudioManager.OnAudioFocusChangeListener {

    private var mMediaPlayer: MediaPlayer? = null
    private var mMediaSessionCompat: MediaSessionCompat? = null


    private val mNoisyReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (mMediaPlayer != null && mMediaPlayer!!.isPlaying) {
                mMediaPlayer!!.pause()
            }
        }
    }

    private var mediaSessionCallBack = object : MediaSessionCompat.Callback() {

        override fun onPlay() {
            super.onPlay()
            if (!successfullyRetrievedAudioFocus()) {
                return;
            }

            mMediaSessionCompat!!.isActive = true;
            setMediaPlaybackState(PlaybackStateCompat.STATE_PLAYING);

            showPlayingNotification()
            mMediaPlayer!!.start()
        }

        override fun onPause() {
            super.onPause()
            if (this@BackgroundAudioService.mMediaPlayer!!.isPlaying) {
                this@BackgroundAudioService.mMediaPlayer!!.pause()
                setMediaPlaybackState(PlaybackStateCompat.STATE_PAUSED);
                showPausedNotification();
            }
        }

        override fun onPlayFromMediaId(mediaId: String?, extras: Bundle?) {
            super.onPlayFromMediaId(mediaId, extras)
            try {
                val afd = Uri.fromFile(File(mediaId!!))
                try {
                    mMediaPlayer!!.setDataSource(
                        applicationContext,
                        afd
                    )
                } catch (e: IllegalStateException) {
                    mMediaPlayer!!.release()
                    initMediaPlayer()
                    mMediaPlayer!!.setDataSource(
                        applicationContext,
                        afd
                    )
                }
                initMediaSessionMetadata(extras?.get("title").toString())
            } catch (e: IOException) {
                return
            }

            try {
                mMediaPlayer!!.prepare()
            } catch (e: IOException) {
            }
        }

        override fun onCommand(command: String?, extras: Bundle?, cb: ResultReceiver?) {
            super.onCommand(command, extras, cb)
        }

        override fun onSeekTo(pos: Long) {
            super.onSeekTo(pos)
        }
    }

    override fun onCreate() {
        super.onCreate()
        initMediaPlayer()
        initMediaSession()
        initNoisyReceiver()
    }

    private fun initNoisyReceiver() {
        //Handles headphones coming unplugged. cannot be done through a manifest receiver
        val filter = IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY)
        registerReceiver(mNoisyReceiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        val audioManager =
            getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.abandonAudioFocus(this)
        unregisterReceiver(mNoisyReceiver)
        mMediaSessionCompat!!.release()
        NotificationManagerCompat.from(this).cancel(1)
    }

    private fun initMediaPlayer() {
        mMediaPlayer = MediaPlayer()
        mMediaPlayer!!.setWakeMode(applicationContext, PowerManager.PARTIAL_WAKE_LOCK)
        mMediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mMediaPlayer!!.setVolume(1.0f, 1.0f)
    }

    private fun showPlayingNotification() {
        val builder: NotificationCompat.Builder =
            mMediaSessionCompat?.let { MediaStyleHelper.from(this, it) }
                ?: return
        builder.addAction(
            NotificationCompat.Action(
                R.drawable.ic_audio_pause,
                "Pause",
                MediaButtonReceiver.buildMediaButtonPendingIntent(
                    this,
                    PlaybackStateCompat.ACTION_PLAY_PAUSE
                )
            )
        )
        builder.setStyle(
            androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(0)
                .setMediaSession(mMediaSessionCompat!!.sessionToken)
        )
        builder.setSmallIcon(R.mipmap.ic_launcher)
        NotificationManagerCompat.from(this@BackgroundAudioService).notify(1, builder.build())
    }

    private fun showPausedNotification() {
        val builder: NotificationCompat.Builder = mMediaSessionCompat?.let {
            MediaStyleHelper.from(
                this,
                it
            )
        }
            ?: return
        builder.addAction(
            NotificationCompat.Action(
                R.drawable.ic_audio_play,
                "Play",
                MediaButtonReceiver.buildMediaButtonPendingIntent(
                    this,
                    PlaybackStateCompat.ACTION_PLAY_PAUSE
                )
            )
        )
        builder.setStyle(
            androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(0)
                .setMediaSession(mMediaSessionCompat!!.sessionToken)
        )
        builder.setSmallIcon(R.mipmap.ic_launcher)
        NotificationManagerCompat.from(this).notify(1, builder.build())
    }


    private fun initMediaSession() {
        val mediaButtonReceiver = ComponentName(
            applicationContext,
            MediaButtonReceiver::class.java
        )
        mMediaSessionCompat =
            MediaSessionCompat(applicationContext, "Tag", mediaButtonReceiver, null)
        mMediaSessionCompat!!.setCallback(mediaSessionCallBack)
        mMediaSessionCompat!!.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS)
        val mediaButtonIntent = Intent(Intent.ACTION_MEDIA_BUTTON)
        mediaButtonIntent.setClass(this, MediaButtonReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, mediaButtonIntent, 0)
        mMediaSessionCompat!!.setMediaButtonReceiver(pendingIntent)
        setSessionToken(mMediaSessionCompat!!.sessionToken)
    }

    private fun setMediaPlaybackState(state: Int) {
        val playbackstateBuilder = PlaybackStateCompat.Builder()
        if (state == PlaybackStateCompat.STATE_PLAYING) {
            playbackstateBuilder.setActions(PlaybackStateCompat.ACTION_PLAY_PAUSE or PlaybackStateCompat.ACTION_PAUSE)
        } else {
            playbackstateBuilder.setActions(PlaybackStateCompat.ACTION_PLAY_PAUSE or PlaybackStateCompat.ACTION_PLAY)
        }
        playbackstateBuilder.setState(state, PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN, 0f)
        mMediaSessionCompat!!.setPlaybackState(playbackstateBuilder.build())
    }

    private fun initMediaSessionMetadata(title: String) {
        val metadataBuilder = MediaMetadataCompat.Builder()
        //Notification icon in card
        metadataBuilder.putBitmap(
            MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON,
            BitmapFactory.decodeResource(resources, R.drawable.ic_icon_challenge)
        )
        metadataBuilder.putBitmap(
            MediaMetadataCompat.METADATA_KEY_ALBUM_ART,
            BitmapFactory.decodeResource(resources, R.drawable.ic_icon_challenge)
        )

        //lock screen icon for pre lollipop
        metadataBuilder.putBitmap(
            MediaMetadataCompat.METADATA_KEY_ART,
            BitmapFactory.decodeResource(resources, R.drawable.ic_icon_challenge)
        )
        metadataBuilder.putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE, title)
        metadataBuilder.putLong(MediaMetadataCompat.METADATA_KEY_TRACK_NUMBER, 1)
        metadataBuilder.putLong(MediaMetadataCompat.METADATA_KEY_NUM_TRACKS, 1)
        mMediaSessionCompat!!.setMetadata(metadataBuilder.build())
    }

    private fun successfullyRetrievedAudioFocus(): Boolean {
        val audioManager =
            getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val result = audioManager.requestAudioFocus(
            this,
            AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN
        )
        return result == AudioManager.AUDIOFOCUS_GAIN
    }


    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        result.sendResult(null)
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot? {
        return if (TextUtils.equals(clientPackageName, packageName)) {
            BrowserRoot(getString(R.string.app_name), null)
        } else null
    }

    override fun onCompletion(mp: MediaPlayer?) {
        if (mMediaPlayer != null) {
            mMediaPlayer!!.release();
        }
    }

    override fun onAudioFocusChange(focusChange: Int) {
        when (focusChange) {
            AudioManager.AUDIOFOCUS_LOSS -> {
                if (mMediaPlayer!!.isPlaying) {
                    mMediaPlayer!!.stop()
                }
            }
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
                mMediaPlayer!!.pause()
            }
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> {
                if (mMediaPlayer != null) {
                    mMediaPlayer!!.setVolume(0.3f, 0.3f)
                }
            }
            AudioManager.AUDIOFOCUS_GAIN -> {
                if (mMediaPlayer != null) {
                    if (!mMediaPlayer!!.isPlaying) {
                        mMediaPlayer!!.start()
                    }
                    mMediaPlayer!!.setVolume(1.0f, 1.0f)
                }
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        MediaButtonReceiver.handleIntent(mMediaSessionCompat, intent)
        return super.onStartCommand(intent, flags, startId)
    }


}