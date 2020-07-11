package id.dev.qurmer.utils.fingerprint

import kotlin.math.log10

class FindPeaks(private val nPeaks: Int) {

    val power: FloatArray = FloatArray(nPeaks)
    val locate: IntArray = IntArray(nPeaks)

    fun findComplexPeaks(data: FloatArray, neighborRange: Int) {
        val len = data.size / 2
        for (i in 0 until nPeaks) {
            power[i] = (-500).toFloat()
            locate[i] = -1
        }
        val dataPower = FloatArray(len)
        (0 until len).forEach { i ->
            dataPower[i] = (10 * log10(
                (data[2 * i] * data[2 * i]
                        + data[2 * i + 1] * data[2 * i + 1]).toDouble()
            )).toFloat()
        }
        (0 until len).forEach { k ->
            val pi = dataPower[k]
            var add = true
            for (j in 0 until neighborRange) {
                val pl: Float = if (k - neighborRange >= 0) {
                    dataPower[k - neighborRange]
                } else pi - 1
                val pr: Float = if (k + neighborRange < len) {
                    dataPower[k + neighborRange]
                } else pi - 1
                if (pi < pl && pi < pr) {
                    add = false
                }
            }
            if (add) add(pi, k)
        }
    }

    private fun add(p: Float, loc: Int) {
        for (i in power.indices) {
            if (power[i] < p) {
                for (j in power.size - 1 downTo i + 1) {
                    power[j] = power[j - 1]
                    locate[j] = locate[j - 1]
                }
                power[i] = p
                locate[i] = loc
                break
            }
        }
    }
}