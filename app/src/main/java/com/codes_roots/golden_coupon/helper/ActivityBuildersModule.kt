package com.codes_roots.golden_coupon.helper

import com.codes_roots.golden_coupon.di.ActivityScope
import com.codes_roots.golden_coupon.di.MainModule
import com.codes_roots.golden_coupon.entites.staticpages.StaticPagesModel
import com.codes_roots.golden_coupon.presentation.auth.RegisterActivity
import com.codes_roots.golden_coupon.presentation.chose_language.LanguageActivity
import com.codes_roots.golden_coupon.presentation.country_activity.CountryActivity
import com.codes_roots.golden_coupon.presentation.mainactivity.MainActivity
import com.codes_roots.golden_coupon.presentation.splash.SplashScreen
import com.codes_roots.golden_coupon.presentation.web_view.WebViewActivity

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

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainModule::class, FragmentFactoryModule::class])
    fun contributeSplashScreen(): SplashScreen

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainModule::class, FragmentFactoryModule::class])
    fun contributeCountryActivity(): CountryActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainModule::class, FragmentFactoryModule::class])
    fun contributeRegisterActivity(): RegisterActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainModule::class, FragmentFactoryModule::class])
    fun contributeLanguageActivity(): LanguageActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainModule::class, FragmentFactoryModule::class])
    fun contributeWebViewActivity(): WebViewActivity


//    @ContributesAndroidInjector(modules = [MainModule::class, FragmentFactoryModule::class])
//    fun contributeLStaticPagesActivity(): StaticPagesActivity


}