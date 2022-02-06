package com.codes_roots.golden_coupon.presentation.couponsfragment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codes_roots.golden_coupon.entites.coupons.CouponsModel
import com.codes_roots.golden_coupon.repo.brands.RemoteDataSource

import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.*
import javax.inject.Inject

class CouponsViewModel @Inject constructor(private val Datasources: RemoteDataSource,
) : ViewModel() {


    private var job: Job? = null
    var mCompositeDisposable = CompositeDisposable()

    var rateJob: Job? = null
    var CouponsLD: MutableLiveData<CouponsModel>? = null

    val errorMessage = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()


    init {
        CouponsLD = MutableLiveData()
    }




    fun getCoupons(brandid:Int?) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = Datasources.getCouponsResponse(brandid!!)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    CouponsLD?.postValue(response.body())

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