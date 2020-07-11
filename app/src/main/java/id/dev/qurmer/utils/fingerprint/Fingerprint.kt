package id.dev.qurmer.utils.fingerprint

import android.annotation.SuppressLint
import id.dev.qurmer.utils.fingerprint.spectrogram.Spectrogram
import id.dev.qurmer.utils.fingerprint.spectrogram.Window

@SuppressLint("NewApi")
class Fingerprint(data: FloatArray?, fs: Float) {

        private val nPeaks = 3
        private val fftSize = 512
        private val overlap = 256
        private val c = 32
        private val peakRange = 5

        private val peakList: ArrayList<Peak> = ArrayList()
        val linkList: ArrayList<Link> = ArrayList()

        private val freq: FloatArray
        private val time: FloatArray

        private val rangeTime = floatArrayOf(1f, 3f)
        private val rangeFreq = floatArrayOf(-600f, 600f)
        private val band = arrayOf(11, 22, 35, 50, 69, 91, 117, 149, 187, 231)

        private val minFreq = 100f
        private val maxFreq = 2000f
        private val minPower = 0f

        fun inband(intFreq: Int): Int {
            val size = band.size
            if (intFreq < band[0] || intFreq > band[size - 1]) {
                return -1
            }
            for (i in 0 until size - 1) {
                if (band[i + 1] > intFreq) return i
            }
            return -1
        }

        fun link(band: Boolean) {
            val n: Int = peakList.size
            for (i in 0 until n) {
                val p1 = peakList[i]

                //time start|end
                var tStart: Int
                var tEnd: Int
                var k: Int = i + 1

                while (k < n) {
                    val t = time[p1.intTime]
                    val t2 = time[peakList[k].intTime]
                    if (t2 - t >= rangeTime[0]) break
                    k++
                }
                tStart = k
                while (k < n) {
                    val t = time[p1.intTime]
                    val t2 = time[peakList[k].intTime]
                    if (t2 - t >= rangeTime[1]) break
                    k++
                }
                tEnd = k
                //freq start|end
                var fstart: Float
                var fend: Float
                fstart = freq[p1.intFreq] + rangeFreq[0]
                fend = freq[p1.intFreq] + rangeFreq[1]
                for (i2 in tStart until tEnd) {
                    val p2 = peakList[i2] ?: continue
                    if (band) {
                        val b1 = inband(p1.intFreq)
                        val b2 = inband(p2.intFreq)
                        if (b1 == b2 && b1 != -1) {
                            val l = Link(p1, p2)
                            linkList.add(l)
                        }
                    } else {
                        if (freq[p2.intFreq] in fstart..fend) {
                            val l = Link(p1, p2)
                            linkList.add(l)
                        }
                    }
                }
            }
        }

        class Peak {
            var intFreq = 0
            var power = 0f
            var intTime = 0
        }

        class Link(val start: Peak, val end: Peak) {
            private val tmp = FloatArray(3)

            init {
                tmp[0] = start.intFreq.toFloat()
                tmp[1] = end.intFreq.toFloat()
                tmp[2] = (end.intTime - start.intTime).toFloat()
            }
        }


        init {
            val spectrogram = Spectrogram(data!!, Window.HANN, fftSize, overlap, fs)
            val stft = spectrogram.stft

            //FTT
            freq = spectrogram.freq
            time = spectrogram.time

            val tmp: ArrayList<Peak> = ArrayList(c * nPeaks)
            val size: Int = stft.size
            val bandNum = band.size - 1


            //Mencari Puncak
            for (b in 0 until bandNum) {
                for (i in 0 until size) {
                    if (i != 0) {
                        if (i % c == 0 || i == size - 1) {
                            //Filter
                            tmp.removeIf { peak ->
                                val peakFreq = freq[peak.intFreq]
                                peakFreq < minFreq || peakFreq > maxFreq
                            }
                            tmp.removeIf { peak -> peak.power <= minPower }
                            tmp.sortWith(Comparator { o1, o2 ->
                                o2.power.toDouble().compareTo(o1.power.toDouble())
                            })
                            val end = if (tmp.size < nPeaks) tmp.size else nPeaks
                            peakList.addAll(tmp.subList(0, end))
                            tmp.clear()
                        }
                    }
                    val fft = stft[i]
                    var len = band[b + 1] - band[b]
                    val start = band[b] * 2
                    len *= 2
                    val fftBand = FloatArray(len)
                    System.arraycopy(fft, start, fftBand, 0, len)
                    val find = FindPeaks(nPeaks)
                    find.findComplexPeaks(fftBand, peakRange)
                    val power: FloatArray = find.power
                    val loc: IntArray = find.locate
                    for (j in power.indices) {
                        loc[j] += band[b]
                    }
                    for (j in 0 until nPeaks) {
                        if (loc[j] == -1) {
                            continue
                        }
                        val p = Peak()
                        p.intFreq = loc[j]
                        p.intTime = i
                        p.power = power[j]
                        tmp.add(p)
                    }
                }
            }
            peakList.sortWith(Comparator { o1, o2 -> o1.intTime - o2.intTime })
            link(true)
        }
}
