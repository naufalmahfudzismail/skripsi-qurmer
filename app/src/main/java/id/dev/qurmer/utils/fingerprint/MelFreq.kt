package id.dev.qurmer.utils.fingerprint

object MelFreq {
    //Mel = 2595*log10(1+freq/700)
    fun Mel2Freq(melFreq: Float): Float {
        return (Math.pow(10.0, melFreq / 2595 - 1.toDouble()) * 700).toFloat()
    }

    fun Freq2Mel(freq: Float): Float {
        return (2595 * Math.log10(1 + freq / 700.toDouble())).toFloat()
    }

    fun MelBand(freq: FloatArray): FloatArray {
        for (i in freq.indices) {
            freq[i] = Freq2Mel(freq[i])
        }
        return freq
    }
}