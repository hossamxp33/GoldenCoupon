package com.codesroots.goldencoupon.data_layer

import com.codesroots.goldencoupon.entites.allbrands.AllBrandsModel
import com.codesroots.goldencoupon.entites.auth.LoginModel
import com.codesroots.goldencoupon.entites.auth.User
import com.codesroots.goldencoupon.entites.brandsmodel.BrandsModel
import com.codesroots.goldencoupon.entites.category.AllCategoryModel
import com.codesroots.goldencoupon.entites.countries.CountryModel
import com.codesroots.goldencoupon.entites.coupons.CouponsModel
import com.codesroots.goldencoupon.entites.deals.DealsModel
import com.codesroots.goldencoupon.entites.fav.FavouritModel
import com.codesroots.goldencoupon.entites.forget.ForgetPasswordModelX
import com.codesroots.goldencoupon.entites.notification.NotificationModel
import com.codesroots.goldencoupon.entites.products.ProductsModel
import com.codesroots.goldencoupon.entites.staticpages.StaticPagesModel
import com.codesroots.goldencoupon.entites.used_coupons.UsedCouponModel
import com.codesroots.goldencoupon.entites.whatsapp.WhatsAppModel
import retrofit2.Response
import retrofit2.http.*


interface APIServices {


    @FormUrlEncoded
    @POST("brands.json")
    suspend fun getBrandsData(
        @Query("page") page: Int?,
        @Field("Filter[name]") cat_id: String?,

        ): BrandsModel


    @GET("categories/getAllCats.json")
    suspend fun getCategoryData(): AllCategoryModel

    @GET("brands/getall.json")
    suspend fun getAllBrands(): AllBrandsModel

    @GET("OffersSliders.json")
    suspend fun getDealsData(@Query("page") page: Int?): DealsModel

    @FormUrlEncoded
    @POST("products/index/{country_id}.json")
    suspend fun getProductData(
        @Path("country_id") country_id: Int?,
        @FieldMap fields: HashMap<String, String>?,
        ): ProductsModel

    @GET("products/index/{country_id}.json")
    suspend fun getProduct(
        @Path("country_id") country_id: Int?,
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
    ): Boolean

    @POST("FavouriteItems/Delete/{brand_id}.json")
    suspend fun deleteFavorite(
        @Path("brand_id") brand_id: Int?,
    ): Boolean


    @GET("notifications.json")
    suspend fun getNotifications(): Response<NotificationModel>

    ////////////// Authentication
    @POST("users/token.json")
    @Headers("Accept: Application/json", "cache-control: no-cache")
    suspend fun login(@Body loginModel: User?): Response<LoginModel>

    @POST("users/facebooklogin.json")
    @Headers("Accept: Application/json", "cache-control: no-cache")
    suspend fun loginByGoogle(@Body loginModel: User?): Response<LoginModel>
//

    @POST("users/add.json")
    @Headers("Accept: Application/json", "cache-control: no-cache")
    suspend fun register(@Body registerModel: User?): Response<LoginModel>

    @FormUrlEncoded
    @POST("Users/forgotpassword.json")
    @Headers("Accept: Application/json", "cache-control: no-cache")
    suspend fun forgetPassword(@Field("email") email: String?): Response<ForgetPasswordModelX>

    //////////////////////////////////////////////////////
    @GET("StaticPages.json")
    suspend fun getStaticPages(): StaticPagesModel

    @FormUrlEncoded
    @POST("UsedCoupons/add.json")
    @Headers("Accept: Application/json", "cache-control: no-cache")
    suspend fun usedCoupons(@Field("item_id") item_id: Int?): Response<UsedCouponModel>

    @GET("whats.json")
    suspend fun whatsApp(): Response<WhatsAppModel>


    //   http://goldencopons.codesroots.com/api/UsedCoupons/add.json
}