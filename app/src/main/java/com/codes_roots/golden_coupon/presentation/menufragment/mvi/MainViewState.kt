package com.codes_roots.golden_coupon.presentation.menufragment.mvi

import com.codes_roots.golden_coupon.entites.staticpages.StaticPagesItem
import com.codes_roots.golden_coupon.presentation.homefragment.mvi.UserError


data class MainViewState(
    var data: List<StaticPagesItem>?=null,
    val progress:  Boolean? = null,
    var error: UserError?=null

)