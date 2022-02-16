package com.codes_roots.golden_coupon.helper

import android.os.Build
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
        var pref = PreferenceHelper(this)

        try {
//            val config = resources.configuration
//            val lang = "ar" // your language code
//            val locale = Locale(lang)
//            Locale.setDefault(locale)
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
//                config.setLocale(locale)
//            else
//                config.locale = locale
//
//            pref.lang = lang
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
//                createConfigurationContext(config)
//            resources.updateConfiguration(config, resources.displayMetrics)
//

            if (ResourceUtil().getCurrentLanguage(this)!!.contains("en")) {
                ResourceUtil().changeLang("en", this)
                pref.lang = "en"

            } else {
                ResourceUtil().changeLang("ar", this)
                pref.lang = "ar"

            }
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