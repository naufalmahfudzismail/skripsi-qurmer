package id.dev.qurmer.data

import id.dev.qurmer.data.model.Month
import java.util.*

object GlobalData{

    val FRAGMENT_STACK: MutableList<Int> = mutableListOf()


    fun days(): List<Int> {
        val mutableDay: MutableList<Int> = mutableListOf()
        repeat(31) {
            mutableDay.add(it + 1)
        }
        return mutableDay
    }

    val months = listOf(
        Month(1, "Januari"),
        Month(2, "Februari"),
        Month(3, "Maret")
        ,
        Month(4, "April"),
        Month(5, "Mei"),
        Month(6, "Juni")
        ,
        Month(7, "Juli"),
        Month(8, "Agustus"),
        Month(9, "September")
        ,
        Month(10, "Oktober"),
        Month(11, "November"),
        Month(12, "Desember")
    )

    fun years(): List<Int> {

        val year = Calendar.getInstance().get(Calendar.YEAR)
        val interval = year - 1900
        val mutableYear: MutableList<Int> = mutableListOf()

        repeat(interval) {
            mutableYear.add(year - it)
        }

        return mutableYear
    }

}