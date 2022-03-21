package com.codesroots.goldencoupon.presentation.dealsfragment


import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible

import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.codesroots.goldencoupon.R
import com.codesroots.goldencoupon.databinding.DealsFragmentBinding

import com.codesroots.goldencoupon.helper.BaseApplication
import com.codesroots.goldencoupon.presentation.dealsfragment.adapter.DealsAdapter
import com.codesroots.goldencoupon.presentation.dealsfragment.mvi.DealsViewModel
import com.codesroots.goldencoupon.presentation.dealsfragment.mvi.MainIntent

import com.codesroots.goldencoupon.presentation.homefragment.mvi.UserError
import com.codesroots.goldencoupon.presentation.mainactivity.MainActivity
import kotlinx.android.synthetic.main.bottom_nav_content.*

import kotlinx.android.synthetic.main.deals_fragment.*
import kotlinx.android.synthetic.main.menu_fragment.*
import kotlinx.coroutines.flow.collect

import java.util.*
import javax.inject.Inject


open class DealsFragment @Inject constructor() : Fragment() {

    lateinit var dealsAdapter: DealsAdapter



    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel by viewModels<DealsViewModel> { viewModelFactory }


    override fun onCreate(savedInstanceState: Bundle?) {
        //   childFragmentManager.fragmentFactory = fragmentFactory!!
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            BaseApplication.appComponent.inject(this)
        }
    }

    private lateinit var view: DealsFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        view = DataBindingUtil.inflate(inflater, R.layout.deals_fragment, container, false)


        //   view.searchLayout.listener = ClickHandler()

        view.context = context as MainActivity
        //  view.listener = ClickHandler()
        //  view.searchLayout.pref = (context as MainActivity).Pref



        (context as MainActivity).bottom_nav_bar.menu.getItem(2).isChecked = true



        myView()

        return view.root
    }



    fun myView() {

       getAllData()
        favoriteRecycleView()
    }



    fun favoriteRecycleView() {
        dealsAdapter = DealsAdapter(requireContext())
        view.dealsRecycle.apply {
            layoutManager = LinearLayoutManager(context) // default orientation is vertical
            adapter = dealsAdapter
            isNestedScrollingEnabled = false
            setHasFixedSize(true)

        }

    }

    fun getAllData() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect {
                if (it != null) {
                    if (it.error != null) {
                        it.error?.let {
                            when (it) {
                                is UserError.InvalidId -> "Invalid id"
                                is UserError.NetworkError -> it.throwable.message
                                UserError.ServerError -> "Server error"
                                UserError.Unexpected -> "Unexpected error"
                                is UserError.UserNotFound -> "User not found"
                                UserError.ValidationFailed -> "Validation failed"
                            }
                        }
                        viewModel.intents.send(MainIntent.ErrorDisplayed(it))
                    } else {
                        if (it.progress == true) {
                           view.progress.isVisible = it.progress
                            viewModel.intents.send(MainIntent.Initialize(it,1))
                        } else {

                          dealsAdapter.submitList(it.data!!.data)
                     //       productsAdapter.submitList(it.filteredData)
//
//
                       view.progress.isVisible = false

                        }

                    }
                }
            }

        }
    }


    fun stopLoadingShimmer() {
//        shimmer_view_container?.setVisibility(View.GONE)
//        shimmer_view_container?.stopShimmerAnimation()
    }

}