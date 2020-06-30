package id.dev.qurmer.challenge

import android.util.Log
import id.dev.qurmer.config.BaseView
import id.dev.qurmer.data.model.AfterChallengeResponse
import id.dev.qurmer.data.model.JoinChallengeResponse
import id.dev.qurmer.data.model.ChallengeResponse

interface ChallengeView : BaseView {

    fun onResult(result : ChallengeResponse?)

    fun onJoinChallenge(result : JoinChallengeResponse?){
        Log.e("HASIL RESULT JOIN", result.toString())
    }

    fun onAfterChallenge(result : AfterChallengeResponse?){
        Log.e("HASIL RESULT AFTER", result.toString())
    }
}