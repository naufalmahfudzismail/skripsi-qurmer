package id.dev.qurmer.config

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import id.dev.qurmer.R
import java.io.Serializable

abstract class BaseActivity : AppCompatActivity() {

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


    fun replyIntentResult(intent: Intent, resultCode: Int) {
        setResult(resultCode, intent)
        finish()
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