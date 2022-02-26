package com.codes_roots.golden_coupon.presentation.web_view

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.web_view.*
import android.webkit.WebView
import android.view.View
import com.codes_roots.golden_coupon.R


class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.web_view)
        webView.webViewClient = WebViewClient()
        webView.settings.setSupportZoom(true)
        webView.settings.javaScriptEnabled = true


        val extras = intent.extras


        val url = extras?.getString("url")


        val webview = findViewById<View>(R.id.webView) as WebView

        webview.settings.javaScriptEnabled = true
        back.setOnClickListener {
            onBackPressed()
        }
        webview.loadUrl(url!!)
        webView.requestFocus();
        val webSettings = webView!!.getSettings()
        webSettings.javaScriptEnabled = true
        // Force links and redirects to open in the WebView instead of in a browser
        webView!!.webViewClient = WebViewClient()
    }
}