package com.codes_roots.golden_coupon.di

import androidx.lifecycle.ViewModel
import com.codes_roots.golden_coupon.helper.ViewModelKey
import com.codes_roots.golden_coupon.presentation.country_activity.viewmodel.CountryViewModel
import com.codes_roots.golden_coupon.presentation.couponsfragment.viewmodel.CouponsViewModel
import com.codes_roots.golden_coupon.presentation.favfragment.mvi.FavViewModel
import com.codes_roots.golden_coupon.presentation.homefragment.mvi.MainViewModel
import com.codes_roots.golden_coupon.presentation.productoffersfragment.mvi.ProductsViewModel


import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
Created by Prokash Sarkar on Tue, January 19, 2021
 **/

@Module
interface MainModule {
//
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductsViewModel::class)
    fun bindProductsViewModel(mainViewModel: ProductsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CouponsViewModel::class)
    fun bindCouponsViewModel(mainViewModel: CouponsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CountryViewModel::class)
    fun bindCountryViewModel(mainViewModel: CountryViewModel): ViewModel
    @Binds
    @IntoMap
    @ViewModelKey(FavViewModel::class)
    fun bindFavViewModel(mainViewModel: FavViewModel): ViewModel



}