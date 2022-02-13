package com.codes_roots.golden_coupon.helper

import android.preference.PreferenceManager
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import com.codes_roots.golden_coupon.di.AppComponent
import com.codes_roots.golden_coupon.di.DaggerAppComponent
import java.net.URISyntaxException
import java.util.*

open class BaseApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return initializeDaggerComponent()
    }


    companion object {
        lateinit var appComponent: AppComponent
    }
    override fun onCreate() {
        super.onCreate()
        var Pref = PreferenceHelper(this)

        try {

            val language =  Locale.getDefault().language

            Pref.lang = language

        } catch (e: URISyntaxException) {
            throw RuntimeException(e)
        }
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