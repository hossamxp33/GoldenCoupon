package com.codes_roots.golden_coupon.presentation.chose_language


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.codes_roots.golden_coupon.R
import com.codes_roots.golden_coupon.helper.ClickHandler
import com.codes_roots.golden_coupon.helper.PreferenceHelper
import com.codes_roots.golden_coupon.helper.ResourceUtil
import com.codes_roots.golden_coupon.presentation.auth.RegisterActivity
import com.codes_roots.golden_coupon.presentation.country_activity.adapter.CountriesAdapter
import com.codes_roots.golden_coupon.presentation.country_activity.viewmodel.CountryViewModel
import com.codes_roots.golden_coupon.presentation.couponsfragment.adapter.CouponsAdapter
import com.codes_roots.golden_coupon.presentation.homefragment.mvi.MainViewModel
import com.codes_roots.golden_coupon.presentation.mainactivity.MainActivity
import com.codes_roots.golden_coupon.presentation.splash.SplashScreen

import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.chose_language_activity.*
import kotlinx.android.synthetic.main.country_activity.*
import javax.inject.Inject


class LanguageActivity @Inject constructor(): AppCompatActivity() , HasAndroidInjector {


    @Inject
    lateinit var Pref: PreferenceHelper
 var index = 0
     override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this@LanguageActivity)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chose_language_activity)


         ar_layout.setOnClickListener {
             noColoredView()
             next.isEnabled = true
             coloredView(ar_layout)
             ResourceUtil().changeLang("ar", this)
             Pref.lang = "ar"


         }

         us_layout.setOnClickListener {
             noColoredView()
             next.isEnabled = true
             coloredView(us_layout)
             ResourceUtil().changeLang("en", this)
             Pref.lang = "en"
         }

         next.setOnClickListener {
             if (Pref.token!="")
                 ClickHandler().switchToActivity(this,SplashScreen())
             else
             ClickHandler().switchToActivity(this,RegisterActivity())
         }


    }

    private fun noColoredView() {
        whiteView(ar_layout)
        whiteView(us_layout)

    }

    private fun coloredView(view: View) {
        view.setBackgroundColor(ContextCompat
            .getColor(this, R.color.bottomNav))
    }
    private fun whiteView(view: View) {
        view.setBackgroundColor(ContextCompat
            .getColor(this, R.color.white))
    }

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

}