package id.dev.qurmer.utils.fingerprint.operation

import android.util.Log
import id.dev.qurmer.data.database.hash.HashTable
import id.dev.qurmer.utils.fingerprint.Fingerprint
import id.dev.qurmer.utils.fingerprint.hash.SearchHashing
import id.dev.qurmer.utils.fingerprint.model.AudioMatch


class SearchMatch {

    var confidence: String? = null
    var relativeConfidence: String? = null
    var offset: String? = null
    var offsetSecond: String? = null

    fun findMatch(dataHash: List<HashTable>, audioFingerPrint: Fingerprint?): AudioMatch? {
        return try {

            val index = SearchHashing()
            val match = audioFingerPrint?.let { index.search(it, dataHash) }
            if (match?.idSong != null) {
                confidence = match.match.count.toString()
                relativeConfidence =
                    (match.match.count.toDouble() / audioFingerPrint.linkList.size.toDouble() * 100).toString()
                offset = match.match.time.toString()
                offsetSecond = (match.match.time * 0.03225806451612903).toString()
                match
            } else {
                null
            }

        } catch (e: Throwable) {
            Log.e("error find", e.message.toString())
            null
        }
    }

}