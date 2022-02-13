package com.codes_roots.golden_coupon.presentation.homefragment.mvi



sealed class MainIntent(open val viewState: MainViewState? = null,open val brand_id:Int?=null,open val user_id:Int??=null) {

    data class Initialize(override val viewState: MainViewState) : MainIntent()
    data class AddToFavorite(override val viewState: MainViewState,override val brand_id:Int?,override val user_id:Int?) : MainIntent()

    data class ErrorDisplayed(override val viewState: MainViewState) : MainIntent()
    data  class SearchByName(override val viewState: MainViewState?, val Name:String? = null): MainIntent()

}
