package id.dev.qurmer.utils.fingerprint

class FindPeaks(private val nPeaks: Int) {
    val power: FloatArray
    val locate: IntArray
    fun findComplexPeaks(data: FloatArray, neighborRange: Int) {
        val len = data.size / 2
        for (i in 0 until nPeaks) {
            power[i] = (-500).toFloat()
            locate[i] = -1
        }
        val data_power = FloatArray(len)
        for (i in 0 until len) {
            data_power[i] = (10 * Math.log10(
                (data[2 * i] * data[2 * i]
                        + data[2 * i + 1] * data[2 * i + 1]).toDouble()
            )).toFloat()
        }
        for (k in 0 until len) {
            val pi = data_power[k]
            var add = true
            for (j in 0 until neighborRange) {
                var pl: Float
                var pr: Float
                pl = if (k - neighborRange >= 0) {
                    data_power[k - neighborRange]
                } else pi - 1
                pr = if (k + neighborRange < len) {
                    data_power[k + neighborRange]
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
    } /*
    private int inBand(float freq, float[] freqRange){
        int size = freqRange.length;
        if(freq < freqRange[0] || freq > freqRange[size - 1]) {
            return -1;
        }
        for(int i = 0; i < size - 1; i ++){
            if(freqRange[i + 1] > freq)
                return i;
        }
        return -1;
    }
*/

    init {
        power = FloatArray(nPeaks)
        locate = IntArray(nPeaks)
    }
}