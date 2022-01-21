package com.codes_roots.golden_coupon.presentation.menufragment

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.lifecycleScope
import com.codes_roots.golden_coupon.R
import com.codes_roots.golden_coupon.databinding.HomeFragmentBinding
import com.codes_roots.golden_coupon.databinding.MenuFragmentBinding
import com.codes_roots.golden_coupon.databinding.OffersFragmentBinding
import com.codes_roots.golden_coupon.helper.BaseApplication
import com.codes_roots.golden_coupon.presentation.MainActivity

import kotlinx.android.synthetic.main.bottom_nav_content.*
import kotlinx.coroutines.flow.collect
import org.jetbrains.anko.support.v4.startActivityForResult
import java.util.*
import javax.inject.Inject


open class MenuFragment @Inject constructor() : Fragment(){


    override fun onCreate(savedInstanceState: Bundle?) {
        //   childFragmentManager.fragmentFactory = fragmentFactory!!

        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            BaseApplication.appComponent.inject(this)
        }
    }

    private lateinit var view: MenuFragmentBinding





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        view = DataBindingUtil.inflate(inflater, R.layout.menu_fragment, container, false)


     //   view.searchLayout.listener = ClickHandler()

        view.context = context as MainActivity
      //  view.listener = ClickHandler()
      //  view.searchLayout.pref = (context as MainActivity).Pref




        return view.root
    }











}