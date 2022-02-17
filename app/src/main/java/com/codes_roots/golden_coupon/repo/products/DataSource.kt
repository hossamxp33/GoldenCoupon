package com.codes_roots.golden_coupon.repo.products


import com.codes_roots.golden_coupon.entites.allbrands.AllBrandsModel
import com.codes_roots.golden_coupon.entites.category.AllCategoryModel
import com.codes_roots.golden_coupon.entites.products.ProductsModel


interface ProductsDataSource {

    suspend fun getCategoryResponse(): AllCategoryModel
    suspend fun getAllBrandsResponse(): AllBrandsModel

    suspend fun getProductsResponse(country_id: Int?,FilterData:HashMap<String,String>,cat_id: String?): ProductsModel


}