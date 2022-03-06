package com.codesroots.goldencoupon.presentation.dealsfragment.mvi

import com.codesroots.goldencoupon.entites.deals.DealsModel
import com.codesroots.goldencoupon.presentation.homefragment.mvi.UserError


data class MainViewState(
    var data: DealsModel?=null,
    val noFavFound:  Boolean? = null,
    val progress:  Boolean? = null,
    var error: UserError?=null

)