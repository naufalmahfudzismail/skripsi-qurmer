package id.dev.qurmer.utils.fingerprint.model

class Match(var count: Int, val time: Int) {

    fun updateCount() {
        count++
    }

}