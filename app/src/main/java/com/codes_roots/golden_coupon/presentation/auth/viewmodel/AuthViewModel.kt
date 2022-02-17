package com.codes_roots.golden_coupon.presentation.auth.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codes_roots.golden_coupon.entites.auth.LoginModel
import com.codes_roots.golden_coupon.entites.auth.User
import com.codes_roots.golden_coupon.helper.PreferenceHelper
import com.codes_roots.golden_coupon.repo.auth.AuthRemoteDataSource

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

    val errorMessage = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()


    init {
        authLD = MutableLiveData()
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

    }
//getRegisterResponse
//fun register(registerModel: User?) {
//    job = CoroutineScope(Dispatchers.IO).launch {
//        val response = Datasources.getRegisterResponse(registerModel!!)
//        withContext(Dispatchers.Main) {
//            if (response.isSuccessful) {
//                authLD?.postValue(response.body())
//
//            } else {
//                onError("Error : ${response.message()} ")
//            }
//        }
//    }
//
//}



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