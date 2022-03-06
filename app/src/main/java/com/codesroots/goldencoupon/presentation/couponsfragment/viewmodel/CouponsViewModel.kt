package com.codesroots.goldencoupon.presentation.couponsfragment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codesroots.goldencoupon.entites.coupons.CouponsModel
import com.codesroots.goldencoupon.entites.used_coupons.UsedCouponModel
import com.codesroots.goldencoupon.repo.brands.RemoteDataSource

import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.*
import javax.inject.Inject

class CouponsViewModel @Inject constructor(private val Datasources: RemoteDataSource,
) : ViewModel() {


    private var job: Job? = null
    var mCompositeDisposable = CompositeDisposable()

    var rateJob: Job? = null
    var CouponsLD: MutableLiveData<CouponsModel>? = null
    var UsedCouponsLD: MutableLiveData<UsedCouponModel>? = null

    val errorMessage = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()


    init {
        CouponsLD = MutableLiveData()
        UsedCouponsLD= MutableLiveData()
    }

    fun getUsedCoupons(item_id: Int?)
    {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = Datasources.getUsedCoupons(item_id!!)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    UsedCouponsLD?.postValue(response.body())

                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }

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