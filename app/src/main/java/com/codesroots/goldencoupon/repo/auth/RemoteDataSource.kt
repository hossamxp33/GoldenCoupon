package com.codesroots.goldencoupon.repo.auth


import com.codesroots.goldencoupon.data_layer.APIServices
import com.codesroots.goldencoupon.entites.auth.LoginModel
import com.codesroots.goldencoupon.entites.auth.User
import com.codesroots.goldencoupon.entites.forget.ForgetPasswordModelX
import retrofit2.Response

import javax.inject.Inject


class AuthRemoteDataSource @Inject constructor(private val ApiService: APIServices) : DataSource {

    override suspend fun getLoginResponse(loginModel: User): Response<LoginModel> {
        return ApiService.login(loginModel)
    }
    override suspend fun getloginByGoogleResponse(loginModel: User): Response<LoginModel> {
        return ApiService.loginByGoogle(loginModel)
    }

    override suspend fun getRegisterModelResponse(registermodel: User): Response<LoginModel> {
        return ApiService.register(registermodel)
    }

    override suspend fun forgetPasswordResponse(email: String?): Response<ForgetPasswordModelX> {
        return ApiService.forgetPassword(email)
    }



}