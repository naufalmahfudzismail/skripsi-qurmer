package id.dev.qurmer.utils.fingerprint.hash

import id.dev.qurmer.utils.fingerprint.Fingerprint

object Hash {
    fun hash(link: Fingerprint.Link): Int {
        val dt = link.end.intTime - link.start.intTime //
        val df = link.end.intFreq - link.start.intFreq + 300 // 300
        val freq = link.start.intFreq // 5000
        return freq + 5000 * (df + 600 * dt)
    }
    fun hash2link(hash: Int): IntArray {
        val freq = hash % 5000
        val df = hash / 5000 % 600
        val dt = hash / 5000 / 600
        return intArrayOf(freq, df, dt)
    }
}