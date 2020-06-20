package id.dev.qurmer.utils.fingerprint.operation

import id.dev.qurmer.data.database.hash.HashTable
import id.dev.qurmer.data.database.hash.HashViewModel
import id.dev.qurmer.utils.ReadFile
import id.dev.qurmer.utils.fingerprint.Fingerprint
import id.dev.qurmer.utils.fingerprint.hash.Hash
import java.io.File


object OperationHash {


    fun getHashFromFingerPrint(file: File, fileDir: String): IntArray {
        val linkList: ArrayList<Fingerprint.Link> = getFingerPrintAudio(file, fileDir)!!.linkList
        val linkHash = IntArray(linkList.size)
        linkHash.indices.forEach { i ->
            linkHash[i] = Hash.hash(linkList[i])
        }
        return linkHash
    }


    fun getFingerPrintAudio(file: File, fileDir: String): Fingerprint? {
        val rf = ReadFile()
        return rf.readFile(file, fileDir)
    }


    fun insert(hashViewModel: HashViewModel, fileDir: String, surahId: Int) {

        val readFile = ReadFile()
        val file = File(fileDir)
        val fp =  readFile.readFile(file, fileDir)
        val audioLength: Double = readFile.audioLength

        val list = fp?.linkList
        list?.forEach {
            val info = InfoHash(surahId, it)
            val hashTable = HashTable(surahId = info.id, hash = info.hash, time = info.time)
            hashViewModel.insertHash(hashTable)
        }

    }

}