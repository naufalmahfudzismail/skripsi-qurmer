package id.dev.qurmer.intro.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import id.dev.qurmer.MainActivity
import id.dev.qurmer.R
import id.dev.qurmer.config.BaseActivity
import id.dev.qurmer.data.database.user.UserTable
import id.dev.qurmer.data.database.user.UserViewModel
import id.dev.qurmer.data.model.LoginResponse
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), LoginView {


    private lateinit var loginPresenter: LoginPresenter
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_login)

        loginPresenter = LoginPresenter(this, this)
        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)

        btn_login.setOnClickListener {
            doLogin()
        }

        btn_gmail.setOnClickListener {
            registerGoogle()
        }
    }


    private fun doLogin() = try {

        val email = edt_email.text.toString()
        val pass = edt_password.text.toString()
        val validate = validateLogin(email, pass)

        if (validate) {
            Log.e("email", email)
            Log.e("Pass", pass)
            loginPresenter.login(email, pass)
        } else {
            null
        }
    } catch (e: Throwable) {

    }

    private fun validateLogin(email: String, pass: String): Boolean {
        /* txt_input_email.isErrorEnabled = true
         txt_input_pass.isErrorEnabled = true*/
        return when {
            email.trim().isEmpty() -> {
                edt_email.isFocusable = true
                edt_email.error = "Masukan Email Anda"
                edt_email.requestFocus()
                false
            }
            pass.trim().isEmpty() -> {
                edt_password_container.isErrorEnabled = true
                edt_password.error = "Masukan Password Anda"
                edt_password.requestFocus()
                false
            }
            else -> {
                true
            }
        }

    }

    override fun onResult(result: LoginResponse?) {
        try {
            if (!result?.error!!) {
                val token = result.data!!.token
                val user = result.data!!.user
                setToken(token.toString())
                userViewModel.insert(UserTable(
                    nama = user?.nama,
                    alamat = user?.alamat,
                    idUser = user?.id,
                    token = token,
                    tanggalLahir = user?.tanggalLahir,
                    email = user?.email,
                    pekerjaan = user?.pekerjaan,
                    gender = user?.gender,
                    updatedAt = user?.updatedAt
                ))
                startActivityClearPreviousActivity<MainActivity>()
            } else {
                makeLongToast(result.message.toString())
            }

        } catch (e: Throwable) {

        }
    }

    override fun startLoading() {
        viewLoading()
    }

    override fun stopLoading() {
        hideLoading()
    }

    override fun onUnAuthorized() {
    }

    override fun onError(error: String) {
        super.onError(error)
        makeLongToast(error)
    }

    private fun registerGoogle() {
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        val googleSignInClient = GoogleSignIn.getClient(this, gso)

        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, 101)
    }

    private fun onLoggedIn(googleSignInAccount: GoogleSignInAccount?) = try {
        Log.e(
            "Akun google",
            googleSignInAccount?.email.toString() + ", " + googleSignInAccount?.id
        )

        val providerId = googleSignInAccount?.id.toString()
        val email = googleSignInAccount?.email.toString()

        //registerPresenter.loginGoogle(providerId, email, coordinateGPS)

    } catch (e: Throwable) {
        // makeLongToast(e.message.toString())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            101 -> {
                try {
                    // The Task returned from this call is always completed, no need to attach
                    // a listener.
                    val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                    val account = task.getResult(ApiException::class.java)
                    onLoggedIn(account)
                } catch (e: ApiException) {
                    // The ApiException status code indicates the detailed failure reason.
                    Log.e(
                        "Login as Google",
                        "signInResult:failed code=" + e.statusCode
                    )
                    // makeLongToast("Login gagal, coba beberapa saat lagi")
                }
            }
            else -> {

            }
        }
    }


}
