package com.codes_roots.golden_coupon.presentation.homefragment.mvi

import com.codes_roots.golden_coupon.entites.brandsmodel.BrandsModel


data class MainViewState(
    var homepagedata: BrandsModel?=null,
    val progress:  Boolean? = null,
    var error:UserError?=null

)