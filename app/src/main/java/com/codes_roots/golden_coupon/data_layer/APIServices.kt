package com.codes_roots.golden_coupon.data_layer

import com.codes_roots.golden_coupon.entites.brandsmodel.BrandsModel
import retrofit2.http.GET


interface APIServices {

    @GET("brands.json")
    suspend fun getBrandsData(): BrandsModel



}