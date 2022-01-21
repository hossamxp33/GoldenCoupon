package com.codes_roots.golden_coupon.helper

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import com.codes_roots.golden_coupon.di.AppComponent
import com.codes_roots.golden_coupon.di.DaggerAppComponent

open class BaseApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return initializeDaggerComponent()
    }


    companion object {
        lateinit var appComponent: AppComponent
    }
    override fun onCreate() {
        super.onCreate()

        initDI()
    }

    private fun initDI() {
        appComponent = DaggerAppComponent.factory()
            .create(this)

    }

    open fun initializeDaggerComponent(): AppComponent {
        val component: AppComponent = DaggerAppComponent.factory()
            .create(this)
        component.inject(this)
        return component
    }


}