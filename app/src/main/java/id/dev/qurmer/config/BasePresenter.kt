package id.dev.qurmer.config

import id.dev.qurmer.data.repository.APIRepository
import id.dev.qurmer.data.repository.APIService
import kotlinx.coroutines.Job

abstract class BasePresenter {

    var service: APIService = APIRepository.makeRetrofitService()
    var job: Job? = null

    fun onCleared (){
        job?.cancel()
    }

    fun onStart(){
        job?.start()
    }
}