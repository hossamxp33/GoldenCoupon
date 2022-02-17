package com.codes_roots.golden_coupon.presentation.homefragment.mvi



sealed class MainIntent(open val viewState: MainViewState? = null,open val brand_id:Int?=null,open val user_id:Int?=null,open val page:Int?=null,open val name:String?=null) {

    data class Initialize(override val viewState: MainViewState,override val page: Int?) : MainIntent()
    data class AddToFavorite(override val viewState: MainViewState,override val brand_id:Int?,override val user_id:Int?) : MainIntent()
    data class ShowProgress(override val viewState: MainViewState) : MainIntent()

    data class ErrorDisplayed(override val viewState: MainViewState) : MainIntent()
    data  class SearchByName(override val viewState: MainViewState?,override val name:String? = null): MainIntent()

}
