package com.codesroots.goldencoupon.repo.brands


import com.codesroots.goldencoupon.data_layer.APIServices
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

import javax.inject.Inject


class RemoteDataSource @Inject constructor(private val ApiService: APIServices) : DataSource {

    override suspend fun getBrandsResponse(page: Int?, filter: String): BrandsModel =
        runCatching { ApiService.getBrandsData(page, filter) }
            .getOrThrow()




    override suspend fun getDealsResponse(page: Int?): DealsModel =
        runCatching { ApiService.getDealsData(page) }
            .getOrElse { throw it }

    override suspend fun getCouponsResponse(brandid: Int): Response<CouponsModel> {
        return ApiService.getCouponsData(brandid)
    }

    override suspend fun getwhatsApp(): Response<WhatsAppModel> {
        return ApiService.whatsApp()

    }


    override suspend fun getCountriesResponse(): Response<CountryModel> {
        return ApiService.getCountries()

    }

    override suspend fun getFavoritesResponse(): FavouritModel {
        return ApiService.getFavorite()

    }

    override suspend fun addFavorites(brand_id: Int): Boolean {
        return ApiService.addFavorite(brand_id)

    }

    override suspend fun deleteFavorite(brand_id: Int): Boolean {
        return ApiService.deleteFavorite(brand_id)

    }


    override suspend fun getStaticPages(): StaticPagesModel {
        return ApiService.getStaticPages()
    }

    override suspend fun getUsedCoupons(item_id: Int): Response<UsedCouponModel> {
        return ApiService.usedCoupons(item_id)

    }



    override suspend fun getNotifications(): Response<NotificationModel> {
        return ApiService.getNotifications()
    }


}