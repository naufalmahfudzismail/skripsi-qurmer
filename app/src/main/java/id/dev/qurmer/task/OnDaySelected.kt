package id.dev.qurmer.task

import android.app.Dialog

interface OnDaySelected {
    fun onSelected(dialog : Dialog, day : Int)
}