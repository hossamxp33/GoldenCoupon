package com.codesroots.goldencoupon.presentation.menufragment.mvi


import com.codesroots.goldencoupon.entites.staticpages.StaticPagesModel
import com.codesroots.goldencoupon.presentation.homefragment.mvi.UserError
import com.codesroots.goldencoupon.repo.brands.DataRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first


/**
 * this is the model function in MVI, it's responsibility is to convert intents into views states
 */
suspend fun mapIntentToViewState(intent: MainIntent, Datarepo: DataRepo,
                                 loadMainData: suspend () -> Flow<Result<StaticPagesModel>> = { Datarepo.getStaticPages() },
) = when (intent) {
    is MainIntent.Initialize -> proceedWithInitialize(loadMainData,intent)
    is MainIntent.ErrorDisplayed -> intent.viewState.copy(error = null)
}


private suspend fun proceedWithInitialize(loadCart: suspend () -> Flow<Result<StaticPagesModel>>, intent:MainIntent): MainViewState {
    val response =   loadCart()
    val data = response.first()
    return runCatching {
        intent.viewState!!.copy(data = data.map { it.data }.getOrThrow(), error = null, progress = false)
    }
        .getOrElse {
            intent.viewState!!.copy(error = UserError.NetworkError(it))
        }
}




