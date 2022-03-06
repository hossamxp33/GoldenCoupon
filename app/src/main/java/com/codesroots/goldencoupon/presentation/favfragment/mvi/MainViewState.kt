package com.codesroots.goldencoupon.presentation.favfragment.mvi

import com.codesroots.goldencoupon.entites.fav.FavouritModel
import com.codesroots.goldencoupon.presentation.homefragment.mvi.UserError


data class MainViewState(
    var data: FavouritModel?=null,
    val noFavFound:  Boolean? = null,
    val progress:  Boolean? = null,
    var IsFave : Boolean ? = false,
    var error: UserError?=null

)