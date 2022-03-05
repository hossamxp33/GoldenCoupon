package com.codes_roots.golden_coupon.presentation.favfragment.mvi



sealed class MainIntent(open val viewState: MainViewState? = null,open val brand_id:Int?=null,open val user_id:Int?=null,) {

    data class Initialize(override val viewState: MainViewState) : MainIntent()

    data class DeleteFavorite(override val viewState: MainViewState, override val brand_id:Int?) : MainIntent()

    data class ErrorDisplayed(override val viewState: MainViewState) : MainIntent()

}
