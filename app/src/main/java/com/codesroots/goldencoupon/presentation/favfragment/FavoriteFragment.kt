package com.codesroots.goldencoupon.presentation.favfragment

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
import com.codesroots.goldencoupon.databinding.FavoriteFragmentBinding
import com.codesroots.goldencoupon.entites.fav.FavoriteData
import com.codesroots.goldencoupon.helper.BaseApplication
import com.codesroots.goldencoupon.presentation.favfragment.adapter.FavoriteAdapter
import com.codesroots.goldencoupon.presentation.favfragment.mvi.FavViewModel
import com.codesroots.goldencoupon.presentation.favfragment.mvi.MainIntent
import com.codesroots.goldencoupon.presentation.homefragment.mvi.UserError
import com.codesroots.goldencoupon.presentation.mainactivity.MainActivity

import kotlinx.coroutines.flow.collect

import java.util.*
import javax.inject.Inject


open class FavoriteFragment @Inject constructor() : Fragment() {

    lateinit var favoriteAdapter: FavoriteAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel by viewModels<FavViewModel> { viewModelFactory }

    var data: ArrayList<FavoriteData>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        //   childFragmentManager.fragmentFactory = fragmentFactory!!
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            BaseApplication.appComponent.inject(this)
        }
    }

    private lateinit var view: FavoriteFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        view = DataBindingUtil.inflate(inflater, R.layout.favorite_fragment, container, false)


        //   view.searchLayout.listener = ClickHandler()

        view.context = context as MainActivity
        //  view.listener = ClickHandler()
        //  view.searchLayout.pref = (context as MainActivity).Pref






        myView()

        return view.root
    }



    fun myView() {

       getAllData()
        favoriteRecycleView()
    }



    fun favoriteRecycleView() {
        favoriteAdapter = FavoriteAdapter(requireContext(),viewModel,this)
        view.favRecycle.apply {
            layoutManager = LinearLayoutManager(context) // default orientation is vertical
            adapter = favoriteAdapter
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
                            viewModel.intents.send(MainIntent.Initialize(it))
                        } else {

                          favoriteAdapter.submitList(it.data!!.data)
                            data = it.data!!.data
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