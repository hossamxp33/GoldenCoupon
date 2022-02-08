package com.codes_roots.golden_coupon.presentation.favfragment.mvi


import com.codes_roots.golden_coupon.entites.brandsmodel.Brand
import com.codes_roots.golden_coupon.entites.brandsmodel.BrandsModel
import com.codes_roots.golden_coupon.entites.fav.FavouritModel
import com.codes_roots.golden_coupon.presentation.homefragment.mvi.UserError
import com.codes_roots.golden_coupon.repo.brands.DataRepo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first


/**
 * this is the model function in MVI, it's responsibility is to convert intents into views states
 */
suspend fun mapIntentToViewState(
    intent: MainIntent,
    Datarepo: DataRepo,
    loadMainData: suspend () -> Flow<Result<FavouritModel>> = { Datarepo.getFavouriteData },
) = when (intent) {
    is MainIntent.Initialize -> proceedWithInitialize(loadMainData, intent)
    is MainIntent.ErrorDisplayed -> intent.viewState.copy(error = null)

}


private suspend fun proceedWithInitialize(
    loadCart: suspend () -> Flow<Result<FavouritModel>>,
    intent: MainIntent
): MainViewState {
    var response = loadCart()
    var data = response.first()
    return runCatching {
        intent.viewState!!.copy(
            data = (data.getOrThrow()),
            error = null,
            progress = false,
            noFavFound = false
        )
    }
        .getOrElse {
            intent.viewState!!.copy(error = UserError.NetworkError(it),noFavFound = true)
        }


}












