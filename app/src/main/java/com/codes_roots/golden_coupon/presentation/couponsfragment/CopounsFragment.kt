package com.codes_roots.golden_coupon.presentation.couponsfragment

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codes_roots.golden_coupon.R
import com.codes_roots.golden_coupon.databinding.CouponsFragmentBinding
import com.codes_roots.golden_coupon.databinding.OffersFragmentBinding
import com.codes_roots.golden_coupon.helper.BaseApplication
import com.codes_roots.golden_coupon.presentation.couponsfragment.adapter.CouponsAdapter
import com.codes_roots.golden_coupon.presentation.couponsfragment.viewmodel.CouponsViewModel
import com.codes_roots.golden_coupon.presentation.homefragment.mvi.UserError
import com.codes_roots.golden_coupon.presentation.mainactivity.MainActivity
import com.codes_roots.golden_coupon.presentation.productoffersfragment.adapter.category.CategoryAdapter
import com.codes_roots.golden_coupon.presentation.productoffersfragment.adapter.product.ProductsAdapter
import com.codes_roots.golden_coupon.presentation.productoffersfragment.mvi.MainIntent
import com.codes_roots.golden_coupon.presentation.productoffersfragment.mvi.ProductsViewModel
import kotlinx.coroutines.flow.collect

import java.util.*
import javax.inject.Inject


open class CouponsFragment @Inject constructor() : Fragment() {
    lateinit var couponsAdapter: CouponsAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel by viewModels<CouponsViewModel> { viewModelFactory }

    var brandImage :String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        //   childFragmentManager.fragmentFactory = fragmentFactory!!
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            BaseApplication.appComponent.inject(this)
        }
    }

    private lateinit var view: CouponsFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {

        view = DataBindingUtil.inflate(inflater, R.layout.coupons_fragment, container, false)


        //   view.searchLayout.listener = ClickHandler()

        view.context = context as MainActivity
        //  view.listener = ClickHandler()
        //  view.searchLayout.pref = (context as MainActivity).Pref

        viewModel.getCoupons(requireArguments().getInt("brandId"))
        brandImage=     requireArguments().getString("brand_image")
        viewModel.CouponsLD!!.observe(requireActivity(), androidx.lifecycle.Observer {
            couponsAdapter.submitList(it.items)
            view.progress.isVisible = false
        })
        myView()

        return view.root
    }


    fun myView() {


        couponsRecycleView()
    }

    fun couponsRecycleView() {
        couponsAdapter = CouponsAdapter(requireContext(),this,viewModel)
        view.couponsRecycleView.apply {
            layoutManager = LinearLayoutManager(
                context
            )
            adapter = couponsAdapter;
            isNestedScrollingEnabled = false
            setHasFixedSize(true)
        }

    }


    fun stopLoadingShimmer() {
//        shimmer_view_container?.setVisibility(View.GONE)
//        shimmer_view_container?.stopShimmerAnimation()
    }

}