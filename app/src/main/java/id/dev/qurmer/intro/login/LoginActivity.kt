package id.dev.qurmer.intro.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import id.dev.qurmer.MainActivity
import id.dev.qurmer.R
import id.dev.qurmer.config.BaseActivity
import id.dev.qurmer.data.database.hash.HashViewModel
import id.dev.qurmer.data.database.surah.SurahViewModel
import id.dev.qurmer.data.database.user.UserTable
import id.dev.qurmer.data.database.user.UserViewModel
import id.dev.qurmer.data.model.LoginResponse
import id.dev.qurmer.utils.fingerprint.operation.OperationHash
import id.dev.qurmer.utils.fingerprint.operation.SearchMatch
import kotlinx.android.synthetic.main.activity_login.*
import java.io.File

class LoginActivity : BaseActivity(), LoginView {


    private lateinit var loginPresenter: LoginPresenter
    private lateinit var userViewModel: UserViewModel
    private lateinit var surahViewModel: SurahViewModel
    private lateinit var hashViewModel: HashViewModel

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
        surahViewModel = ViewModelProviders.of(this).get(SurahViewModel::class.java)
        hashViewModel = ViewModelProviders.of(this).get(HashViewModel::class.java)

        surahViewModel.allSurah.observe(this, Observer { it ->

            //input file
            val file = File(it[0].surahPath!!)
            Log.e("SURAT", it[0].surahName.toString())

            val hashs = OperationHash.getHashFromFingerPrint(file, it[0].surahPath!!)
            val fingerprint = OperationHash.getFingerPrintAudio(file, it[0].surahPath!!)

            Log.e("COUNT ARRAY", hashs.size.toString())

            //target
            hashViewModel.getHashByValue(hashs, it[0].surahId!!)
                .observe(this, Observer { hash ->
                    val result = StringBuilder()
                    hash.forEach {
                        result.append(it.surahId.toString() + ",")
                    }
                    Log.e("COUNT RESULT", hash.size.toString())
                    Log.e("Result", result.toString())

                    val search = SearchMatch()
                    val match = search.findMatch(hash, fingerprint)
                    if (match != null) {
                        Log.e(
                            "CONFIDENCE",
                            ((hash.size.toDouble() / hashs.size.toDouble()) * 100).toString() + "%"
                        )
                        /* Log.e("RELATIVE_CONFIDENCE", search.relativeConfidence!!)
                        Log.e("OFFSET", search.offset!!)
                        Log.e("OFFSET_SECOND", search.offsetSecond!!)*/
                        val audio = it.find { audio -> audio.surahId == match.idSong }
                        Log.e("AUDIO", audio?.surahName.toString())
                    } else {
                        Log.e("NOT FOUND", "NOT FOUND")
                    }
                })

        })


        val email = intent.getStringExtra("email")
        if (email != null) edt_email.setText(email)


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
                Log.e("TOKEN", token.toString())
                val user = result.data!!.user
                setToken(token.toString())
                userViewModel.insert(
                    UserTable(
                        nama = user?.nama,
                        alamat = user?.alamat,
                        idUser = user?.id,
                        token = token,
                        tanggalLahir = user?.tanggalLahir,
                        email = user?.email,
                        pekerjaan = user?.pekerjaan,
                        gender = user?.gender,
                        updatedAt = user?.updatedAt
                    )
                )
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
