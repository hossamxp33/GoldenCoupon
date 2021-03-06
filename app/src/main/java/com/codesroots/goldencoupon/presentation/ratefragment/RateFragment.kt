package com.codesroots.goldencoupon.presentation.ratefragment

import android.content.ActivityNotFoundException
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import com.codesroots.goldencoupon.R
import com.codesroots.goldencoupon.databinding.RateFragmentBinding

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject


class RateFragment @Inject constructor() : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "TownBottomSheetDialogFragment"
    }

    @Inject

    lateinit var view: RateFragmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        view = DataBindingUtil.inflate(inflater,
            R.layout.rate_fragment, container, false)
        view.webVieww.webViewClient = WebViewClient()
        view.webVieww.settings.setSupportZoom(true)
        view.webVieww.settings.javaScriptEnabled = true



        val webview =view.webVieww as WebView

        webview.requestFocus();
        val appPackageName ="com.codesroots.cityrolls"  // getPackageName() from Context or Activity object
        val uri: Uri = Uri.parse("market://details?id=$appPackageName")
        val webSettings = webview!!.getSettings()
        webview.loadUrl(uri.toString())

        try {
            webview.loadUrl("https://play.google.com/store/apps/details?id=com.codesroots.cityrolls")

        } catch (anfe: ActivityNotFoundException) {
            webview.loadUrl("https://play.google.com/store/apps/details?id=$appPackageName")
        }

        webSettings.setJavaScriptEnabled(true)
        // Force links and redirects to open in the WebView instead of in a browser
        webview!!.setWebViewClient(WebViewClient())

   //     ClickHandler().rateApp(context as MainActivity)

        return view.root
    }


}