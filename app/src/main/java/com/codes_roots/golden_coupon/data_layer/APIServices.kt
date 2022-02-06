package com.codes_roots.golden_coupon.data_layer

import com.codes_roots.golden_coupon.entites.brandsmodel.BrandsModel
import com.codes_roots.golden_coupon.entites.category.AllCategoryModel
import com.codes_roots.golden_coupon.entites.coupons.CouponsModel
import com.codes_roots.golden_coupon.entites.products.ProductsModel
import retrofit2.Response
import retrofit2.http.*


interface APIServices {

    @GET("brands.json")
    suspend fun getBrandsData(): BrandsModel

    @GET("categories/getAllCats.json")
    suspend fun getCategoryData(): AllCategoryModel

    @FormUrlEncoded
    @POST("products.json")
    suspend fun getProductData(@Field("Filter[cat_id]") cat_id: Int?): ProductsModel

    @GET("items/index/{brandid}.json")
    suspend fun getCouponsData(@Path("brandid") brandid: Int): Response<CouponsModel>

}