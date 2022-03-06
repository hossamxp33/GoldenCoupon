package com.codesroots.goldencoupon.di

import androidx.lifecycle.ViewModel
import com.codesroots.goldencoupon.helper.ViewModelKey
import com.codesroots.goldencoupon.presentation.auth.viewmodel.AuthViewModel
import com.codesroots.goldencoupon.presentation.country_activity.viewmodel.CountryViewModel
import com.codesroots.goldencoupon.presentation.couponsfragment.viewmodel.CouponsViewModel
import com.codesroots.goldencoupon.presentation.dealsfragment.mvi.DealsViewModel
import com.codesroots.goldencoupon.presentation.favfragment.mvi.FavViewModel
import com.codesroots.goldencoupon.presentation.homefragment.mvi.MainViewModel
import com.codesroots.goldencoupon.presentation.menufragment.mvi.StaticPagesViewModel
import com.codesroots.goldencoupon.presentation.notificationfragment.viewmodel.NotificationViewModel
import com.codesroots.goldencoupon.presentation.productoffersfragment.mvi.ProductsViewModel


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

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    fun bindAuthViewModel(mainViewModel: AuthViewModel): ViewModel
    @Binds
    @IntoMap
    @ViewModelKey(DealsViewModel::class)
    fun bindDealsViewModel(mainViewModel: DealsViewModel): ViewModel
    @Binds
    @IntoMap
    @ViewModelKey(StaticPagesViewModel::class)
    fun bindStaticPagesViewModel(mainViewModel: StaticPagesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NotificationViewModel::class)
    fun bindNotificationViewModel(mainViewModel: NotificationViewModel): ViewModel



}