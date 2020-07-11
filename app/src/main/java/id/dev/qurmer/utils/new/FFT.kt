package id.dev.qurmer.utils.new


object FFT {
    // compute the FFT of x[], assuming its length is a power of 2
    fun fft(x: Array<Complex?>): Array<Complex?> {
        val N = x.size

        // base case
        if (N == 1) return arrayOf(x[0])

        // radix 2 Cooley-Tukey FFT
        if (N % 2 != 0) {
            throw RuntimeException("N is not a power of 2")
        }

        // fft of even terms
        val even = arrayOfNulls<Complex>(N / 2)
        for (k in 0 until N / 2) {
            even[k] = x[2 * k]
        }
        val q = fft(even)

        // fft of odd terms
        for (k in 0 until N / 2) {
            even[k] = x[2 * k + 1]
        }
        val r = fft(even)

        // combine
        val y = arrayOfNulls<Complex>(N)
        for (k in 0 until N / 2) {
            val kth = -2 * k * Math.PI / N
            val wk = Complex(Math.cos(kth), Math.sin(kth))
            y[k] = q[k]!!.plus(wk.times(r[k]!!))
            y[k + N / 2] = q[k]!!.minus(wk.times(r[k]!!))
        }
        return y
    }

    // compute the inverse FFT of x[], assuming its length is a power of 2
    fun ifft(x: Array<Complex?>): Array<Complex?> {
        val N = x.size
        var y = arrayOfNulls<Complex>(N)

        // take conjugate
        for (i in 0 until N) {
            y[i] = x[i]!!.conjugate()
        }

        // compute forward FFT
        y = fft(y)

        // take conjugate again
        for (i in 0 until N) {
            y[i] = y[i]!!.conjugate()
        }

        // divide by N
        for (i in 0 until N) {
            y[i] = y[i]!!.times(1.0 / N)
        }
        return y
    }

    // compute the circular convolution of x and y
    fun cconvolve(x: Array<Complex?>, y: Array<Complex?>): Array<Complex?> {

        // should probably pad x and y with 0s so that they have same length
        // and are powers of 2
        if (x.size != y.size) {
            throw RuntimeException("Dimensions don't agree")
        }
        val N = x.size

        // compute FFT of each sequence
        val a = fft(x)
        val b = fft(y)

        // point-wise multiply
        val c = arrayOfNulls<Complex>(N)
        for (i in 0 until N) {
            c[i] = a[i]!!.times(b[i]!!)
        }

        // compute inverse FFT
        return ifft(c)
    }

    // compute the linear convolution of x and y
    fun convolve(x: Array<Complex?>, y: Array<Complex?>): Array<Complex?> {
        val ZERO = Complex(0.0, 0.0)
        val a = arrayOfNulls<Complex>(2 * x.size)
        for (i in x.indices) a[i] = x[i]
        for (i in x.size until 2 * x.size) a[i] = ZERO
        val b = arrayOfNulls<Complex>(2 * y.size)
        for (i in y.indices) b[i] = y[i]
        for (i in y.size until 2 * y.size) b[i] = ZERO
        return cconvolve(a, b)
    }

    // display an array of Complex numbers to standard output
    fun show(x: Array<Complex?>, title: String?) {
        println(title)
        println("-------------------")
        for (i in x.indices) {
            System.out.println(x[i])
        }
        println()
    }
    /*********************************************************************
     * Test client and sample execution
     *
     * % java FFT 4 x ------------------- -0.03480425839330703
     * 0.07910192950176387 0.7233322451735928 0.1659819820667019
     *
     * y = fft(x) ------------------- 0.9336118983487516 -0.7581365035668999 +
     * 0.08688005256493803i 0.44344407521182005 -0.7581365035668999 -
     * 0.08688005256493803i
     *
     * z = ifft(y) ------------------- -0.03480425839330703 0.07910192950176387
     * + 2.6599344570851287E-18i 0.7233322451735928 0.1659819820667019 -
     * 2.6599344570851287E-18i
     *
     * c = cconvolve(x, x) ------------------- 0.5506798633981853
     * 0.23461407150576394 - 4.033186818023279E-18i -0.016542951108772352
     * 0.10288019294318276 + 4.033186818023279E-18i
     *
     * d = convolve(x, x) ------------------- 0.001211336402308083 -
     * 3.122502256758253E-17i -0.005506167987577068 - 5.058885073636224E-17i
     * -0.044092969479563274 + 2.1934338938072244E-18i 0.10288019294318276 -
     * 3.6147323062478115E-17i 0.5494685269958772 + 3.122502256758253E-17i
     * 0.240120239493341 + 4.655566391833896E-17i 0.02755001837079092 -
     * 2.1934338938072244E-18i 4.01805098805014E-17i
     *
     */
}