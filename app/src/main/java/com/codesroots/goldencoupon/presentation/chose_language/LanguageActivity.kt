package com.codesroots.goldencoupon.presentation.chose_language


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.codesroots.goldencoupon.R
import com.codesroots.goldencoupon.helper.ClickHandler
import com.codesroots.goldencoupon.helper.PreferenceHelper
import com.codesroots.goldencoupon.helper.ResourceUtil
import com.codesroots.goldencoupon.presentation.country_activity.CountryActivity
import com.codesroots.goldencoupon.presentation.splash.SplashScreen

import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.chose_language_activity.*
import kotlinx.android.synthetic.main.country_activity.*
import javax.inject.Inject


class LanguageActivity @Inject constructor() : AppCompatActivity(), HasAndroidInjector {


    @Inject
    lateinit var Pref: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this@LanguageActivity)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chose_language_activity)

        ar_layout.setOnClickListener {
            noColoredView()
            next.isEnabled = true
            next.setText("التالي")
            coloredView(ar_layout)
            ResourceUtil().changeLang("ar", this)
            Pref.lang = "ar"


        }

        us_layout.setOnClickListener {
            noColoredView()
            next.isEnabled = true
            next.setText("next")

            coloredView(us_layout)
            ResourceUtil().changeLang("en", this)
            Pref.lang = "en"
        }

        next.setOnClickListener {
            if (Pref.CountryId == 0)
                ClickHandler().switchToActivity(this, CountryActivity())
            else
                ClickHandler().switchToActivity(this, SplashScreen())
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