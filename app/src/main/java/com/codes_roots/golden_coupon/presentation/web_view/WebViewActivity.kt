package com.codes_roots.golden_coupon.presentation.web_view

import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.web_view.*
import android.webkit.WebView
import android.view.View
import com.codes_roots.golden_coupon.R
import com.codes_roots.golden_coupon.di.WARN_MotionToast
import com.codes_roots.golden_coupon.helper.PreferenceHelper
import com.codes_roots.golden_coupon.helper.ResourceUtil
import com.codes_roots.golden_coupon.presentation.mainactivity.MainActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


class WebViewActivity : AppCompatActivity(), HasAndroidInjector {
    @Inject
    lateinit var preferenceHelper: PreferenceHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.web_view)
        webView.webViewClient = WebViewClient()
        webView.settings.setSupportZoom(true)
        webView.settings.javaScriptEnabled = true

        backbutton.setOnClickListener {
            finish()
        }

        val extras = intent.extras



        val url = extras?.getString("url")
        val url_en = extras?.getString("url_en")


        val webview = findViewById<View>(R.id.webView) as WebView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ResourceUtil().changeLang(preferenceHelper.lang!!, this)

        }
        webview.settings.javaScriptEnabled = true
        backbutton.setOnClickListener {

            onBackPressed()
        }

        if (preferenceHelper.lang!!.contains("ar") &&  !url.isNullOrEmpty())
            webview.loadUrl(url)
        else if (preferenceHelper.lang!!.contains("en") &&  !url_en.isNullOrEmpty())
            webview.loadUrl(url_en)

        else if (preferenceHelper.lang!!.contains("en") && !url.isNullOrEmpty())
            webview.loadUrl(url)

        else
            WARN_MotionToast("not available",this)

        webView.requestFocus();
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