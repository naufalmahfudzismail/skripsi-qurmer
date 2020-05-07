package id.dev.qurmer.intro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.dev.qurmer.BaseActivity
import id.dev.qurmer.R

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}
