package id.dev.qurmer.utils

import android.app.Service
import android.content.Intent
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log


/**
 * Created By naufa on 07/02/2020
 */
open class BroadcastCountdownService(): Service (){

    private val TAG = "BroadcastService"

    companion object {
        const val COUNTDOWN_BR = "id.qurmar.countdown"
    }

    var bi = Intent(COUNTDOWN_BR)
    var cdt: CountDownTimer? = null



    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "Starting timer...")
        cdt = object : CountDownTimer(120000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.i(TAG, "Countdown seconds remaining: " + millisUntilFinished / 1000)
                bi.putExtra("finish", false)
                bi.putExtra("countdown", millisUntilFinished)
                sendBroadcast(bi)
            }

            override fun onFinish() {
                Log.i(TAG, "Timer finished")
                bi.putExtra("finish", true)
            }
        }
        cdt?.start()
    }

    override fun onDestroy() {
        cdt!!.cancel()
        Log.i(TAG, "Timer cancelled")
        super.onDestroy()
    }

    override fun onBind(arg0: Intent?): IBinder? {
        return null
    }

}