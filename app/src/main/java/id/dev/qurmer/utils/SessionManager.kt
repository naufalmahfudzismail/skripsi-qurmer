package id.dev.qurmer.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager private constructor(private val context: Context) {

    private val sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    val isLoggedIn: Boolean
        get() {
            val sharedPreferences =
                context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getString("token", null) != null
        }

    val user: SharedPreferences
        get() {
            val sharedPreferences =
                context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            sharedPreferences.getInt("id", 1)
            return sharedPreferences
        }


    fun saveUser(token: String?) {
        val editor = sharedPreferences.edit()
        editor.putString("token", token!!)
        editor.apply()

    }

    fun saveIdUser(id : String?){
        val editor = sharedPreferences.edit()
        editor.putString("id", id)
        editor.apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString("token", null)
    }

    fun getIdUser() : String?{
        return sharedPreferences.getString("id", null)
    }


    fun clearLogin(){
        val editor = sharedPreferences.edit()
        editor.remove("token").apply()
        editor.remove("id").apply()
        editor.apply()
    }

    fun setIntro() {
        val edit = sharedPreferences.edit()
        edit.putString(INTRO, "done")
        edit.apply()
    }

    fun getIntro() : String?{
        return sharedPreferences.getString(INTRO, "")
    }


    companion object {

        private val SHARED_PREF_NAME = "TURU_SHARED_PREFERENCE"
        private val INTRO = "intro"

        private var mInstance: SessionManager? = null

        @Synchronized
        fun getInstance(mCtx: Context): SessionManager {
            if (mInstance == null) {
                mInstance = SessionManager(mCtx)
            }
            return mInstance as SessionManager
        }

    }
}