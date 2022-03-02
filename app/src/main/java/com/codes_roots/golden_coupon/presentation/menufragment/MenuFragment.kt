package com.codes_roots.golden_coupon.presentation.menufragment

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
import com.codes_roots.golden_coupon.R
import com.codes_roots.golden_coupon.databinding.MenuFragmentBinding
import com.codes_roots.golden_coupon.helper.BaseApplication
import com.codes_roots.golden_coupon.helper.ClickHandler
import com.codes_roots.golden_coupon.helper.PreferenceHelper
import com.codes_roots.golden_coupon.presentation.homefragment.mvi.UserError
import com.codes_roots.golden_coupon.presentation.mainactivity.MainActivity
import com.codes_roots.golden_coupon.presentation.menufragment.mvi.MainIntent
import com.codes_roots.golden_coupon.presentation.menufragment.mvi.StaticPagesViewModel

import kotlinx.android.synthetic.main.bottom_nav_content.*
import kotlinx.coroutines.flow.collect
import java.util.*
import javax.inject.Inject


open class MenuFragment @Inject constructor() : Fragment() {

    @Inject
    lateinit var pref: PreferenceHelper

    lateinit var staticPagesAdapter: StaticPagesAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel by viewModels<StaticPagesViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        //   childFragmentManager.fragmentFactory = fragmentFactory!!

        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            BaseApplication.appComponent.inject(this)
        }
    }

    private lateinit var view: MenuFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {

        view = DataBindingUtil.inflate(inflater, R.layout.menu_fragment, container, false)
        view.context = context as MainActivity
        view.listener = ClickHandler()

        view.myAccount.isVisible = pref.token.isNullOrEmpty()
        view.logout.isVisible = !pref.token.isNullOrEmpty()



//        if (pref.lang!!.contains("en")) {
//            view.rateApp.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_next, 0)
//            view.lang.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_next, 0)
//            view.myAccount.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_next, 0)
//            view.deals.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_next, 0)
//
//
//            //            chat.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_next, 0);
//        }
        // viewModel.intents.trySend(MainIntent.Initialize(viewModel.state.value!!.copy(progress = false)))

        myOrderRecycleView()

        getAllData()
        return view.root
    }


    fun getAllData() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect {
                if (it != null) {
                    if (it.error != null) {
                        it.error?.let { userError ->
                            when (userError) {
                                is UserError.InvalidId -> "Invalid id"
                                is UserError.NetworkError -> userError.throwable.message
                                UserError.ServerError -> "Server error"
                                UserError.Unexpected -> "Unexpected error"
                                is UserError.UserNotFound -> "User not found"
                                UserError.ValidationFailed -> "Validation failed"
                            }
                        }
                        viewModel.intents.trySend(MainIntent.ErrorDisplayed(it))
                    } else {

                        if (it.progress == true) {
                            viewModel.intents.send(MainIntent.Initialize(it))

                        } else {

                            if (it.data?.isNotEmpty() == true)

                                staticPagesAdapter.submitList(it.data!!)
                            view.progress.isVisible = it.progress!!


                        }
                    }
                }

            }
        }
    }

    fun myOrderRecycleView() {
        staticPagesAdapter = StaticPagesAdapter(requireContext())
        view.pagesRecycle.apply {
            layoutManager = LinearLayoutManager(
                context
            )
            adapter = staticPagesAdapter;
            isNestedScrollingEnabled = false
            setHasFixedSize(true)
        }
    }


}