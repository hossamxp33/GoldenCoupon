package com.codesroots.goldencoupon.repo.products


import com.codesroots.goldencoupon.entites.allbrands.AllBrandsModel
import com.codesroots.goldencoupon.entites.category.AllCategoryModel
import com.codesroots.goldencoupon.entites.products.ProductsModel


interface ProductsDataSource {

    suspend fun getCategoryResponse(): AllCategoryModel
    suspend fun getAllBrandsResponse(): AllBrandsModel

    suspend fun getProductsResponse(page:Int?,country_id: Int?,FilterData:HashMap<String,String>,cat_id: String?): ProductsModel


}