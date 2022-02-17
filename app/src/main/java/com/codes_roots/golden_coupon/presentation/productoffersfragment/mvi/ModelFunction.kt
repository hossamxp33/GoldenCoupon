package com.codes_roots.golden_coupon.presentation.productoffersfragment.mvi


import com.codes_roots.golden_coupon.entites.allbrands.AllBrandsModel
import com.codes_roots.golden_coupon.entites.category.AllCategoryModel
import com.codes_roots.golden_coupon.entites.products.Product
import com.codes_roots.golden_coupon.entites.products.ProductsModel
import com.codes_roots.golden_coupon.presentation.homefragment.mvi.UserError
import com.codes_roots.golden_coupon.repo.products.DataRepo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.util.*
import kotlin.collections.ArrayList


/**
 * this is the model function in MVI, it's responsibility is to convert intents into views states
 */
suspend fun mapIntentToViewState(
    intent: MainIntent,
    Datarepo: DataRepo,
    loadCategoryData: suspend () -> Flow<Result<AllCategoryModel>> = { Datarepo.getCategoryData },
    loadAllBrandsData: suspend () -> Flow<Result<AllBrandsModel>> = { Datarepo.getAllBrandsResponse },
    loadProductsData: suspend () -> Flow<Result<ProductsModel>> = { Datarepo.getProductsData(intent.country_id!!,intent.sort!!,intent.cat_id!!) },

    //getProductsData
) = when (intent) {
    is MainIntent.InitializeData -> proceedWithInitialize(
        loadCategoryData,
        loadProductsData,
        loadAllBrandsData,
        intent
    )
    is MainIntent.ErrorDisplayed -> intent.viewState.copy(error = null)
    is MainIntent.SearchByName -> searchByName(intent, intent.Name!!)
 //   is MainIntent.SortProductsByName -> sortByName(intent)
    is MainIntent.FilterDataByCategory -> filterDataByCatId(intent,intent.cat_id!!)
    is MainIntent.FilterDataBySubCategory ->filterDataBySubCategoryId(intent,intent.subcategory_id!!)
}


private suspend fun proceedWithInitialize(
    categoryData: suspend () -> Flow<Result<AllCategoryModel>>,
    productsData: suspend () -> Flow<Result<ProductsModel>>,
    allBrandData:suspend () -> Flow<Result<AllBrandsModel>>,
    intent: MainIntent,
): MainViewState {
    val categoryDataResponse = categoryData()
    val productsDataResponse = productsData()
val  brandsResponse =  allBrandData()

    val productsData = productsDataResponse.first()
    val categoryData = categoryDataResponse.first()
    val allBransData =   brandsResponse.first()
    return runCatching {
        intent.viewState!!.copy(
            productsData = productsData.getOrThrow(),
            allBrandsData = allBransData.getOrThrow(),
            categoryData = categoryData.getOrThrow(),
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




private  fun filterDataByCatId(intent: MainIntent, catId: Int): MainViewState
{
    val filterDataArray = filterProductByCategoryId(catId,intent.viewState?.productsData!!.brands)
    return intent.viewState!!.copy(filteredData = filterDataArray as ArrayList<Product>)
}
fun filterProductByCategoryId(catId: Int, productArray:  ArrayList<Product>?) =
    productArray!!.filter { data -> data.cat_id == catId }



private  fun filterDataBySubCategoryId(intent: MainIntent, sub_catId: Int): MainViewState
{
    val filterDataArray = filterProductBySubCategoryId(sub_catId,intent.viewState?.productsData!!.brands)
    return intent.viewState!!.copy(filteredData = filterDataArray as ArrayList<Product>)
}
fun filterProductBySubCategoryId(sub_catId: Int, productArray:  ArrayList<Product>?) =
    productArray!!.filter { data -> data.subcat_id == sub_catId }




private fun searchByName(intent: MainIntent, Name: String): MainViewState {
    val filterData = SearchProductByname(Name, intent.viewState?.productsData!!.brands)

    return intent.viewState!!.copy(filteredData = filterData as ArrayList)
}


fun SearchProductByname(Name: String, brandsArray: ArrayList<Product>?) =
    brandsArray?.filter { data ->
        data.name!!.contains(Name)
    }

//
//private  fun sortByName(intent:MainIntent):MainViewState
//{
//    val filterData = sortBranchesByName(intent.viewState?.productsData!!.brands)
//    return intent.viewState!!.copy(filteredData =  filterData)
//
//}
//fun sortBranchesByName(branch: ArrayList<Product>?) =
//    branch?.sortedBy { it.name }
//
//







