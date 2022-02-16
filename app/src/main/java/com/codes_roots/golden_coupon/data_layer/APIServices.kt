package com.codes_roots.golden_coupon.data_layer

import com.codes_roots.golden_coupon.entites.auth.LoginModel
import com.codes_roots.golden_coupon.entites.auth.User
import com.codes_roots.golden_coupon.entites.brandsmodel.BrandsModel
import com.codes_roots.golden_coupon.entites.category.AllCategoryModel
import com.codes_roots.golden_coupon.entites.countries.CountryModel
import com.codes_roots.golden_coupon.entites.coupons.CouponsModel
import com.codes_roots.golden_coupon.entites.deals.DealsModel
import com.codes_roots.golden_coupon.entites.fav.FavouritModel
import com.codes_roots.golden_coupon.entites.products.ProductsModel
import retrofit2.Response
import retrofit2.http.*


interface APIServices {

    @GET("brands.json")
    suspend fun getBrandsData(@Query("page") page: Int?): BrandsModel


    @GET("categories/getAllCats.json")
    suspend fun getCategoryData(): AllCategoryModel

    @GET("OffersSliders.json")
    suspend fun getDealsData(@Query("page") page: Int?): DealsModel

    @FormUrlEncoded
    @POST("products/index/{country_id}.json")
    suspend fun getProductData(
        @Path("country_id") country_id: Int?,
        @Query("sort=cat_id") sort: String?,
        @Field("Filter[cat_id]") cat_id: Int?,
    ): ProductsModel

    @GET("items/index/{brandid}.json")
    suspend fun getCouponsData(@Path("brandid") brandid: Int): Response<CouponsModel>

    @GET("countries/getAllCountries.json")
    suspend fun getCountries(): Response<CountryModel>

    @GET("FavouriteItems/index.json")
    suspend fun getFavorite(): FavouritModel

    @FormUrlEncoded
    @POST("FavouriteItems/add.json")
    suspend fun addFavorite(
        @Field("brand_id") brand_id: Int?,
        @Field("user_id") UserId: Int,
    ): Boolean


    //
    ////////////// Authentication
    @POST("users/token.json")
    @Headers("Accept: Application/json", "cache-control: no-cache")
    suspend fun login(@Body loginModel: User?): Response<LoginModel>
//
//    @POST("createUser")
//    @Headers("Accept: Application/json", "cache-control: no-cache")
//    suspend fun register(@Body registerModel: User?): Response<LoginModel>

}