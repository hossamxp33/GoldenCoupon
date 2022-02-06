package com.codes_roots.golden_coupon.repo.brands


import com.codes_roots.golden_coupon.data_layer.APIServices
import com.codes_roots.golden_coupon.entites.brandsmodel.BrandsModel
import com.codes_roots.golden_coupon.entites.coupons.CouponsModel
import retrofit2.Response

import javax.inject.Inject


class RemoteDataSource @Inject constructor(private val ApiService: APIServices) : DataSource {

    override suspend fun getBrandsResponse(): BrandsModel =
        runCatching { ApiService.getBrandsData() }
            .getOrElse { throw it }

    override suspend fun getCouponsResponse(brandid:Int): Response<CouponsModel> {
       return ApiService.getCouponsData(brandid)
    }


}