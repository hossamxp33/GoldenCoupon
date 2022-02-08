package com.codes_roots.golden_coupon.data_layer

import com.codes_roots.golden_coupon.entites.brandsmodel.BrandsModel
import com.codes_roots.golden_coupon.entites.category.AllCategoryModel
import com.codes_roots.golden_coupon.entites.countries.CountryModel
import com.codes_roots.golden_coupon.entites.coupons.CouponsModel
import com.codes_roots.golden_coupon.entites.fav.FavouritModel
import com.codes_roots.golden_coupon.entites.products.ProductsModel
import retrofit2.Response
import retrofit2.http.*


interface APIServices {

    @GET("brands.json")
    suspend fun getBrandsData(): BrandsModel

    @GET("categories/getAllCats.json")
    suspend fun getCategoryData(): AllCategoryModel

    @FormUrlEncoded
    @POST("products/index/1.json")
    suspend fun getProductData(@Field("Filter[cat_id]") cat_id: Int?): ProductsModel

    @GET("items/index/{brandid}.json")
    suspend fun getCouponsData(@Path("brandid") brandid: Int): Response<CouponsModel>

    @GET("countries/getAllCountries.json")
    suspend fun getCountries(): Response<CountryModel>

    @GET("FavouriteItems/index.json")
    suspend fun getFavorite(): FavouritModel

    //
}