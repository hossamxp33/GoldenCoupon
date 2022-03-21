package com.codesroots.goldencoupon.presentation.homefragment.mvi

import com.codesroots.goldencoupon.entites.brandsmodel.Brand
import com.codesroots.goldencoupon.entites.brandsmodel.BrandsModel


data class MainViewState(
    var homepagedata: BrandsModel?=null,
    var filteredData: ArrayList<Brand> ?=null,
    val noBrandFound:  Boolean? = null,
    val progress:  Boolean? = null,
    var IsFave : Boolean ? = null,
    var error:UserError?=null


)