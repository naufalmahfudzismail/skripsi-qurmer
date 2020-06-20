package id.dev.qurmer.config

import android.app.Application
import android.util.Log
import cafe.adriel.androidaudioconverter.AndroidAudioConverter
import cafe.adriel.androidaudioconverter.callback.ILoadCallback

class Qurmer : Application() {

    /*override fun onCreate() {
        super.onCreate()
        AndroidAudioConverter.load(this, object : ILoadCallback {

            override fun onSuccess() {
                Log.e("TEST", "SUCCESS")
            }

            override fun onFailure(error: Exception?) {
                Log.e("ERROR", error.toString())
            }
        })
    }*/
}