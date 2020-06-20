package id.dev.qurmer.intro.register

import android.view.View
import android.widget.AdapterView

interface ChooseItemSpinnerListener {
    fun onChoose(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id : Long
    )
}