package com.codesroots.goldencoupon.presentation.menufragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.codesroots.goldencoupon.R
import com.codesroots.goldencoupon.databinding.StaticFragmentBinding
import com.codesroots.goldencoupon.entites.staticpages.StaticPagesItem
import com.codesroots.goldencoupon.helper.BaseApplication
import com.codesroots.goldencoupon.helper.ClickHandler
import com.codesroots.goldencoupon.helper.PreferenceHelper
import com.codesroots.goldencoupon.presentation.mainactivity.MainActivity

import javax.inject.Inject

class StaticFragment @Inject constructor
    (var contentData: StaticPagesItem) : Fragment() {

    @Inject
    lateinit var Pref: PreferenceHelper


    lateinit var view: StaticFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            BaseApplication.appComponent.inject(this)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        view = DataBindingUtil.inflate(inflater,
            R.layout.static_fragment, container, false)

        view.listener = ClickHandler()
        view.context = context as MainActivity

        view.data = contentData

        if (contentData != null)
            view.progress.isVisible = false





        return view.root
    }


}





