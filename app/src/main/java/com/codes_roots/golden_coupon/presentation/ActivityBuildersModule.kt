package com.codes_roots.golden_coupon.presentation

import com.codes_roots.golden_coupon.di.ActivityScope
import com.codes_roots.golden_coupon.di.MainModule
import com.codes_roots.golden_coupon.helper.FragmentFactoryModule

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
Created by Prokash Sarkar on Tue, January 19, 2021
 **/

@Module
interface ActivityBuildersModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainModule::class, FragmentFactoryModule::class])
    fun contributeMainActivity(): MainActivity




}