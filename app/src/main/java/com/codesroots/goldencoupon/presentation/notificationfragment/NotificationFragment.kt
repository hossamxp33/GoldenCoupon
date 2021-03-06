package com.codesroots.goldencoupon.presentation.notificationfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.codesroots.goldencoupon.R
import com.codesroots.goldencoupon.databinding.NotificaionFragmentBinding
import com.codesroots.goldencoupon.di.Error_MotionToast
import com.codesroots.goldencoupon.helper.BaseApplication
import com.codesroots.goldencoupon.presentation.notificationfragment.adapter.NotificationAdapter
import com.codesroots.goldencoupon.presentation.notificationfragment.viewmodel.NotificationViewModel

import javax.inject.Inject


open class NotificationFragment @Inject constructor() : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<NotificationViewModel> { viewModelFactory }

    lateinit var notificationAdapter: NotificationAdapter
    private lateinit var view: NotificaionFragmentBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            BaseApplication.appComponent.inject(this)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        view = DataBindingUtil.inflate(inflater,
            R.layout.notificaion_fragment, container, false)



//        getAllData()
        notificationsRecycleView()
        viewModel.getNotification()
        viewModel.NotificationLD.observe(this, Observer {
            if (it.success){
                notificationAdapter.submitList(it.data)
            view.progress.isVisible = false}

            else
                Error_MotionToast("error", requireActivity())

        })
        return view.root
    }


    fun notificationsRecycleView() {
        notificationAdapter = NotificationAdapter(viewModel, requireContext())
        view.notificationRecycle.apply {
            adapter = notificationAdapter;
       //w     isNestedScrollingEnabled = false
            setHasFixedSize(true)
        }
    }

}