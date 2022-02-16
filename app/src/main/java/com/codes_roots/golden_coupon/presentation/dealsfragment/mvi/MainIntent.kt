package com.codes_roots.golden_coupon.presentation.dealsfragment.mvi



sealed class MainIntent(open val viewState: MainViewState? = null,open val page:Int?=null) {

    data class Initialize(override val viewState: MainViewState,override val page: Int?) : MainIntent()

    data class ErrorDisplayed(override val viewState: MainViewState) : MainIntent()

}
