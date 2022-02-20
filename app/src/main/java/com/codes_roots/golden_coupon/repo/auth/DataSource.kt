package com.codes_roots.golden_coupon.repo.auth


import com.codes_roots.golden_coupon.entites.auth.LoginModel
import com.codes_roots.golden_coupon.entites.auth.User
import com.codes_roots.golden_coupon.entites.brandsmodel.BrandsModel
import com.codes_roots.golden_coupon.entites.countries.CountryModel
import com.codes_roots.golden_coupon.entites.coupons.CouponsModel
import com.codes_roots.golden_coupon.entites.deals.DealsModel
import com.codes_roots.golden_coupon.entites.fav.FavouritModel
import com.codes_roots.golden_coupon.entites.forget.ForgetPasswordModelX
import retrofit2.Response


interface DataSource {

    suspend fun getLoginResponse(loginModel: User): Response<LoginModel>
    suspend fun getRegisterModelResponse(registerModel: User): Response<LoginModel>
    suspend fun forgetPasswordResponse(email:String?): Response<ForgetPasswordModelX>

//
}