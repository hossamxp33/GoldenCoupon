package com.codes_roots.golden_coupon.helper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.widget.Toast
import com.codes_roots.golden_coupon.R
import com.codes_roots.golden_coupon.presentation.mainactivity.MainActivity
import java.util.*
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.content.ContextCompat
import java.lang.Exception
import java.net.URLEncoder
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.ContextCompat.startActivity

import android.R.id
import android.database.Cursor

import android.provider.ContactsContract
import androidx.core.content.ContextCompat.startActivity
import com.google.android.play.core.internal.e
import kotlin.Unit.toString
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.ContextCompat.startActivity

import android.telephony.PhoneNumberUtils

import android.content.ComponentName
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.ContextCompat.startActivity
import com.codes_roots.golden_coupon.presentation.ratefragment.RateFragment.Companion.TAG


class ResourceUtil {
    private var myLocale: Locale? = null
    fun getCurrentLanguage(context: Context): String? {
        val langPref = context.packageName + "App_Language"
        val prefs =
            context.getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE)
        return prefs.getString(langPref, "ar")
    }
    fun openWhatsApp(context: Context, number:String?, message: String?) {
        context as MainActivity
        try {

            val toNumber =
                "201102856010" // Replace with mobile phone number without +Sign or leading zeros, but with country code
            //Suppose your country is India and your phone number is “xxxxxxxxxx”, then you need to send “91xxxxxxxxxx”.
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("http://api.whatsapp.com/send?phone=$toNumber&text=$message")
            context.  startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
//        try {
//         val  newNumber = number!!.replace(" ", "").replace("+", "")
//            val sendIntent = Intent("android.intent.action.MAIN")
//
//            sendIntent.component = ComponentName("com.whatsapp", "com.whatsapp.Conversation")
//            sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(newNumber) + "@s.whatsapp.net")
//            sendIntent.putExtra(Intent.EXTRA_TEXT, message);
//            sendIntent.type = "text/plain"
//            context.startActivity(sendIntent)
//
//
////            val uri =
////                Uri.parse("https://api.whatsapp.com/send?phone=" + newNumber + "&text=" + message)
//
////            val sendIntent = Intent(Intent.ACTION_VIEW, uri)
////
////            context.startActivity(sendIntent)
//        } catch (e: Exception) {
//            Log.e(TAG, "ERROR_OPEN_MESSANGER$e")
//        }

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