package id.dev.qurmer.utils

import android.annotation.SuppressLint
import android.media.AudioTrack
import android.media.MediaMetadataRetriever
import id.dev.qurmer.utils.fingerprint.Fingerprint
import java.io.File
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.time.Duration





class ReadFile {
    var audioLength = 0.0
    @Throws(Exception::class)
    fun readFile(file: File, path: String): Fingerprint? {
        val stream = file.inputStream()
        val len = getDuration(path).toInt()
        val dataL = FloatArray(len)
        val dataR = FloatArray(len)
        val buf: ByteBuffer = ByteBuffer.allocate(4 * len)
        val bytes = ByteArray(4 * len)
        try {
            stream.read(bytes)
            buf.put(bytes)
            buf.rewind()
            for (i in 0 until len) {
                buf.order(ByteOrder.LITTLE_ENDIAN)
                dataL[i] = buf.short / 32768f
                dataR[i] = buf.short / 32768f
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

        val data = FloatArray(len)
        data.forEachIndexed { index, _ ->
            data[index] = dataL[index] + dataR[index]
            data[index] = data[index] / 2
        }

        audioLength = (len / SAMPLE_RATE).toDouble()
        return Fingerprint(data, SAMPLE_RATE.toFloat())
    }

    @SuppressLint("InlinedApi")
    private fun getDuration(path: String): Long {
        val mediaMetadataRetriever = MediaMetadataRetriever()
        mediaMetadataRetriever.setDataSource(path)
        val durationStr =
            mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        return durationStr.toLong()
    }

    companion object {
        private const val SAMPLE_RATE = 8000
    }
}