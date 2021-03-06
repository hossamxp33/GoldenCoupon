package com.codesroots.goldencoupon.presentation.productoffersfragment.mvi


sealed class MainIntent(
    open val viewState: MainViewState? = null,
    open val cat_id: Int? = null,
    open val subcategory_id: Int? = null,
    open val country_id: Int? = null,
    open val sort: String? = "name",
    open val page: Int? = null,
    open val FilterMap: HashMap<String, String>? = null,
) {

    data class InitializeData(
        override val viewState: MainViewState,
        override val sort: String? = null,
        override val page: Int? = null,

        override val cat_id: Int? = null,
        override val country_id: Int? = null,
    ) : MainIntent()

    data class ErrorDisplayed(override val viewState: MainViewState) : MainIntent()

    data class SearchByName(override val viewState: MainViewState?, val Name: String? = null) :
        MainIntent()

    data class GetBrandList(override val viewState: MainViewState? = null) : MainIntent()

    data class Paging(override val viewState: MainViewState? = null  ,
                      override val country_id: Int? = null
        , override val page: Int? = null,
    ) : MainIntent()


    data class FilterData(
        override val viewState: MainViewState? = null,
        override val FilterMap: HashMap<String, String>? = null,
        override val country_id: Int? = null,
        override val sort: String? = "id",
        ) : MainIntent()

    data class FilterDataBySubCategory(
        override val viewState: MainViewState? = null,
        override val subcategory_id: Int? = null,
    ) : MainIntent()

}
