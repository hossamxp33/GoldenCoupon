package com.codes_roots.golden_coupon.repo.products


import com.codes_roots.golden_coupon.data_layer.APIServices
import com.codes_roots.golden_coupon.entites.allbrands.AllBrandsModel
import com.codes_roots.golden_coupon.entites.brandsmodel.Brand
import com.codes_roots.golden_coupon.entites.brandsmodel.BrandsModel
import com.codes_roots.golden_coupon.entites.category.AllCategoryModel
import com.codes_roots.golden_coupon.entites.products.ProductsModel

import javax.inject.Inject

class RemoteProductsDataSource @Inject constructor(private val ApiService: APIServices) :
    ProductsDataSource {

    override suspend fun getCategoryResponse(): AllCategoryModel =
        runCatching { ApiService.getCategoryData() }
            .getOrElse { throw it }

    override suspend fun getProductsResponse(country_id: Int?,FilterData:HashMap<String,String>, cat_id: String?): ProductsModel =
        runCatching { ApiService.getProductData(country_id,FilterData) }
            .getOrElse { throw it }

   override suspend fun getAllBrandsResponse(): AllBrandsModel =
        runCatching { ApiService.getAllBrands() }
            .getOrElse { throw it }


}