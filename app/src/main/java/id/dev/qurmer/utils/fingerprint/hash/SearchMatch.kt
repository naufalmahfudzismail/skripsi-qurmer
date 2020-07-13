package id.dev.qurmer.utils.fingerprint.hash

import id.dev.qurmer.data.database.hash.HashTable
import id.dev.qurmer.utils.fingerprint.Fingerprint
import id.dev.qurmer.utils.fingerprint.hash.Hash.hash
import id.dev.qurmer.utils.fingerprint.model.AudioMatch
import id.dev.qurmer.utils.fingerprint.model.Match

class SearchHashing {

    private val hashMap: HashMap<Long, Match?> = HashMap(400000)
    private var maxId: Long = -1
    private var maxCount = -1
    private var maxTime = -1

    fun search(fp: Fingerprint, hashs: List<HashTable>): AudioMatch {
        val linkList: ArrayList<Fingerprint.Link> = fp.linkList

        val linkHash = IntArray(linkList.size)
        val linkTime = IntArray(linkList.size)

        for (i in linkHash.indices) {
            linkHash[i] = hash(linkList[i])
            linkTime[i] = linkList[i].start.intTime
        }

        return search(linkTime = linkTime, linkHash = linkHash, results = hashs)
    }

    private fun search(
        linkTime: IntArray,
        linkHash: IntArray,
        results: List<HashTable>
    ): AudioMatch {
        val linkHashMap: HashMap<Int, Int> = HashMap(linkHash.size)
        for (i in linkHash.indices) {
            linkHashMap[linkHash[i]] = linkTime[i]
        }
        try {
            results.forEach { hash ->
                var count: Match?
                val idHash =
                    idHash(
                        hash.surahId!!
                    )

                //inisialisasi hashMap pada key ke biner
                count = hashMap[idHash]
                //jika masih kosong maka diisi dengan inisialisasi count = 0
                if (count == null) count = Match(0, linkHashMap[hash.hash]!! - hash.time!!)
                //count tambah
                count.updateCount()
                //mengumpdet jumlah count pada hash atau id surat
                hashMap[idHash] = count
            }

            hashMap.forEach { (hash, countTime) ->
                if (countTime!!.count > maxCount) {
                    // max id id user yang di hashing
                    maxId = hash
                    // jumlah confidence
                    maxCount = countTime.count
                    // waktu perbedaan
                    maxTime = countTime.time
                }
            }
            val offset = -maxTime
            return AudioMatch(
                hash2id(
                    maxId
                ), Match(maxCount, offset)
            )

        } catch (e: Throwable) {
            e.printStackTrace()
            return AudioMatch(-1, Match(-1, -1))
        }
    }

    companion object {
        fun idHash(id: Int): Long {

            //bitsiw left shift <<
            return ((id shl 16)).toLong()
        }

        fun hash2id(idHash: Long): Int {

            //bitwise right shift >>
            return (idHash shr 16).toInt()
        }
    }

}