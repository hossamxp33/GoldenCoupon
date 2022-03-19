package com.codesroots.goldencoupon.repo.products


import com.codesroots.goldencoupon.data_layer.APIServices
import com.codesroots.goldencoupon.entites.allbrands.AllBrandsModel
import com.codesroots.goldencoupon.entites.category.AllCategoryModel
import com.codesroots.goldencoupon.entites.products.ProductsModel

import javax.inject.Inject

class RemoteProductsDataSource @Inject constructor(private val ApiService: APIServices) :
    ProductsDataSource {

    override suspend fun getCategoryResponse(): AllCategoryModel =
        runCatching { ApiService.getCategoryData() }
            .getOrElse { throw it }

    override suspend fun getProductResponse(country_id: Int?): ProductsModel =
        runCatching { ApiService.getProduct(country_id) }
            .getOrElse { throw it }

    override suspend fun getFilterProductsResponse(country_id: Int?,FilterData:HashMap<String,String>, cat_id: String?): ProductsModel =
        runCatching { ApiService.getProductData(country_id,FilterData) }
            .getOrElse { throw it }

   override suspend fun getAllBrandsResponse(): AllBrandsModel =
        runCatching { ApiService.getAllBrands() }
            .getOrElse { throw it }


}