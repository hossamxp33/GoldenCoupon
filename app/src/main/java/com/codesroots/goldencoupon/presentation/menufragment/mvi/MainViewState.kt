package com.codesroots.goldencoupon.presentation.menufragment.mvi

import com.codesroots.goldencoupon.entites.staticpages.StaticPagesItem
import com.codesroots.goldencoupon.presentation.homefragment.mvi.UserError


data class MainViewState(
    var data: List<StaticPagesItem>?=null,
    val progress:  Boolean? = null,
    var error: UserError?=null

)