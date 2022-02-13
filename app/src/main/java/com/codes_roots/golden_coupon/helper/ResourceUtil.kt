package com.codes_roots.golden_coupon.helper

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
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
        if (lang.equals("", ignoreCase = true)) return
        myLocale = Locale(lang)
        Locale.setDefault(myLocale)
        val config = Configuration()
        config.locale = myLocale
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
        saveLocale(lang, context)
    }


    fun saveLocale(lang: String?, context: Context) {
        val langPref = context.packageName + "App_Language"
        val prefs = context.getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(langPref, lang)
        editor.commit()

    }





}