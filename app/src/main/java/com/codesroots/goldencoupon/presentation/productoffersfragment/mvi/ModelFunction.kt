package com.codesroots.goldencoupon.presentation.productoffersfragment.mvi


import com.codesroots.goldencoupon.entites.allbrands.AllBrandsModel
import com.codesroots.goldencoupon.entites.category.AllCategoryModel
import com.codesroots.goldencoupon.entites.products.Product
import com.codesroots.goldencoupon.entites.products.ProductsModel
import com.codesroots.goldencoupon.presentation.homefragment.mvi.UserError
import com.codesroots.goldencoupon.repo.products.DataRepo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlin.collections.ArrayList


/**
 * this is the model function in MVI, it's responsibility is to convert intents into views states
 */
suspend fun mapIntentToViewState(
    intent: MainIntent,
    Datarepo: DataRepo,

    loadCategoryData: suspend () -> Flow<Result<AllCategoryModel>> = { Datarepo.getCategoryData },

    loadAllBrandsData: suspend () -> Flow<Result<AllBrandsModel>> = { Datarepo.getAllBrandsResponse },

    loadProducts: suspend () -> Flow<Result<ProductsModel>> = {Datarepo.getProductsData(intent.country_id!!) },

    loadProductsData: suspend () -> Flow<Result<ProductsModel>> = {Datarepo.getProductsData(intent.country_id!!,intent.sort!!,intent.FilterMap!!) },

    //getProductsData
) = when (intent) {
    is MainIntent.InitializeData -> proceedWithInitialize(
        loadCategoryData,
        loadProductsData,
        loadAllBrandsData,
        loadProducts,
        intent)
    is MainIntent.ErrorDisplayed -> intent.viewState.copy(error = null)
    is MainIntent.SearchByName -> searchByName(intent, intent.Name!!)
    is MainIntent.GetBrandList ->  proceedWithBrandList(loadAllBrandsData,intent)
    is MainIntent.FilterData -> proceedWithInitialize(
        loadCategoryData,
        loadProductsData,
        loadAllBrandsData,
        loadProducts,
        intent,
    )
    is MainIntent.FilterDataBySubCategory ->filterDataBySubCategoryId(intent,intent.subcategory_id!!)
}
private suspend fun proceedWithBrandList(
    allBrandData:suspend () -> Flow<Result<AllBrandsModel>>,
    intent: MainIntent,
): MainViewState {
    val  brandsResponse =  allBrandData()
    val allBransData =   brandsResponse.first()
    return runCatching {
        intent.viewState!!.copy(
            allBrandsData = allBransData.getOrThrow(),
            error = null,
            progress = false,
            noProductsFound = false
        )
    }
        .getOrElse {
            intent.viewState!!.copy(error = UserError.NetworkError(it), noProductsFound = true)
        }


}

private suspend fun proceedWithInitialize(
    categoryData: suspend () -> Flow<Result<AllCategoryModel>>,
    allProductsData: suspend () -> Flow<Result<ProductsModel>>,
    allBrandData:suspend () -> Flow<Result<AllBrandsModel>>,
    productsData:suspend () -> Flow<Result<ProductsModel>>,


    intent: MainIntent,
): MainViewState {
    val categoryDataResponse = categoryData()

    val productsDataResponse = allProductsData()
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







