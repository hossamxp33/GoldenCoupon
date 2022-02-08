package com.codes_roots.golden_coupon.helper

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.codes_roots.golden_coupon.R
import com.codes_roots.golden_coupon.entites.products.Product
import com.codes_roots.golden_coupon.presentation.country_activity.CountryActivity
import com.codes_roots.golden_coupon.presentation.couponsfragment.CouponsFragment
import com.codes_roots.golden_coupon.presentation.mainactivity.MainActivity
import com.codes_roots.golden_coupon.presentation.web_view.WebViewActivity

class ClickHandler {


    fun switchToCouponsFragment(brandId: Int, context: Context) {
        context as MainActivity
        val fragment = CouponsFragment()
        val bundle = Bundle()
        bundle.putInt("brandId", brandId)
        fragment.arguments = bundle
        switchFragment(context, fragment)

    }

    fun setClipboard(context: Context, text: String) {
        context as MainActivity
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            val clipboard =
                context.getSystemService(Context.CLIPBOARD_SERVICE) as android.text.ClipboardManager
            clipboard.text = text
        } else {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Copied Text", text)
            clipboard.setPrimaryClip(clip)

        }

        Toast.makeText(context, context.getString(R.string.copied), Toast.LENGTH_SHORT).show()
    }

    fun switchFragment(context: Context, fragment: Fragment) {
        (context as MainActivity).supportFragmentManager.beginTransaction()
            .setCustomAnimations(0, 0, 0, 0)
            .replace(R.id.main_frame, fragment).addToBackStack(null).commit()
    }

    fun openWebView(context: Context,url:String?) {
        context as MainActivity

        if (!url.isNullOrEmpty()) {

            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra("url", url);

            (context as MainActivity).startActivity(intent)
        }
    }

    fun switchToMainActivity(context: Context,countryId:Int?){
         context as CountryActivity
        context.Pref.CountryId = countryId!!
        switchToActivity(context,MainActivity())

    }
        fun switchToActivity(context: Context, activity: AppCompatActivity) {

            val i = Intent(context, activity::class.java)
            (context).startActivity(i);

        }
    }
