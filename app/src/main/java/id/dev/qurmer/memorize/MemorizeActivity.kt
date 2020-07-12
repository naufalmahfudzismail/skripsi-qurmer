package id.dev.qurmer.memorize

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import id.dev.qurmer.R
import id.dev.qurmer.config.BaseActivity
import id.dev.qurmer.data.database.hash.HashTable
import id.dev.qurmer.data.database.hash.HashViewModel
import id.dev.qurmer.data.database.surah.SurahTable
import id.dev.qurmer.data.database.surah.SurahViewModel
import id.dev.qurmer.utils.fingerprint.operation.OperationHash
import id.dev.qurmer.utils.fingerprint.operation.SearchMatch
import kotlinx.android.synthetic.main.activity_memorize.*
import kotlinx.android.synthetic.main.dialog_fingerprint.view.*
import java.io.File


class MemorizeActivity : BaseActivity() {

    private var myAudioRecorder: MediaRecorder? = null
    private lateinit var hashViewModel: HashViewModel
    private lateinit var surahViewModel: SurahViewModel
    private lateinit var surah: SurahTable
    private var state: Boolean = false
    private var recordingStopped: Boolean = false


    private var outputFile: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memorize)

        surah = intent.getSerializableExtra("surah") as SurahTable
        val dateNow = System.currentTimeMillis()

        txt_surah.text = surah.surahName

        hashViewModel = ViewModelProviders.of(this).get(HashViewModel::class.java)
        surahViewModel = ViewModelProviders.of(this).get(SurahViewModel::class.java)

        outputFile =
            Environment.getExternalStorageDirectory().absolutePath + "/record${surah.surahAudioName}-${dateNow}.mp3"


        //compare()

        //doMatch()
        myAudioRecorder = MediaRecorder()
        myAudioRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        myAudioRecorder?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        myAudioRecorder?.setAudioSamplingRate(44100)
        myAudioRecorder?.setAudioEncodingBitRate(16 * 44100)
        myAudioRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
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
                doMatch()
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


    private fun compare() {

        val dir = Environment.getExternalStorageDirectory().absolutePath + "/record114-7.mp3"
        val dir2 = Environment.getExternalStorageDirectory().absolutePath + "/test-nas.mp3"

        val file = File(dir)
        val file2 = File(dir2)

        // val targetSurat = surah.surahId

        val hashs1 = OperationHash.getHashTable(dir, 1)
        val fingerprint1 = OperationHash.getFingerPrintAudio(file, dir)

        val hashs2 = OperationHash.getHashTable(dir2, 2)
        val fingerprint2 = OperationHash.getFingerPrintAudio(file2, dir2)
        val resultHash: MutableList<HashTable> = mutableListOf()


        Log.e("Hash1", hashs1.size.toString())
        Log.e("Hash2", hashs2.size.toString())


        hashs1.forEach { hash1 ->
            hashs2.forEach { hash2 ->
                if (hash2.hash == hash1.hash) {
                    resultHash.add(hash2)
                }
            }
        }

        Log.e("result", resultHash.size.toString())

        val search = SearchMatch()
        val match = search.findMatch(resultHash, fingerprint1)

        if (match != null) {
            Log.e(
                "CONFIDENCE",
                search.confidence
            )
            Log.e("RELATIVE_CONFIDENCE", search.relativeConfidence!!)
            Log.e("OFFSET", search.offset!!)
            Log.e("OFFSET_SECOND", search.offsetSecond!!)
            /* val audio = it.find { audio -> audio.surahId == match.idSong }
             Log.e("AUDIO_RESULT", audio?.surahName.toString())

             val succes = if (surah.surahId == audio?.surahId) {
                 "Sesuai"
             } else {
                 "Tidak Sesuai"
             }
 */
            showResultDialog(
                "An-nas",
                search.confidence.toString(),
                search.relativeConfidence.toString(),
                "TestCompare"
            )
        } else {
            Log.e("NOT FOUND", "NOT FOUND")
        }


    }


    private fun doMatch() {

        surahViewModel.allSurah.observe(this, Observer {
            //input file
            val dir = outputFile!!
            val file = File(dir)

            //Environment.getExternalStorageDirectory().absolutePath + "/test-nas.mp3"
            val hashs = OperationHash.getHashFromFingerPrint(file, dir)
            Log.e("JUMLAH HASH", hashs.size.toString())
            val fingerprint = OperationHash.getFingerPrintAudio(file, dir)

            /* val file2 = File(it[0].surahPath!!)
             val wave1 =
                 Wave(file.inputStream())
             val wave2 =
                 Wave(file2.inputStream())

             val simmiliar = wave1.getFingerprintSimilarity(wave2)

             val count = simmiliar.score
             val same = simmiliar.similarity
             Log.e(
                 "Result lib",
                 "${Environment.getExternalStorageDirectory().absolutePath + "/record${114}-${1}.mp3"} to" + " ${Environment.getExternalStorageDirectory().absolutePath + "/record${114}-${2}.mp3"} , ${count * 100}, ${same * 100}"
             )*/

            hashViewModel.getSearchHash(hashs)
                .observe(this, Observer { hash ->
                    val search = SearchMatch()
                    val match = search.findMatch(hash, fingerprint)
                    if (match != null) {
                        Log.e(
                            "CONFIDENCE",
                            search.confidence
                        )
                        Log.e("RELATIVE_CONFIDENCE", search.relativeConfidence!!)
                        Log.e("OFFSET", search.offset!!)
                        Log.e("OFFSET_SECOND", search.offsetSecond!!)
                        val audio = it.find { audio -> audio.surahId == match.idSong }
                        Log.e("AUDIO_RESULT", audio?.surahName.toString())

                        val succes = if (surah.surahId == audio?.surahId) {
                            "Sesuai"
                        } else {
                            "Tidak Sesuai"
                        }

                        showResultDialog(
                            audio?.surahName.toString(),
                            search.confidence.toString(),
                            search.relativeConfidence.toString(),
                            succes
                        )
                    } else {
                        Log.e("NOT FOUND", "NOT FOUND")
                    }


                })

        })
    }


    private fun showResultDialog(
        result: String,
        confidence: String,
        relativeConfidence: String,
        finalResult: String
    ) {

        val factory = LayoutInflater.from(this)
        val dialogView =
            factory.inflate(R.layout.dialog_fingerprint, null)
        val dialog = AlertDialog.Builder(this).create()

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        dialogView.txt_result.text = "Terdeteksi $result"
        dialogView.txt_confidence.text = "Confidence : $confidence"
        dialogView.txt_relative_confidence.text = "Relative Confidence : $relativeConfidence"
        dialogView.txt_final_result.text = "Hasil : $finalResult"

        dialogView.btn_close.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setCancelable(false)
        dialog.setView(dialogView)
        dialog.show()

    }

}
