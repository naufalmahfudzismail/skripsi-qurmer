package id.dev.qurmer.config

import android.app.Dialog

interface DialogChallengeListener {

    fun onPositiveClicked(dialog : Dialog)
    fun onNegativeClicked(dialog : Dialog)
}