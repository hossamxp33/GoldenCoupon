package com.codesroots.goldencoupon.presentation.forgetfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.codesroots.goldencoupon.R
import com.codesroots.goldencoupon.databinding.ForgetFragmentBinding
import com.codesroots.goldencoupon.di.Error_MotionToast
import com.codesroots.goldencoupon.di.SUCCESS_MotionToast
import com.codesroots.goldencoupon.di.WARN_MotionToast
import com.codesroots.goldencoupon.helper.BaseApplication
import com.codesroots.goldencoupon.helper.ClickHandler
import com.codesroots.goldencoupon.helper.PreferenceHelper
import com.codesroots.goldencoupon.presentation.auth.viewmodel.AuthViewModel
import com.codesroots.goldencoupon.presentation.mainactivity.MainActivity

import javax.inject.Inject


class ForgetPasswordFragment @Inject constructor() : Fragment() {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<AuthViewModel> { viewModelFactory }

    @Inject
    lateinit var Pref: PreferenceHelper

    lateinit var view: ForgetFragmentBinding

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

        var emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        view = DataBindingUtil.inflate(inflater,
            R.layout.forget_fragment, container, false)


        view.send.setOnClickListener {
            if (view.email.text.toString() == "" && !view.email.text.toString().trim { it <= ' ' }
                    .matches(emailPattern.toRegex())) {
                Toast.makeText(requireContext(), "الرجاء أكمل البيانات", Toast.LENGTH_SHORT).show()

            } else {
               getPassword()
            }


        }
        viewModel.forgetPasswordLD?.observe(requireActivity(), Observer {
            if (it.user.state.contains( "500")) {
                Error_MotionToast(it?.user?.data!!,requireActivity())

            } else if (it.user.state.contains( "200") ){
                 SUCCESS_MotionToast(it.user.data,requireActivity())
                ClickHandler().switchToActivity(requireContext(), MainActivity())
            }
            else
                WARN_MotionToast("please enter valid email",requireActivity())

        })




        viewModel.errorMessage.observe(requireActivity(), Observer {
            Toast.makeText(context, "خطأ في كلمة المرور او كلمة السر", Toast.LENGTH_SHORT).show()
        })
        return view.root
    }


    fun getPassword() {
        val  email = view.email.text.toString()
        viewModel.forgetPassword(email)
    }
}