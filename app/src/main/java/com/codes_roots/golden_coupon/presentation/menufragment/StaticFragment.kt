package com.codes_roots.golden_coupon.presentation.menufragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.codes_roots.golden_coupon.R
import com.codes_roots.golden_coupon.databinding.StaticFragmentBinding
import com.codes_roots.golden_coupon.entites.staticpages.StaticPagesItem
import com.codes_roots.golden_coupon.helper.BaseApplication
import com.codes_roots.golden_coupon.helper.ClickHandler
import com.codes_roots.golden_coupon.helper.PreferenceHelper
import com.codes_roots.golden_coupon.presentation.mainactivity.MainActivity

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





