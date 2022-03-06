package com.codesroots.goldencoupon.repo.auth


import com.codesroots.goldencoupon.entites.auth.LoginModel
import com.codesroots.goldencoupon.entites.auth.User
import com.codesroots.goldencoupon.entites.forget.ForgetPasswordModelX
import retrofit2.Response


interface DataSource {

    suspend fun getLoginResponse(loginModel: User): Response<LoginModel>
    suspend fun getloginByGoogleResponse(loginModel: User): Response<LoginModel>
    suspend fun getRegisterModelResponse(registerModel: User): Response<LoginModel>
    suspend fun forgetPasswordResponse(email:String?): Response<ForgetPasswordModelX>

//
}