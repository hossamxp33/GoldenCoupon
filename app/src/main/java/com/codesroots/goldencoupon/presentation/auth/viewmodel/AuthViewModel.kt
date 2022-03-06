package com.codesroots.goldencoupon.presentation.auth.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codesroots.goldencoupon.entites.auth.LoginModel
import com.codesroots.goldencoupon.entites.auth.User
import com.codesroots.goldencoupon.entites.forget.ForgetPasswordModelX
import com.codesroots.goldencoupon.helper.PreferenceHelper
import com.codesroots.goldencoupon.repo.auth.AuthRemoteDataSource

import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.*
import javax.inject.Inject

class AuthViewModel @Inject constructor(private val Datasources: AuthRemoteDataSource,
) : ViewModel() {

    @Inject
    lateinit var Pref: PreferenceHelper
    private var job: Job? = null
    var mCompositeDisposable = CompositeDisposable()
    var processVisibility = MutableLiveData(false)

    var rateJob: Job? = null
    var authLD: MutableLiveData<LoginModel>? = null

    var  forgetPasswordLD: MutableLiveData<ForgetPasswordModelX>? = null

    val errorMessage = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()


    init {
        authLD = MutableLiveData()

        forgetPasswordLD= MutableLiveData()
    }


//    //authentication
//    fun facebookLogin(facebookModel: User?) {
//        job = CoroutineScope(Dispatchers.IO).launch {
//            val response = Datasources.facebookResponse(facebookModel!!)
//            withContext(Dispatchers.Main) {
//                if (response.isSuccessful) {
//                    authLD?.postValue(response.body())
//
//                } else {
//                    onError("Error : ${response.message()} ")
//                }
//            }
//        }
//
//    }
    //authentication
    fun login(loginModel: User?) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = Datasources.getLoginResponse(loginModel!!)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    authLD?.postValue(response.body())

                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }

    }    fun loginByGoogleResponse(loginModel: User?) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = Datasources.getloginByGoogleResponse(loginModel!!)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    authLD?.postValue(response.body())

                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }

    }


    fun register(registerModel: User?) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = Datasources.getRegisterModelResponse(registerModel!!)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    authLD?.postValue(response.body())

                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }

    }    fun forgetPassword(email: String?) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = Datasources.forgetPasswordResponse(email!!)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    forgetPasswordLD?.postValue(response.body())

                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }

    }




    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        mCompositeDisposable.dispose()
        mCompositeDisposable.clear()
        rateJob?.cancel()
    }
}