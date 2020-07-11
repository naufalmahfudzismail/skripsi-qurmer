package id.dev.qurmer.config

import android.app.Application
import androidx.work.Configuration

class Qurmer : Application(), Configuration.Provider {
    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()
}
