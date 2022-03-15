package com.codesroots.goldencoupon.helper

import com.codesroots.goldencoupon.di.ActivityScope
import com.codesroots.goldencoupon.di.MainModule
import com.codesroots.goldencoupon.presentation.auth.RegisterActivity
import com.codesroots.goldencoupon.presentation.chose_language.LanguageActivity
import com.codesroots.goldencoupon.presentation.country_activity.CountryActivity
import com.codesroots.goldencoupon.presentation.mainactivity.MainActivity
import com.codesroots.goldencoupon.presentation.splash.SplashScreen
import com.codesroots.goldencoupon.presentation.web_view.WebViewActivity
import com.codesroots.goldencoupon.presentation.web_view_slider.WebViewSliderActivity

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

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainModule::class, FragmentFactoryModule::class])
    fun contributeWebViewSliderActivity(): WebViewSliderActivity


//    @ContributesAndroidInjector(modules = [MainModule::class, FragmentFactoryModule::class])
//    fun contributeLStaticPagesActivity(): StaticPagesActivity


}