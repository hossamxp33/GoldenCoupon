package com.codes_roots.golden_coupon.presentation.homefragment.mvi

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.codes_roots.golden_coupon.entites.coupons.CouponsModel
import com.codes_roots.golden_coupon.entites.whatsapp.WhatsAppModel
import com.codes_roots.golden_coupon.repo.brands.DataRepo
import com.codes_roots.golden_coupon.repo.brands.DataSource
import com.codes_roots.golden_coupon.repo.brands.RemoteDataSource
import com.tarweej.mypost.BaseViewModel
import kotlinx.coroutines.*


import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject


class MainViewModel @Inject constructor(
    private val DateRepoCompnay: DataRepo,private val Datasources: RemoteDataSource


    ) : BaseViewModel<MainViewState>() {

    val intents: Channel<MainIntent> = Channel<MainIntent>(Channel.UNLIMITED)

    protected val uiState: MutableStateFlow<MainViewState?> = MutableStateFlow(MainViewState())

    val state: MutableStateFlow<MainViewState?> get() = uiState

    private var job: Job? = null
    private val viewStateMapper: suspend (MainIntent) -> MainViewState =
        { mapIntentToViewState(it, DateRepoCompnay) }


    val errorMessage = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()
    var whatsAppLD: MutableLiveData<WhatsAppModel>? = null


    init {


        getIntent()
        whatsAppLD= MutableLiveData()

// لازم ابعت viewstate كامل واتحكم بكل option حسب المطلوب وارساله مرة اخري واستقباله هنا او في ال view

        uiState.value = MainViewState().copy(progress = true)
        // launchNextViewStateJob(intents.value)
    }

    fun getIntent() {

        job = viewModelScope.launch() {

            intents.receiveAsFlow().collect {

                uiState.value = (viewStateMapper(it))

            }
        }
    }
    fun getWhatsApp() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = Datasources.getwhatsApp()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    whatsAppLD?.postValue(response.body())

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




