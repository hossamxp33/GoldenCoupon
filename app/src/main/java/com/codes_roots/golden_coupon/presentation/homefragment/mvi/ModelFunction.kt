package com.codes_roots.golden_coupon.presentation.homefragment.mvi


import com.codes_roots.golden_coupon.entites.brandsmodel.Brand
import com.codes_roots.golden_coupon.entites.brandsmodel.BrandsModel
import com.codes_roots.golden_coupon.repo.brands.DataRepo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first


/**
 * this is the model function in MVI, it's responsibility is to convert intents into views states
 */
suspend fun mapIntentToViewState(
    intent: MainIntent,
    Datarepo: DataRepo,
    loadMainData: suspend () -> Flow<Result<BrandsModel>> = { Datarepo.getMainData(intent.page,"") },
    loadSearchByName: suspend () -> Flow<Result<BrandsModel>> = { Datarepo.getMainData(intent.page,intent.name!!) },
    AddFavorite: suspend () ->  Flow<Result<Boolean>> = { Datarepo.addFavouriteData(intent.brand_id!!) },
    DeleteFavorite: suspend () ->  Flow<Result<Boolean>> = { Datarepo.deleteFavorite(intent.brand_id!!) },


) = when (intent) {
    is MainIntent.Initialize -> proceedWithInitialize(loadMainData, intent)
    is MainIntent.ShowProgress -> intent.viewState.copy(progress = true)
    is MainIntent.ErrorDisplayed -> intent.viewState.copy(error = null,filteredData=null)
    is MainIntent.SearchByName -> proceedWithInitialize(loadSearchByName, intent)
    is MainIntent.AddToFavorite -> proceedAddFavorite(AddFavorite,intent)
    is MainIntent.DeleteFavorite -> proceedDeleteFavorite(DeleteFavorite,intent)
}


private suspend fun proceedWithInitialize(
    loadCart: suspend () -> Flow<Result<BrandsModel>>,
    intent: MainIntent
): MainViewState {
    val response = loadCart()
    val data = response.first()
    return runCatching {
        intent.viewState!!.copy(
            homepagedata = (data.getOrThrow()),
            filteredData = data.map { it.brands }.getOrThrow(),
            error = null,
            progress = false,
            noBrandFound = false
        )
    }
        .getOrElse {
            intent.viewState!!.copy(error = UserError.NetworkError(it),noBrandFound = true)
        }


}
private suspend fun proceedAddFavorite( AddFavorite : suspend () -> Flow<Result<Boolean>>   ,intent: MainIntent
): MainViewState
{
    val response =  AddFavorite().first()

    return runCatching {

        return intent.viewState!!.copy(IsFave = true)
    }.getOrElse {
        intent.viewState!!.copy(error = UserError.NetworkError(it))

    }

}private suspend fun proceedDeleteFavorite( DeleteFavorite : suspend () -> Flow<Result<Boolean>>   ,intent: MainIntent
): MainViewState
{
    val response =  DeleteFavorite().first()

    return runCatching {

        return intent.viewState!!.copy(IsFave = false)
    }.getOrElse {
        intent.viewState!!.copy(error = UserError.NetworkError(it))

    }
}

private fun searchByName(intent: MainIntent, Name: String): MainViewState {
    val filterData = SearchFamousBynamee(Name, intent.viewState?.homepagedata!!.brands)

    return intent.viewState!!.copy(filteredData = filterData as ArrayList)
}


fun SearchFamousBynamee(Name: String, brandsArray: ArrayList<Brand>?) =
    brandsArray?.filter { data ->
        data.name!!.contains(Name)
    }








