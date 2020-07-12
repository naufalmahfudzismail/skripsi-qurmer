package id.dev.qurmer.intro

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.AsyncTask
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import id.dev.qurmer.MainActivity
import id.dev.qurmer.R
import id.dev.qurmer.config.BaseActivity
import id.dev.qurmer.data.database.ayat.AyatTable
import id.dev.qurmer.data.database.ayat.AyatViewModel
import id.dev.qurmer.data.database.hash.HashViewModel
import id.dev.qurmer.data.database.surah.SurahTable
import id.dev.qurmer.data.database.surah.SurahViewModel
import id.dev.qurmer.data.model.IntroDataResponse
import id.dev.qurmer.data.repository.APIRepository
import id.dev.qurmer.intro.login.LoginActivity
import id.dev.qurmer.intro.register.RegisterActivity
import id.dev.qurmer.utils.fingerprint.operation.OperationHash
import id.dev.qurmer.utils.fingerprint.operation.SearchMatch
import kotlinx.android.synthetic.main.activity_intro.*
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class IntroActivity : BaseActivity(), IntroView {


    private lateinit var surahViewModel: SurahViewModel
    private lateinit var ayatViewModel: AyatViewModel
    private lateinit var hashViewModel: HashViewModel
    private lateinit var introPresenter: IntroPresenter

    private val audioSurahList: MutableList<IntroDataResponse.Data.Audio> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_intro)

        surahViewModel = ViewModelProviders.of(this).get(SurahViewModel::class.java)
        ayatViewModel = ViewModelProviders.of(this).get(AyatViewModel::class.java)
        hashViewModel = ViewModelProviders.of(this@IntroActivity).get(HashViewModel::class.java)

        introPresenter = IntroPresenter(this, this)
        if (!getDownloadStatus()) introPresenter.getAudioSurah()


        btn_login.setOnClickListener {
            if (getDownloadStatus()) startActivityWithIntent<LoginActivity>()
            else makeLongToast("Harap Tunggu")
        }

        btn_register.setOnClickListener {
            if (getDownloadStatus()) startActivityWithIntent<RegisterActivity>()
            else makeLongToast("Harap Tunggu")
        }

    }


    override fun onResult(result: IntroDataResponse?) {
        val data = result?.data?.audio
        val urlAudioList: MutableList<String> = mutableListOf()

        val ayat = result?.data?.ayat
        ayat?.forEach {
            ayatViewModel.insertAyat(
                AyatTable(
                    ayat = it.ayat,
                    surahServerId = it.surahId?.toInt(),
                    orderNumber = it.urutanAyat?.toInt(),
                    ayatServerId = it.id
                )
            )
        }

        data?.let { audioSurah ->
            audioSurahList.addAll(audioSurah)
            data.map { it.file }.forEach { fileName ->
                val urlFile = "${APIRepository.BASE_URL}download-audio/$fileName"
                urlAudioList.add(urlFile)
            }
        }

        DownloadFileFromURL().execute(urlAudioList)
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

    @Suppress("DEPRECATION")
    @SuppressLint("StaticFieldLeak")
    inner class DownloadFileFromURL : AsyncTask<List<String>, String, String>() {

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
                /*try {
                    repeat(13) {
                        val dir =
                            Environment.getExternalStorageDirectory().absolutePath + "/114/114-${it + 1}.mp3"
                        OperationHash.insert(hashViewModel, dir, 1)
                        Log.e("HAshing", "an-nas")
                    }
                    repeat(13) {
                        val dir =
                            Environment.getExternalStorageDirectory().absolutePath + "/113/113-${it + 1}.mp3"
                        OperationHash.insert(hashViewModel, dir, 2)
                        Log.e("HAshing", "al-falaq")
                    }
                    repeat(13) {
                        val dir =
                            Environment.getExternalStorageDirectory().absolutePath + "/112/112-${it + 1}.mp3"
                        OperationHash.insert(hashViewModel, dir, 3)
                        Log.e("HAshing", "al-ikhlas")
                    }
                }catch (e : Throwable){
                    Log.e("ERROR", e.message.toString())
                }*/
                setDownloadDone()
            }

        }

        override fun doInBackground(vararg params: List<String>?): String {
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

    }

    override fun onStart() {
        super.onStart()
        if(loggedIn()) startActivityClearPreviousActivity<MainActivity>()
    }


}
