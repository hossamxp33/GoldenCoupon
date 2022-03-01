package com.codes_roots.golden_coupon.presentation.splash


import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.codes_roots.golden_coupon.R
import com.codes_roots.golden_coupon.helper.ClickHandler
import com.codes_roots.golden_coupon.helper.PreferenceHelper
import com.codes_roots.golden_coupon.helper.ResourceUtil
import com.codes_roots.golden_coupon.presentation.chose_language.LanguageActivity
import com.codes_roots.golden_coupon.presentation.country_activity.CountryActivity
import com.codes_roots.golden_coupon.presentation.mainactivity.MainActivity

import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject






class SplashScreen @Inject constructor(): AppCompatActivity() , HasAndroidInjector {

    private val SPLASH_DISPLAY_LENGTH = 2000 //splash screen will be shown for 2 seconds

    @Inject
    lateinit var Pref: PreferenceHelper

     override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this@SplashScreen)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
      val lang =   Resources.getSystem().configuration.locale.language;

         if (lang.contains("ar"))
             img.setImageResource(R.drawable.splash_logo_ar)
         else
             img.setImageResource(R.drawable.splash_logo_english)

         Handler().postDelayed({
            if (Pref.lang != ""){
               // SUCCESS_MotionToast(Pref.lang.toString(),this)
                ResourceUtil().changeLang(Pref.lang!!, this)
              if (Pref.CountryId != 0){

                  ClickHandler().switchToActivity(this,MainActivity())

                }else {

                  ClickHandler().switchToActivity(this,CountryActivity())

              }




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