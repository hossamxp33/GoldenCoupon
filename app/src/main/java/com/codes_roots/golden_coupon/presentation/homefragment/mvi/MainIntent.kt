package com.codes_roots.golden_coupon.presentation.homefragment.mvi



sealed class MainIntent(open val viewState: MainViewState? = null) {

    data class Initialize(override val viewState: MainViewState) : MainIntent()

    data class ErrorDisplayed(override val viewState: MainViewState) : MainIntent()
    data  class SearchByName(override val viewState: MainViewState?, val Name:String? = null): MainIntent()

}
