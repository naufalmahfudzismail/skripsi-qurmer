package id.dev.qurmer.utils

import android.media.AudioFormat
import android.media.MediaMetadataRetriever
import id.dev.qurmer.utils.fingerprint.Fingerprint
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder


class ReadFile {
    var fingerprint: Fingerprint? = null
    var Title: String? = null
    var Album: String? = null
    var Artist: String? = null
    var audio_length = 0.0

    private fun getTabs(filename: String) {
        val strings = filename.split("}}").toTypedArray()
        if (strings.size < 3) {
            Title = filename.replace(".wav", "")
            Album = "Advertisement"
            Artist = "Advertisement"
            return
        }
        Title = strings[0]
        Album = strings[1]
        //remove .wav
        val len = strings[2].length
        Artist = strings[2].substring(0, len - 4)
    }

    @Throws(Exception::class)
    fun readFile(file: File) {
        /*val stream: InputStream
        stream = try {
            AudioSystem.getAudioInputStream(file)
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
        val format: AudioFormat = AudioFormat.ENCODING_MP3
        if (format.encoding != AudioFormat.ENCODING_PCM_16BIT) {
            throw Exception("Encoding must be PCM_SIGNED!")
        }
        if (format.sampleRate != SAMPLE_RATE) {
            throw Exception("SampleRate must be $SAMPLE_RATE!")
        }*/

        val stream = file.inputStream()
        val len = getDuration(file).toInt()
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
            return
        }

        val data = FloatArray(len)
        data.forEachIndexed { index, _ ->
            data[index] = dataL[index] + dataR[index]
            data[index] = data[index] / 2
        }

        audio_length = (len / SAMPLE_RATE).toDouble()
        fingerprint = Fingerprint(data, SAMPLE_RATE.toFloat())
        getTabs(file.name)
    }

    private fun getDuration(file: File): Long {
        val mediaMetadataRetriever = MediaMetadataRetriever()
        mediaMetadataRetriever.setDataSource(file.absolutePath)
        val durationStr =
            mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        return durationStr.toLong()
    }

    companion object {
        private const val SAMPLE_RATE = 8000
    }
}