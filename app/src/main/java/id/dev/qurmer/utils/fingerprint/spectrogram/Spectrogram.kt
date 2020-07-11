package id.dev.qurmer.utils.fingerprint.spectrogram

import org.jtransforms.fft.FloatFFT_1D

class Spectrogram(
    data: FloatArray,
    windowsType: Int,
    windowSize: Int,
    overlap: Int,
    fs: Float
) {
    val stft: ArrayList<FloatArray>
    val freq: FloatArray
    val time: FloatArray
    val fft: FloatFFT_1D
    val fftData: FloatArray

    private fun calcFFT(win: FloatArray) {
        for (i in win.indices) {
            fftData[i * 2] = win[i]
            fftData[i * 2 + 1] = 0f
        }
        fft.complexForward(fftData)
    }

    private fun addSTFT() {
        val half = FloatArray(fftData.size / 2)
        System.arraycopy(fftData, 0, half, 0, fftData.size / 2)
        stft.add(half)
    }

    private fun calcFreq(fs: Float) {
        for (i in freq.indices) {
            freq[i] = fs * i / freq.size
        }
    }

    private fun calcTime(fs: Float, stepSize: Int) {
        for (i in time.indices) {
            time[i] = stepSize * i / fs
        }
    }
    init {
        val dataLen = data.size
        val stepSize = windowSize - overlap
        stft = ArrayList()
        freq = FloatArray(windowSize / 2)
        time = FloatArray(dataLen / stepSize)
        fft = FloatFFT_1D(windowSize.toLong())
        fftData = FloatArray(windowSize * 2)
        val window = Window(windowsType, windowSize)
        var i = 0
        while (i < dataLen) {
            if (i + windowSize > data.size) {
                break
            }
            val win: FloatArray? = try {
                window.window(data, i)
            } catch (e: Exception) {
                null
            }
            //calc fft
            calcFFT(win!!)
            //add
            addSTFT()
            i += stepSize
        }
        calcFreq(fs)
        calcTime(fs, stepSize)
    }
}