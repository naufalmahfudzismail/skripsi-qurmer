package id.dev.qurmer.utils.fingerprint.spectrogram

class Window(windowType: Int, private val windowSize: Int) {


    private val window = FloatArray(windowSize)

    fun window(data: FloatArray, pos: Int): FloatArray {
        var size = windowSize
        if (pos + size > data.size) {
            size = data.size - pos
        }
        val win = FloatArray(windowSize)
        System.arraycopy(data, pos, win, 0, size)
        for (i in win.indices) {
            win[i] = win[i] * window[i]
        }
        return win
    }

    private fun initRectWindow(windowSize: Int) {
        for (i in 0 until windowSize) {
            window[i] = 1f
        }
    }

    private fun initHannWindow(windowSize: Int) {
        for (i in 0 until windowSize) {
            window[i] =
                (0.5 * (1 - Math.cos(2 * Math.PI * i / (windowSize - 1)))).toFloat()
        }
    }

    companion object {
        private const val RECT = 1
        const val HANN = 2
        fun window(
            data: FloatArray,
            pos: Int,
            windowType: Int,
            windowSize: Int
        ): FloatArray {
            var size = windowSize
            if (pos + size > data.size) {
                size = data.size - pos
            }
            val win = FloatArray(windowSize)
            System.arraycopy(data, pos, win, 0, size)
            when (windowType) {
                RECT -> rect(win)
                HANN -> hann(win)
            }
            return win
        }

        private fun rect(data: FloatArray): FloatArray {
            //w(t) = 1;
            val n = data.size
            val w = 1f
            for (i in 0 until n) {
                data[i] = data[i] * w
            }
            return data
        }

        private fun hann(data: FloatArray): FloatArray {
            //w(t) = 0.5*(1-cos(2*pi*k/(N-1)));
            val n = data.size
            var w: Float
            for (i in 0 until n) {
                w =
                    (0.5 * (1 - Math.cos(2 * Math.PI * i / (n - 1)))).toFloat()
                data[i] = data[i] * w
            }
            return data
        }
    }

    init {
        when (windowType) {
            RECT -> initRectWindow(windowSize)
            HANN -> initHannWindow(windowSize)
        }
    }
}