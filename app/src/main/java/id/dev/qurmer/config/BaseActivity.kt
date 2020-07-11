package id.dev.qurmer.config

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import id.dev.qurmer.R
import kotlinx.android.synthetic.main.dialog_play_status.*
import kotlinx.android.synthetic.main.dialog_play_status.view.*
import java.io.Serializable

abstract class BaseActivity : AppCompatActivity() {


    fun setDownloadDone() = SessionManager.getInstance(this).setDownload()
    fun setIntroDone() = SessionManager.getInstance(this).setIntro()
    fun setToken(token: String) = SessionManager.getInstance(this).saveToken(token)

    fun loggedIn(): Boolean = SessionManager.getInstance(this).isLoggedIn
    fun getDownloadStatus() = SessionManager.getInstance(this).getDownloadStatus()
    fun getIntroStatus() = SessionManager.getInstance(this).getIntro()
    fun getTokenWithBearer(): String = "Bearer ${SessionManager.getInstance(this).getToken()}"
    fun logOut() = SessionManager.getInstance(this).logOut()



    companion object {
        const val NOTIFICATION_CHANNEL_ID = "10001"
        const val NOTIFICATION_REQUEST_CODE = 2002
    }

    fun makeToast(text: String) {
        runOnUiThread {
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        }
    }

    fun makeLongToast(text: String) {

        runOnUiThread {
            Toast.makeText(this, text, Toast.LENGTH_LONG).show()
        }
    }


    fun showDialogChallenge(title : String, content : String, listener : DialogChallengeListener)=  try {

        val factory = LayoutInflater.from(this)
        val dialogViewOne =
            factory.inflate(R.layout.dialog_play_status, null)
        val dialogOne = AlertDialog.Builder(this).create()

        dialogOne.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogOne.requestWindowFeature(Window.FEATURE_NO_TITLE)

        dialogViewOne.txt_title_dialog.text = title
        dialogViewOne.txt_content_dialog.text = content

        dialogViewOne.btn_yes.setOnClickListener {
            listener.onPositiveClicked(dialogOne)
        }


        dialogViewOne.btn_no.setOnClickListener {
            listener.onNegativeClicked(dialogOne)
        }


        dialogOne.setCancelable(false)
        dialogOne.setView(dialogViewOne)
        dialogOne.show()
    }catch (e : Throwable){
        Log.e("ERROR DIALOG", e.message.toString())
    }


    fun replyIntentResult(intent: Intent, resultCode: Int) {
        setResult(resultCode, intent)
        finish()
    }

    private lateinit var dialogLoading: Dialog

    fun viewLoading() {
        dialogLoading = Dialog(this)
        dialogLoading.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogLoading.setCancelable(false)
        dialogLoading.setContentView(R.layout.default_loading)
        dialogLoading.show()

    }

    fun hideLoading() {
        dialogLoading.dismiss()
    }


    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    inline fun <reified T : Any> startActivityClearPreviousActivity(vararg params: Pair<String, Any?>) {
        startActivity(
            createIntent(
                this,
                T::class.java,
                params
            ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        )
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }


    inline fun <reified T : Any> intentFor(vararg params: Pair<String, Any?>): Intent =
        createIntent(this, T::class.java, params)

    inline fun <reified T : Any> startActivityWithIntent(vararg params: Pair<String, Any?>) {
        startActivity(createIntent(this, T::class.java, params))
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

    }

    fun <T> createIntent(
        ctx: Context,
        clazz: Class<out T>,
        params: Array<out Pair<String, Any?>>
    ): Intent {
        val intent = Intent(ctx, clazz)
        if (params.isNotEmpty()) fillIntentArguments(intent, params)
        return intent
    }

    private fun fillIntentArguments(intent: Intent, params: Array<out Pair<String, Any?>>) {
        params.forEach {
            when (val value = it.second) {
                null -> intent.putExtra(it.first, null as Serializable?)
                is Int -> intent.putExtra(it.first, value)
                is Long -> intent.putExtra(it.first, value)
                is CharSequence -> intent.putExtra(it.first, value)
                is String -> intent.putExtra(it.first, value)
                is Float -> intent.putExtra(it.first, value)
                is Double -> intent.putExtra(it.first, value)
                is Char -> intent.putExtra(it.first, value)
                is Short -> intent.putExtra(it.first, value)
                is Boolean -> intent.putExtra(it.first, value)
                is Serializable -> intent.putExtra(it.first, value)
                is Bundle -> intent.putExtra(it.first, value)
                is Parcelable -> intent.putExtra(it.first, value)
                is Array<*> -> when {
                    value.isArrayOf<CharSequence>() -> intent.putExtra(it.first, value)
                    value.isArrayOf<String>() -> intent.putExtra(it.first, value)
                    value.isArrayOf<Parcelable>() -> intent.putExtra(it.first, value)
                }
                is IntArray -> intent.putExtra(it.first, value)
                is LongArray -> intent.putExtra(it.first, value)
                is FloatArray -> intent.putExtra(it.first, value)
                is DoubleArray -> intent.putExtra(it.first, value)
                is CharArray -> intent.putExtra(it.first, value)
                is ShortArray -> intent.putExtra(it.first, value)
                is BooleanArray -> intent.putExtra(it.first, value)
            }
            return@forEach
        }
    }
}