package id.dev.qurmer.data

import id.dev.qurmer.data.model.Month
import java.text.SimpleDateFormat
import java.util.*

object GlobalData{


    var LEVEL = 1
    var isChallenge = false
    var SUB_LEVEL = 1

    val FRAGMENT_STACK: MutableList<Int> = mutableListOf()

    private val id = Locale("in", "ID")
    private const val DATE_FORMAT_FROM_SERVER = "yyyy-MM-dd"
    private const val DATE_FORMAT_WITH_TIME_SERVER = "yyyy-MM-dd HH:mm:ss"
    private const val DATE_FORMAT_FOR_REQUEST = "YYYYMMDD"
    private const val TARGET_DATE_FORMAT = "EEEE, dd MMM yyyy"
    private const val TARGET_DATE_WITH_TIME = "EEEE, dd MMMM yyyy HH:mm"
    private const val TARGET_JUST_DAY = "EEEE, dd MMM"

    private val NEW_FORMAT = "dd-MM-yyyy"

    val oldDateFormat = SimpleDateFormat(
        DATE_FORMAT_FROM_SERVER,
        id
    )

    val newFormatNoDay = SimpleDateFormat(
        NEW_FORMAT,
        id
    )
    val newFormat = SimpleDateFormat(
        TARGET_DATE_FORMAT,
        id
    )
    val justDayFormat = SimpleDateFormat(
        TARGET_JUST_DAY,
        id
    )

    val oldFormatWithTime = SimpleDateFormat(
        DATE_FORMAT_WITH_TIME_SERVER,
        id
    )

    val newFormatWithTime = SimpleDateFormat(
        TARGET_DATE_WITH_TIME,
        id
    )


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