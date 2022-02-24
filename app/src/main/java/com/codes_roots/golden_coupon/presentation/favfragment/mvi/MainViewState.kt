package com.codes_roots.golden_coupon.presentation.favfragment.mvi

import com.codes_roots.golden_coupon.entites.brandsmodel.Brand
import com.codes_roots.golden_coupon.entites.brandsmodel.BrandsModel
import com.codes_roots.golden_coupon.entites.fav.FavouritModel
import com.codes_roots.golden_coupon.presentation.homefragment.mvi.UserError


data class MainViewState(
    var data: FavouritModel?=null,
    val noFavFound:  Boolean? = null,
    val progress:  Boolean? = null,
    var IsFave : Boolean ? = false,
    var error: UserError?=null

)