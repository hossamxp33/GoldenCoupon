package com.codesroots.goldencoupon.presentation.menufragment.mvi


sealed class MainIntent(open val viewState: MainViewState? = null) {

    data class Initialize(override val viewState: MainViewState) : MainIntent()


    data class ErrorDisplayed(override val viewState: MainViewState) : MainIntent()

}
