package com.codes_roots.golden_coupon.repo.brands


import com.codes_roots.golden_coupon.entites.brandsmodel.BrandsModel
import com.codes_roots.golden_coupon.entites.countries.CountryModel
import com.codes_roots.golden_coupon.entites.coupons.CouponsModel
import com.codes_roots.golden_coupon.entites.deals.DealsModel
import com.codes_roots.golden_coupon.entites.fav.FavouritModel
import com.codes_roots.golden_coupon.entites.notification.NotificationModel
import com.codes_roots.golden_coupon.entites.staticpages.StaticPagesItem
import com.codes_roots.golden_coupon.entites.staticpages.StaticPagesModel
import com.codes_roots.golden_coupon.entites.used_coupons.UsedCouponModel
import com.codes_roots.golden_coupon.entites.whatsapp.WhatsAppModel
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