package com.codesroots.goldencoupon.presentation.country_activity.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codesroots.goldencoupon.entites.countries.CountryModel
import com.codesroots.goldencoupon.repo.brands.RemoteDataSource

import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.*
import javax.inject.Inject

class CountryViewModel @Inject constructor(private val Datasources: RemoteDataSource,
) : ViewModel() {


    private var job: Job? = null
    var mCompositeDisposable = CompositeDisposable()

    var rateJob: Job? = null
    var CountryLD: MutableLiveData<CountryModel>? = null

    val errorMessage = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()


    init {
        CountryLD = MutableLiveData()
    }




    fun getCountriesData() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = Datasources.getCountriesResponse()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    CountryLD?.postValue(response.body())

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