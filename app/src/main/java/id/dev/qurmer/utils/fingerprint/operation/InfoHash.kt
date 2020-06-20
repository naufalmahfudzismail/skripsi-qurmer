package id.dev.qurmer.utils.fingerprint.operation

import id.dev.qurmer.utils.fingerprint.Fingerprint
import id.dev.qurmer.utils.fingerprint.hash.Hash

open class InfoHash(val id: Int, link: Fingerprint.Link) {

    val hash: Int = Hash.hash(link)
    val time: Int = link.start.intTime

    override fun hashCode(): Int {
        return hash
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as InfoHash

        if (id != other.id) return false
        if (hash != other.hash) return false
        if (time != other.time) return false

        return true
    }

}
