package com.codes_roots.golden_coupon.presentation.splash


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.codes_roots.golden_coupon.R
import com.codes_roots.golden_coupon.helper.ClickHandler
import com.codes_roots.golden_coupon.helper.PreferenceHelper
import com.codes_roots.golden_coupon.helper.ResourceUtil
import com.codes_roots.golden_coupon.presentation.auth.RegisterActivity
import com.codes_roots.golden_coupon.presentation.chose_language.LanguageActivity
import com.codes_roots.golden_coupon.presentation.country_activity.CountryActivity
import com.codes_roots.golden_coupon.presentation.mainactivity.MainActivity

import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


class SplashScreen constructor(): AppCompatActivity() , HasAndroidInjector {

    private val SPLASH_DISPLAY_LENGTH = 3000 //splash screen will be shown for 2 seconds

    @Inject
    lateinit var Pref: PreferenceHelper

     override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this@SplashScreen)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        Handler().postDelayed({
            if (Pref.lang != ""){
             if (Pref.token != "")
              ClickHandler().switchToActivity(this,MainActivity())
             else
             ClickHandler().switchToActivity(this,RegisterActivity())

         ResourceUtil().changeLang(Pref.lang!!, this)

            }else{
                ClickHandler().switchToActivity(this, LanguageActivity())
            }

        }, SPLASH_DISPLAY_LENGTH.toLong())
    }

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

}