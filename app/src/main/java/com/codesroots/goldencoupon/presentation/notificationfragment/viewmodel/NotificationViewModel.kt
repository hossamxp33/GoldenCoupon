package com.codesroots.goldencoupon.presentation.notificationfragment.viewmodel

import androidx.lifecycle.MutableLiveData
import com.codesroots.goldencoupon.entites.notification.NotificationModel
import com.codesroots.goldencoupon.presentation.favfragment.mvi.MainViewState
import com.codesroots.goldencoupon.repo.brands.RemoteDataSource
import com.tarweej.mypost.BaseViewModel
import kotlinx.coroutines.*

import javax.inject.Inject


class NotificationViewModel@Inject constructor(
                                               private val Datasources: RemoteDataSource, )
    : BaseViewModel<MainViewState>() {


    private var job: Job? = null
    var NotificationLD = MutableLiveData <NotificationModel>()

    val errorMessage = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()



    init {


// لازم ابعت viewstate كامل واتحكم بكل option حسب المطلوب وارساله مرة اخري واستقباله هنا او في ال view

        NotificationLD = MutableLiveData()
   // launchNextViewStateJob(intents.value)
}



    fun getNotification() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = Datasources.getNotifications()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    NotificationLD.postValue(response.body())

                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }

    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job!!.cancel()
    }



}




