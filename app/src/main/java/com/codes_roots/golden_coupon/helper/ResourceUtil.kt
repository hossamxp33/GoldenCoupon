package com.codes_roots.golden_coupon.helper

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import java.util.*

class ResourceUtil {
    private var myLocale: Locale? = null
    fun getCurrentLanguage(context: Context): String? {
        val langPref = context.packageName + "App_Language"
        val prefs =
            context.getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE)
        return prefs.getString(langPref, "ar")
    }


    fun changeLang(lang: String, context: Context) {
        val config = context.resources.configuration
        val locale = Locale(lang)
        Locale.setDefault(locale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            config.setLocale(locale)
        else
            config.locale = locale

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            context. createConfigurationContext(config)
        context. resources.updateConfiguration(config, context.resources.displayMetrics)


    }


    fun saveLocale(lang: String?, context: Context) {
        val langPref = context.packageName + "App_Language"
        val prefs = context.getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(langPref, lang)
        editor.commit()

    }





}