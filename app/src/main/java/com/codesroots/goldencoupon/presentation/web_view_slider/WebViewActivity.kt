package com.codesroots.goldencoupon.presentation.web_view_slider

import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.web_view.*
import android.webkit.WebView
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import com.codesroots.goldencoupon.R
import com.codesroots.goldencoupon.di.WARN_MotionToast
import com.codesroots.goldencoupon.helper.ClickHandler
import com.codesroots.goldencoupon.helper.PreferenceHelper
import com.codesroots.goldencoupon.helper.ResourceUtil
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.web_view.backbutton
import kotlinx.android.synthetic.main.web_view.webView
import kotlinx.android.synthetic.main.web_view.wraning
import kotlinx.android.synthetic.main.web_view_slider.*
import javax.inject.Inject


class WebViewSliderActivity : AppCompatActivity(), HasAndroidInjector {
    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.web_view_slider)
        webView.webViewClient = WebViewClient()
        webView.settings.setSupportZoom(true)
        webView.settings.javaScriptEnabled = true

        backbutton.setOnClickListener {
            finish()
        }

        val extras = intent.extras
        val code = extras?.getString("code")
        val descriptionText = extras?.getString("description")



        val url = extras?.getString("url")
        val url_en = extras?.getString("url_en")
        copy_button.text = code
        description.text = descriptionText

        copy_button.setOnClickListener {
           ClickHandler().setClipboard(this,code!!,code)
        }
        val webview = findViewById<View>(R.id.webView) as WebView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ResourceUtil().changeLang(preferenceHelper.lang!!, this)
        }
        webview.settings.javaScriptEnabled = true
        backbutton.setOnClickListener {

            onBackPressed()
        }
        wraning.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this).create()
            alertDialog.setTitle( this.getString(R.string.report))
            alertDialog.setMessage(this.getString(R.string.errorincoupon))
            alertDialog.setIcon(this.getDrawable(R.drawable.whatsapp))

            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, this.getString(R.string.yes)
            ) { dialog, which ->  ResourceUtil().openWhatsApp(this,
                "201068746570",  this.getString(R.string.problem)
            ) }

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, this.getString(R.string.no)
            ) { dialog, which -> dialog.dismiss() }
            alertDialog.show()

            val btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            val btnNegative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)

            val layoutParams = btnPositive.layoutParams as LinearLayout.LayoutParams
            layoutParams.weight = 10f
            btnPositive.layoutParams = layoutParams
            btnNegative.layoutParams = layoutParams
        }


        if (preferenceHelper.lang!!.contains("ar") &&  !url.isNullOrEmpty()){
            webview.loadUrl(url)
            logo.setImageResource(R.drawable.splash_logo_ar)
        } else if (preferenceHelper.lang!!.contains("en") &&  !url_en.isNullOrEmpty()){
            webview.loadUrl(url!!)
            logo.setImageResource(R.drawable.splash_logo_english)
        } else
            WARN_MotionToast("not available",this)

        webView.requestFocus()
        val webSettings = webView!!.getSettings()
        webSettings.javaScriptEnabled = true
        // Force links and redirects to open in the WebView instead of in a browser
        webView!!.webViewClient = WebViewClient()


    }
    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.getAction() === KeyEvent.ACTION_DOWN) {
            when (keyCode) {
                KeyEvent.KEYCODE_BACK -> {
                    if (webView.canGoBack()) {
                        webView.goBack()
                    } else {
                        finish()
                    }
                    return true
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }

}