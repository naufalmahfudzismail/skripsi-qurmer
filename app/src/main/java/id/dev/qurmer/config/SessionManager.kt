package id.dev.qurmer.config

import android.content.Context

class SessionManager private constructor(private val context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    val isLoggedIn: Boolean
        get() {
            val sharedPreferences =
                context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getString(TOKEN_KEY, null) != null
        }

    fun saveToken(token: String?) {
        val editor = sharedPreferences.edit()
        editor.putString(TOKEN_KEY, token!!)
        editor.apply()
    }

    fun setIntro() {
        val edit = sharedPreferences.edit()
        edit.putBoolean(INTRO_KEY, true)
        edit.apply()
    }

    fun setDownload() {
        val edit = sharedPreferences.edit()
        edit.putBoolean(DOWNLOAD_KEY, true)
        edit.apply()
    }

    fun setTimeAlarm(time: Long) {
        val edit = sharedPreferences.edit()
        edit.putLong(ALARM_KEY, time)
        edit.apply()
    }

    fun setDescriptionAlarm(desc : String){
        val edit = sharedPreferences.edit()
        edit.putString(DESC_ALARM, desc)
        edit.apply()
    }

    fun getDescriptionAlarm() = sharedPreferences.getString(DESC_ALARM, null)

    fun getTimeAlarm(): Long = sharedPreferences.getLong(ALARM_KEY, 1)
    fun getToken(): String? = sharedPreferences.getString(TOKEN_KEY, null)
    fun getIntro(): Boolean = sharedPreferences.getBoolean(INTRO_KEY, false)
    fun getDownloadStatus(): Boolean = sharedPreferences.getBoolean(DOWNLOAD_KEY, false)


    fun logOut() {
        val edit = sharedPreferences.edit()
        edit.remove(TOKEN_KEY)
        edit.apply()
    }


    companion object {


        const val SHARED_PREF_NAME = "QURMER_PREFERENCES"
        private const val INTRO_KEY = "intro"
        private const val TOKEN_KEY = "token"
        private const val DOWNLOAD_KEY = "download"
        private const val ALARM_KEY = "alarm"
        private const val DESC_ALARM = "desc_alarm"

        private var mInstance: SessionManager? = null

        @Synchronized
        fun getInstance(mCtx: Context): SessionManager {
            if (mInstance == null) {
                mInstance =
                    SessionManager(mCtx)
            }
            return mInstance as SessionManager
        }

    }
}