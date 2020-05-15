package id.dev.qurmer

import androidx.appcompat.app.AppCompatActivity
import id.dev.qurmer.utils.SessionManager

abstract class BaseActivity : AppCompatActivity() {

    fun getTokenWithBearer(): String = "Bearer ${SessionManager.getInstance(this).getToken()}"
    fun loggedIn(): Boolean = SessionManager.getInstance(this).isLoggedIn

}