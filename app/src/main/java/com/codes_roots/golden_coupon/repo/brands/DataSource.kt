package com.codes_roots.golden_coupon.repo.brands


import com.codes_roots.golden_coupon.entites.brandsmodel.BrandsModel
import com.codes_roots.golden_coupon.entites.countries.CountryModel
import com.codes_roots.golden_coupon.entites.coupons.CouponsModel
import com.codes_roots.golden_coupon.entites.deals.DealsModel
import com.codes_roots.golden_coupon.entites.fav.FavouritModel
import retrofit2.Response


interface DataSource {

    suspend fun getBrandsResponse(page: Int?): BrandsModel
    suspend fun getDealsResponse(page: Int?): DealsModel

    suspend fun getCouponsResponse(brandid: Int): Response<CouponsModel>


    suspend fun getCountriesResponse(): Response<CountryModel>
    suspend fun getFavoritesResponse(): FavouritModel
    suspend fun addFavorites(brand_id: Int, UserId: Int): Boolean

//
}