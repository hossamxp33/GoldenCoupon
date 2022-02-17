package com.codes_roots.golden_coupon.repo.brands


import com.codes_roots.golden_coupon.data_layer.APIServices
import com.codes_roots.golden_coupon.entites.brandsmodel.BrandsModel
import com.codes_roots.golden_coupon.entites.countries.CountryModel
import com.codes_roots.golden_coupon.entites.coupons.CouponsModel
import com.codes_roots.golden_coupon.entites.deals.DealsModel
import com.codes_roots.golden_coupon.entites.fav.FavouritModel
import com.codes_roots.golden_coupon.entites.staticpages.StaticPagesItem
import com.codes_roots.golden_coupon.entites.staticpages.StaticPagesModel
import retrofit2.Response

import javax.inject.Inject


class RemoteDataSource @Inject constructor(private val ApiService: APIServices) : DataSource {

    override suspend fun getBrandsResponse(page: Int?, filter: String): BrandsModel =
        runCatching { ApiService.getBrandsData(page,filter)}
            .getOrThrow()

    override suspend fun getDealsResponse(page: Int?): DealsModel =
        runCatching { ApiService.getDealsData(page)}
            .getOrElse { throw it }

    override suspend fun getCouponsResponse(brandid:Int): Response<CouponsModel> {
       return ApiService.getCouponsData(brandid)
    }

    override suspend fun getCountriesResponse(): Response<CountryModel> {
        return ApiService.getCountries()

    }

    override suspend fun getFavoritesResponse(): FavouritModel {
        return ApiService.getFavorite()

    }

    override suspend fun addFavorites(brand_id: Int, UserId: Int): Boolean {
        return ApiService.addFavorite(brand_id, UserId)

    }

    override suspend fun getStaticPages(): StaticPagesModel {
        return ApiService.getStaticPages()
    }

}