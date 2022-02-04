package com.codes_roots.golden_coupon.repo.products


import com.codes_roots.golden_coupon.entites.category.AllCategoryModel
import com.codes_roots.golden_coupon.entites.products.ProductsModel


interface ProductsDataSource {

    suspend fun getCategoryResponse(): AllCategoryModel

    suspend fun getProductsResponse(cat_id: Int?): ProductsModel


}