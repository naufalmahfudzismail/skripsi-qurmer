package id.dev.qurmer.utils

object StringReplace {

    private fun stringReplace(str: String): String {
        var tmp: String = str.replace("\\", "\\\\")
        tmp = tmp.replace("\"", "\\\"")
        tmp = tmp.replace("\'", "\\\'")
        return tmp
    }

    fun stringReplace(seq: CharSequence?): String {
        return if (seq == null) "" else stringReplace(seq.toString())
    }
}