package com.codesroots.goldencoupon.presentation.dealsfragment.mvi


import com.codesroots.goldencoupon.entites.deals.DealsModel
import com.codesroots.goldencoupon.presentation.homefragment.mvi.UserError
import com.codesroots.goldencoupon.repo.brands.DataRepo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first


/**
 * this is the model function in MVI, it's responsibility is to convert intents into views states
 */
suspend fun mapIntentToViewState(
    intent: MainIntent,
    Datarepo: DataRepo,
    loadMainData: suspend () -> Flow<Result<DealsModel>> = { Datarepo.getDealsData(intent.page!!) },
) = when (intent) {
    is MainIntent.Initialize -> proceedWithInitialize(loadMainData, intent)
    is MainIntent.ErrorDisplayed -> intent.viewState.copy(error = null)

}


private suspend fun proceedWithInitialize(
    loadCart: suspend () -> Flow<Result<DealsModel>>,
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












