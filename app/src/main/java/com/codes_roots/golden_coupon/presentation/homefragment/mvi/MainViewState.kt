package com.codes_roots.golden_coupon.presentation.homefragment.mvi

import com.codes_roots.golden_coupon.entites.brandsmodel.Brand
import com.codes_roots.golden_coupon.entites.brandsmodel.BrandsModel


data class MainViewState(
    var homepagedata: BrandsModel?=null,
    var filteredData: ArrayList<Brand> ?=null,
    val noBrandFound:  Boolean? = null,
    val progress:  Boolean? = null,
    var IsFave : Boolean ? = false,
    var error:UserError?=null


)