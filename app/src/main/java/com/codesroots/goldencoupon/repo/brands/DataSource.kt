package com.codesroots.goldencoupon.repo.brands


import com.codesroots.goldencoupon.entites.brandsmodel.BrandsModel
import com.codesroots.goldencoupon.entites.countries.CountryModel
import com.codesroots.goldencoupon.entites.coupons.CouponsModel
import com.codesroots.goldencoupon.entites.deals.DealsModel
import com.codesroots.goldencoupon.entites.fav.FavouritModel
import com.codesroots.goldencoupon.entites.notification.NotificationModel
import com.codesroots.goldencoupon.entites.staticpages.StaticPagesModel
import com.codesroots.goldencoupon.entites.used_coupons.UsedCouponModel
import com.codesroots.goldencoupon.entites.whatsapp.WhatsAppModel
import retrofit2.Response


interface DataSource {

    suspend fun getBrandsResponse(page: Int?, filter: String): BrandsModel
    suspend fun getDealsResponse(page: Int?): DealsModel

    suspend fun getCouponsResponse(brandid: Int): Response<CouponsModel>


    suspend fun getCountriesResponse(): Response<CountryModel>
    suspend fun getFavoritesResponse(): FavouritModel
    suspend fun addFavorites(brand_id: Int): Boolean
    suspend fun deleteFavorite(brand_id: Int): Boolean

    suspend fun getStaticPages(): StaticPagesModel
    suspend fun getUsedCoupons(item_id:Int): Response<UsedCouponModel>
    suspend fun getNotifications(): Response<NotificationModel>
    suspend fun getwhatsApp(): Response<WhatsAppModel>

//
}