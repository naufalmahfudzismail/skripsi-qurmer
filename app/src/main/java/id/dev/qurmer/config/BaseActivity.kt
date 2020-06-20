package id.dev.qurmer.config

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import id.dev.qurmer.R
import id.dev.qurmer.data.database.surah.SurahTable
import id.dev.qurmer.data.repository.APIRepository
import id.dev.qurmer.utils.fingerprint.operation.OperationHash
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.Serializable
import java.net.URL

abstract class BaseActivity : AppCompatActivity() {


    fun setDownloadDone() = SessionManager.getInstance(this).setDownload()
    fun setIntroDone() = SessionManager.getInstance(this).setIntro()
    fun setToken(token : String) = SessionManager.getInstance(this).saveToken(token)

    fun loggedIn(): Boolean = SessionManager.getInstance(this).isLoggedIn
    fun getDownloadStatus() = SessionManager.getInstance(this).getDownloadStatus()
    fun getIntroStatus() = SessionManager.getInstance(this).getIntro()
    fun getTokenWithBearer(): String = "Bearer ${SessionManager.getInstance(this).getToken()}"

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



    /*fun  downloadAudio(fileName : String){
        val urlFile = "${APIRepository.BASE_URL}download-audio/$fileName"
        DownloadFileFromURL().execute(urlFile)
    }

    @Suppress("DEPRECATION")
    @SuppressLint("StaticFieldLeak")
    inner class DownloadFileFromURL : AsyncTask<String, String, String>() {

        var pd: ProgressDialog? = null
        var pathFolder = ""
        var pathFile = ""
        var indexDownload: Int = 0
        var totalIndex: Int = audioSurahList.size


        override fun onPreExecute() {
            super.onPreExecute()
            pd = ProgressDialog(this@IntroActivity)
            pd!!.setMessage("Mengunduh $indexDownload / $totalIndex")
            pd!!.setTitle("Harap tunggu...")
            pd!!.max = 100
            pd!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
            pd!!.setCancelable(false)
            pd!!.show()
        }


        override fun onProgressUpdate(vararg progress: String) {
            // setting progress percentage
            pd!!.progress = Integer.parseInt(progress[0])
            pd!!.setMessage("Mengunduh $indexDownload / $totalIndex")
        }

        override fun onPostExecute(file_url: String) {
            if (pd != null) {
                pd!!.dismiss()
                surahViewModel.allSurah.observe(this@IntroActivity, Observer { it ->
                    it.forEach { surah ->
                        OperationHash.insert(
                            hashViewModel,
                            surah.surahPath.toString(),
                            surah.surahId!!
                        )
                    }

                })
                setDownloadDone()
            }

        }

        override fun doInBackground(vararg params: String?): String? {
            var count: Int = 0
            try {
                params[0]?.forEachIndexed { index, it ->
                    pathFolder =
                        applicationContext.filesDir.toString() + "/qurmer/audio"
                    pathFile = "$pathFolder/${114 - index}.mp3"

                    val futureStudioIconFile = File(pathFolder)
                    if (!futureStudioIconFile.exists()) {
                        futureStudioIconFile.mkdirs()
                    }

                    val url = URL(it)
                    val connection = url.openConnection()
                    connection.connect()

                    // this will be useful so that you can show a tipical 0-100%
                    // progress bar
                    val lengthOfFile = connection.contentLength

                    // download the file
                    val input = BufferedInputStream(url.openStream(), 8192)
                    val output = FileOutputStream(pathFile)

                    val audioData = audioSurahList[index]

                    surahViewModel.insert(
                        SurahTable(
                            surahId = audioData.surah?.id,
                            surahName = audioData.surah?.nama,
                            surahPath = pathFile,
                            surahAudioId = audioData.id,
                            surahBadgeId = audioData.surah?.badgeId,
                            surahAudioName = audioData.name
                        )
                    )


                    val data = ByteArray(1024) //anybody know what 1024 means ?
                    var total: Long = 0

                    while ({ count = input.read(data);count }() != -1) {
                        total += count.toLong()
                        // publishing the progress....
                        // After this onProgressUpdate will be called
                        publishProgress("" + (total * 100 / lengthOfFile).toInt())
                        // writing data to file
                        output.write(data, 0, count)
                    }

                    indexDownload++
                    // flushing output
                    output.flush()

                    // closing streams
                    output.close()
                    input.close()
                }

            } catch (e: Throwable) {
                Log.e("Error: ", e.message.toString())
            }

            return pathFile
        }

    }*/
}