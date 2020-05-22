package id.dev.qurmer.config

import android.content.Context
import android.content.SharedPreferences
import bolts.Bolts

class SessionManager private constructor(private val context: Context) {

    private val sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
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

    fun setDownload(){
        val edit = sharedPreferences.edit()
        edit.putBoolean(DOWNLOAD_KEY, true)
        edit.apply()
    }

    fun getToken(): String? = sharedPreferences.getString(TOKEN_KEY, null)
    fun getIntro() : Boolean  = sharedPreferences.getBoolean(INTRO_KEY, false)
    fun getDownloadStatus(): Boolean = sharedPreferences.getBoolean(DOWNLOAD_KEY, false)


    companion object {


        private const val SHARED_PREF_NAME = "QURMER_PREFERENCES"
        private const val INTRO_KEY = "intro"
        private const val TOKEN_KEY = "token"
        private const val DOWNLOAD_KEY = "download"

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