package com.codes_roots.golden_coupon.presentation.productoffersfragment.mvi



sealed class MainIntent(open val viewState: MainViewState? = null,open val cat_id:Int?=null) {

    data class InitializeData(override val viewState: MainViewState,override val cat_id: Int?) : MainIntent()

    data class ErrorDisplayed(override val viewState: MainViewState) : MainIntent()

    data  class SearchByName(override val viewState: MainViewState?, val Name:String? = null): MainIntent()

    data class SortProducts(override val viewState: MainViewState? = null,): MainIntent()


   data class FilterDataByCategory(override val viewState: MainViewState? = null,override val cat_id: Int? = null): MainIntent()

}
