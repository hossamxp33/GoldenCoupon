package com.codes_roots.golden_coupon.presentation.notificationfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.codes_roots.golden_coupon.R
import com.codes_roots.golden_coupon.databinding.NotificaionFragmentBinding
import com.codes_roots.golden_coupon.helper.BaseApplication
import com.codes_roots.golden_coupon.helper.ClickHandler

import kotlinx.coroutines.flow.collect
import javax.inject.Inject


open class NotificationFragment @Inject constructor() : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

 //   private val viewModel by viewModels<NotificationViewModel> { viewModelFactory }

  //  lateinit var notificationAdapter: NotificationAdapter
    private lateinit var view: NotificaionFragmentBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null)  {
            BaseApplication.appComponent.inject(this)
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        view = DataBindingUtil.inflate(inflater,
                R.layout.notificaion_fragment, container, false)

                          view.progress.isVisible = false


//        getAllData()
//        notificationsRecycleView()

        return view.root
    }


//    fun notificationsRecycleView() {
//        notificationAdapter = NotificationAdapter(viewModel, requireContext())
//        view.notificationRecycle.apply {
//            adapter = notificationAdapter;
//            isNestedScrollingEnabled = false
//            setHasFixedSize(true)
//        }
//    }

//
//    fun getAllData() {
//        lifecycleScope.launchWhenStarted {
//            viewModel.state.collect {
//                if (it != null) {
//
//                    if (it.error != null) {
//                        it.error?.let { userError ->
//                            when (userError) {
//                                is UserError.InvalidId -> "Invalid id"
//                                is UserError.NetworkError -> userError.throwable.message
//                                UserError.ServerError -> "Server error"
//                                UserError.Unexpected -> "Unexpected error"
//                                is UserError.UserNotFound -> "User not found"
//                                UserError.ValidationFailed -> "Validation failed"
//                            }
//                        }
//                        viewModel.intents.trySend(MainIntent.ErrorDisplayed(it))
//                    } else {
//
//                        if (it.progress == true) {
//                            viewModel.intents.send(MainIntent.Initialize(it))
//                        } else {
//                            view.progress.visibility = View.GONE
//
//                            if (it.data?.isNotEmpty() == true)
//
//                            ///newOffersAdapter
//                                notificationAdapter.submitList(it.data)
//
//                            else
//                                view.progress.isVisible = it.noFavouriteYet!!
//
//                        }
//                    }
//                }
//
//            }
//        }
//    }
}