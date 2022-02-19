package com.codes_roots.golden_coupon.repo.auth


import com.codes_roots.golden_coupon.data_layer.APIServices
import com.codes_roots.golden_coupon.entites.auth.LoginModel
import com.codes_roots.golden_coupon.entites.auth.User
import com.codes_roots.golden_coupon.entites.brandsmodel.BrandsModel
import com.codes_roots.golden_coupon.entites.countries.CountryModel
import com.codes_roots.golden_coupon.entites.coupons.CouponsModel
import com.codes_roots.golden_coupon.entites.deals.DealsModel
import com.codes_roots.golden_coupon.entites.fav.FavouritModel
import retrofit2.Response

import javax.inject.Inject


class AuthRemoteDataSource @Inject constructor(private val ApiService: APIServices) : DataSource {

    override suspend fun getLoginResponse(loginModel: User): Response<LoginModel> {
        return ApiService.login(loginModel)
    }

    override suspend fun getRegisterModelResponse(registermodel: User): Response<LoginModel> {
        return ApiService.register(registermodel)
    }

    override suspend fun forgetPasswordResponse(email: String?): Response<LoginModel> {
        return ApiService.forgetPassword(email)
    }



}