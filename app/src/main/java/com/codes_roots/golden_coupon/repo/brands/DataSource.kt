package com.codes_roots.golden_coupon.repo.brands


import com.codes_roots.golden_coupon.entites.brandsmodel.BrandsModel
import com.codes_roots.golden_coupon.entites.coupons.CouponsModel
import retrofit2.Response


interface DataSource {

    suspend fun getBrandsResponse(): BrandsModel

    suspend fun getCouponsResponse(brandid:Int): Response<CouponsModel>


}