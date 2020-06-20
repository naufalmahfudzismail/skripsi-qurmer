package id.dev.qurmer.memorize

import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import com.musicg.wave.Wave
import id.dev.qurmer.R
import id.dev.qurmer.config.BaseActivity
import id.dev.qurmer.data.database.hash.HashViewModel
import id.dev.qurmer.data.database.surah.SurahTable
import id.dev.qurmer.data.database.surah.SurahViewModel
import id.dev.qurmer.utils.fingerprint.operation.OperationHash
import kotlinx.android.synthetic.main.activity_memorize.*
import java.io.File


class MemorizeActivity : BaseActivity() {

    private var myAudioRecorder: MediaRecorder? = null
    private lateinit var hashViewModel: HashViewModel
    private lateinit var surahViewModel: SurahViewModel
    private var state: Boolean = false
    private var recordingStopped: Boolean = false


    private var outputFile: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memorize)

        val surah = intent.getSerializableExtra("surah") as SurahTable
        val dateNow = System.currentTimeMillis()

        txt_surah.text = surah.surahName

        hashViewModel = ViewModelProviders.of(this).get(HashViewModel::class.java)
        surahViewModel = ViewModelProviders.of(this).get(SurahViewModel::class.java)

        outputFile =
            Environment.getExternalStorageDirectory().absolutePath + "/record${surah.surahAudioName}-${1}.wav";

       // doMatch()


        myAudioRecorder = MediaRecorder()
        myAudioRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        myAudioRecorder?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        myAudioRecorder?.setAudioSamplingRate(44100)
        myAudioRecorder?.setAudioEncodingBitRate(1024 * 1024)
        myAudioRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        myAudioRecorder?.setOutputFile(outputFile)

        btn_play.isEnabled = false
        btn_stop.isEnabled = false


        btn_record.setOnClickListener {
            try {
                myAudioRecorder?.prepare()
                myAudioRecorder?.start()
                state = true

            } catch (e: Throwable) {
                Log.e("ERROR", e.message.toString())
            }
            btn_record.isEnabled = false
            btn_stop.isEnabled = true

            makeToast("START RECORDING")

        }

        btn_stop.setOnClickListener {
            if (state) {
                myAudioRecorder?.stop()
                myAudioRecorder?.release()
                state = false

                btn_record.isEnabled = true
                btn_stop.isEnabled = false
                makeToast("STOP RECORDING")
                btn_play.isEnabled = true
                //doMatch(surah)
            }
        }

        btn_play.setOnClickListener {
            val mediaPlayer = MediaPlayer()
            try {
                mediaPlayer.setDataSource(outputFile)
                mediaPlayer.prepare()
                mediaPlayer.start()
                makeToast("PLAY RECORD")
            } catch (e: Throwable) {

            }
        }

    }


    private fun doMatch() {

        val dir = Environment.getExternalStorageDirectory().absolutePath + "/tes.wav"

        val file1 =
            File(Environment.getExternalStorageDirectory().absolutePath + "/record${114}-${1}.wav")
        val file2 =
            File(Environment.getExternalStorageDirectory().absolutePath + "/record${114}-${2}.wav")

        val wave1 =
            Wave(file1.inputStream())
        val wave2 =
            Wave(file2.inputStream())

        val simmiliar = wave1.getFingerprintSimilarity(wave2)

        val count = simmiliar.score
        val same = simmiliar.similarity
        Log.e(
            "Result lib",
            "${Environment.getExternalStorageDirectory().absolutePath + "/record${114}-${2}.mp3"} to" + " ${Environment.getExternalStorageDirectory().absolutePath + "/record${114}-${2}.mp3"} , ${count * 100}, ${same * 100}"
        )


        val hashs1 = OperationHash.getHashFromFingerPrint(file1, Environment.getExternalStorageDirectory().absolutePath + "/record${114}-${1}.wav")
        val fingerprint1 = OperationHash.getFingerPrintAudio(file1, file1.absolutePath)
        Log.e("COUNT ARRAY", hashs1.size.toString())

        val hashs2 = OperationHash.getHashFromFingerPrint(file2, Environment.getExternalStorageDirectory().absolutePath + "/record${114}-${2}.wav")
        val fingerprint2 = OperationHash.getFingerPrintAudio(file2, file2.absolutePath)
        Log.e("COUNT ARRAY", hashs2.size.toString())

        var sum = 0

        hashs1.forEach {
            hashs2.forEach { it2 ->
                if (it == it2) {
                    sum++
                }
            }
        }
        Log.e("RESULT COUNT ARRAY", (sum / hashs1.size).toString())

        //target

        /*   surahViewModel.allSurah.observe(this, Observer { surahData ->

               val target = surahData[0].surahPath

               val file = File(surahData[1].surahPath!!)

               val hashs = OperationHash.getHashFromFingerPrint(file, dir)
               val fingerprint = OperationHash.getFingerPrintAudio(file, dir)
               Log.e("COUNT ARRAY", hashs.size.toString())

               hashViewModel.getHashByValue(hashs, surahData[0].surahId!!)
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

                           Log.e("RELATIVE_CONFIDENCE", search.relativeConfidence!!)
                           Log.e("OFFSET", search.offset!!)
                           Log.e("OFFSET_SECOND", search.offsetSecond!!)
                           val audio = surahData.find { audio -> audio.surahId == match.idSong }
                           Log.e("AUDIO", audio?.surahName.toString())


                       } else {
                           Log.e("NOT FOUND", "NOT FOUND")
                       }
                   })
           })*/
    }


}
