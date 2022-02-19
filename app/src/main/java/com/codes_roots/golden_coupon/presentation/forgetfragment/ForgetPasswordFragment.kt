package com.codes_roots.golden_coupon.presentation.forgetfragment

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
import com.codes_roots.golden_coupon.R
import com.codes_roots.golden_coupon.databinding.ForgetFragmentBinding
import com.codes_roots.golden_coupon.databinding.LoginFragmentBinding
import com.codes_roots.golden_coupon.entites.auth.User
import com.codes_roots.golden_coupon.helper.BaseApplication
import com.codes_roots.golden_coupon.helper.ClickHandler
import com.codes_roots.golden_coupon.helper.PreferenceHelper
import com.codes_roots.golden_coupon.presentation.auth.RegisterActivity
import com.codes_roots.golden_coupon.presentation.auth.viewmodel.AuthViewModel
import com.codes_roots.golden_coupon.presentation.mainactivity.MainActivity
import kotlinx.android.synthetic.main.forget_fragment.*

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
        viewModel.authLD?.observe(requireActivity(), Observer {

            if (it.data.token == null) {

                Toast.makeText(context, "please enter valid email", Toast.LENGTH_SHORT)
                    .show()

            } else {

                Pref.userName = (it.data.username)
                Pref.token = (it.data.token)
                Pref.UserId = it.data.userid!!

                ClickHandler().switchToActivity(requireContext(), MainActivity())

            }

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