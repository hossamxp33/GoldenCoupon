package com.codes_roots.golden_coupon.presentation.productoffersfragment.mvi


import com.codes_roots.golden_coupon.entites.category.AllCategoryModel
import com.codes_roots.golden_coupon.entites.products.Product
import com.codes_roots.golden_coupon.entites.products.ProductsModel
import com.codes_roots.golden_coupon.presentation.homefragment.mvi.UserError
import com.codes_roots.golden_coupon.repo.products.DataRepo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first


/**
 * this is the model function in MVI, it's responsibility is to convert intents into views states
 */
suspend fun mapIntentToViewState(
    intent: MainIntent,
    Datarepo: DataRepo,
    loadCategoryData: suspend () -> Flow<Result<AllCategoryModel>> = { Datarepo.getCategoryData },
    loadProductsData: suspend () -> Flow<Result<ProductsModel>> = { Datarepo.getProductsData(intent.cat_id!!) },

    //getProductsData
) = when (intent) {
    is MainIntent.InitializeData -> proceedWithInitialize(
        loadCategoryData,
        loadProductsData,
        intent
    )
    is MainIntent.ErrorDisplayed -> intent.viewState.copy(error = null)
    is MainIntent.SearchByName -> searchByName(intent, intent.Name!!)
    is MainIntent.SortProducts -> TODO()
    is MainIntent.FilterDataByCategory -> filterData(intent,intent.cat_id!!)
}


private suspend fun proceedWithInitialize(
    categoryData: suspend () -> Flow<Result<AllCategoryModel>>,
    productsData: suspend () -> Flow<Result<ProductsModel>>,

    intent: MainIntent
): MainViewState {
    val categoryDataResponse = categoryData()

    val productsDataResponse = productsData()

    val productsData = productsDataResponse.first()


    val categoryData = categoryDataResponse.first()

    return runCatching {
        intent.viewState!!.copy(
            productsData = productsData.getOrThrow(),
            categoryData = (categoryData.getOrThrow()),
            filteredData = productsData.map { it.brands }.getOrThrow(),
            error = null,
            progress = false,
            noProductsFound = false
        )
    }
        .getOrElse {
            intent.viewState!!.copy(error = UserError.NetworkError(it), noProductsFound = true)
        }


}




private  fun filterData(intent: MainIntent, catId: Int): MainViewState
{
    val filterDataArray = filterProductByCategoryId(catId,intent.viewState?.filteredData!!)
    return intent.viewState!!.copy(filteredData = filterDataArray as ArrayList<Product>)
}
fun filterProductByCategoryId(catId: Int, productArray:  ArrayList<Product>?) =
    productArray!!.filter { data -> data.cat_id == catId }




private fun searchByName(intent: MainIntent, Name: String): MainViewState {
    val filterData = SearchProductByname(Name, intent.viewState?.productsData!!.brands)

    return intent.viewState!!.copy(filteredData = filterData as ArrayList)
}


fun SearchProductByname(Name: String, brandsArray: ArrayList<Product>?) =
    brandsArray?.filter { data ->
        data.name!!.contains(Name)
    }








