package com.codes_roots.golden_coupon.presentation.dealsfragment.mvi

import com.codes_roots.golden_coupon.entites.brandsmodel.Brand
import com.codes_roots.golden_coupon.entites.brandsmodel.BrandsModel
import com.codes_roots.golden_coupon.entites.deals.DealsModel
import com.codes_roots.golden_coupon.entites.fav.FavouritModel
import com.codes_roots.golden_coupon.presentation.homefragment.mvi.UserError


data class MainViewState(
    var data: DealsModel?=null,
    val noFavFound:  Boolean? = null,
    val progress:  Boolean? = null,
    var error: UserError?=null

)