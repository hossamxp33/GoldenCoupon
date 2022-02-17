package com.codes_roots.golden_coupon.presentation.auth.loginfragment

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
import com.codes_roots.golden_coupon.databinding.LoginFragmentBinding
import com.codes_roots.golden_coupon.entites.auth.User
import com.codes_roots.golden_coupon.helper.BaseApplication
import com.codes_roots.golden_coupon.helper.ClickHandler
import com.codes_roots.golden_coupon.helper.PreferenceHelper
import com.codes_roots.golden_coupon.presentation.auth.RegisterActivity
import com.codes_roots.golden_coupon.presentation.auth.viewmodel.AuthViewModel
import com.codes_roots.golden_coupon.presentation.mainactivity.MainActivity

import javax.inject.Inject


class LoginFragment@Inject constructor(): Fragment() {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<AuthViewModel> { viewModelFactory }

    @Inject
    lateinit var Pref: PreferenceHelper

    lateinit var view: LoginFragmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            BaseApplication.appComponent.inject(this)
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        view = DataBindingUtil.inflate(inflater,
            R.layout.login_fragment, container, false)


        view.login.setOnClickListener {
          loginRequest()
        }

//        view.forgetPW.setOnClickListener {
//            val intent = Intent(this.activity, ForgotPasswordActivity::class.java)
//
//            (requireActivity()).startActivity(intent)
//        }

        viewModel.authLD?.observe(requireActivity() , Observer {

            if (it.data.token == null){

                Toast.makeText(context , "خطأ في كلمة المرور او كلمة السر", Toast.LENGTH_SHORT).show()

            }else {

                Pref.userName = (it.data.username)
                Pref.token = (it.data.token)
                Pref.UserId = it.data.userid!!

            ClickHandler().switchToActivity(requireContext(),MainActivity())

            }

        })




        viewModel.errorMessage.observe(requireActivity(), Observer {
            Toast.makeText(context , "خطأ في كلمة المرور او كلمة السر", Toast.LENGTH_SHORT).show()
        })
   return view.root
    }


    fun loginRequest() {
        val loginInfo = User(
         username = view.username.text.toString()
         , password = view.password.text.toString())
        viewModel.login(loginInfo)
    }
}