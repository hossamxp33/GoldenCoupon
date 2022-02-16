package com.codes_roots.golden_coupon.presentation.dealsfragment.mvi

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.codes_roots.golden_coupon.repo.brands.DataRepo
import com.codes_roots.golden_coupon.repo.brands.DataSource
import com.tarweej.mypost.BaseViewModel


import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class DealsViewModel @Inject constructor(
    private val DateRepoCompnay: DataRepo,


    ) : BaseViewModel<MainViewState>() {

    val intents: Channel<MainIntent> = Channel<MainIntent>(Channel.UNLIMITED)

    protected val uiState: MutableStateFlow<MainViewState?> =
        MutableStateFlow(MainViewState())

    val state: MutableStateFlow<MainViewState?> get() = uiState

    private var job: Job? = null
    private val viewStateMapper: suspend (MainIntent) -> MainViewState =
        { mapIntentToViewState(it, DateRepoCompnay) }


    val errorMessage = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()


    init {


        getIntent()

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


    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job!!.cancel()
    }


}




