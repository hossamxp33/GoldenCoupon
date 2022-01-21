package com.codes_roots.golden_coupon.di

import androidx.lifecycle.ViewModel
import com.codes_roots.golden_coupon.helper.ViewModelKey
import com.codes_roots.golden_coupon.presentation.homefragment.mvi.MainViewModel


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



}