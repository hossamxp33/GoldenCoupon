package com.codes_roots.golden_coupon.data_layer

import com.codes_roots.golden_coupon.entites.brandsmodel.BrandsModel
import com.codes_roots.golden_coupon.entites.category.AllCategoryModel
import com.codes_roots.golden_coupon.entites.products.ProductsModel
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


interface APIServices {

    @GET("brands.json")
    suspend fun getBrandsData(): BrandsModel

    @GET("categories/getAllCats.json")
    suspend fun getCategoryData(): AllCategoryModel

    @FormUrlEncoded
    @POST("products.json")
    suspend fun getProductData(@Field("Filter[cat_id]") cat_id: Int?): ProductsModel


}