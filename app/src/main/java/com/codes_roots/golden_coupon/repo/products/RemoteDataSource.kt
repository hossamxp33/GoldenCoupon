package com.codes_roots.golden_coupon.repo.products


import com.codes_roots.golden_coupon.data_layer.APIServices
import com.codes_roots.golden_coupon.entites.category.AllCategoryModel
import com.codes_roots.golden_coupon.entites.products.ProductsModel

import javax.inject.Inject

class RemoteProductsDataSource @Inject constructor(private val ApiService: APIServices) :
    ProductsDataSource {

    override suspend fun getCategoryResponse(): AllCategoryModel =
        runCatching { ApiService.getCategoryData() }
            .getOrElse { throw it }

    override suspend fun getProductsResponse(country_id: Int?,sort:String?, cat_id: Int?): ProductsModel =
        runCatching { ApiService.getProductData(country_id,sort, cat_id) }
            .getOrElse { throw it }


}