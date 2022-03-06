package com.codesroots.goldencoupon.presentation.menufragment.mvi

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codesroots.goldencoupon.helper.PreferenceHelper
import com.codesroots.goldencoupon.repo.brands.DataRepo

import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class StaticPagesViewModel @Inject constructor(
    private val DateRepoCompnay: DataRepo,
) : ViewModel() {

    @Inject
    lateinit var Pref: PreferenceHelper
    private var job: Job? = null
    var mCompositeDisposable = CompositeDisposable()
    val intents: Channel<MainIntent> = Channel<MainIntent>(Channel.UNLIMITED)
    protected val uiState: MutableStateFlow<MainViewState?> = MutableStateFlow(MainViewState())
    val state: MutableStateFlow<MainViewState?> get() = uiState

    val loading = MutableLiveData<Boolean>()

    private val viewStateMapper: suspend (MainIntent)

    -> MainViewState = { mapIntentToViewState(it, DateRepoCompnay) }


    init {
        getIntent()
        uiState.value = MainViewState().copy(progress = true)

    }


    fun getIntent() {

        job = viewModelScope.launch() {

            intents.receiveAsFlow().collect {

                uiState.value = (viewStateMapper(it))

            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        mCompositeDisposable.dispose()
        mCompositeDisposable.clear()
        job?.cancel()
    }
}