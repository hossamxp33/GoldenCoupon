package com.codes_roots.golden_coupon.presentation.auth.signupfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.codes_roots.golden_coupon.R
import com.codes_roots.golden_coupon.databinding.RegisterFragmentBinding
import com.codes_roots.golden_coupon.entites.auth.User
import com.codes_roots.golden_coupon.helper.BaseApplication
import com.codes_roots.golden_coupon.helper.PreferenceHelper
import com.codes_roots.golden_coupon.presentation.auth.viewmodel.AuthViewModel

import javax.inject.Inject


class SignUpFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<AuthViewModel> { viewModelFactory }

    @Inject
    lateinit var Pref: PreferenceHelper

    lateinit var view: RegisterFragmentBinding

    val progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            BaseApplication.appComponent.inject(this)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        view = DataBindingUtil.inflate(inflater,
            R.layout.register_fragment, container, false)

        var emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"


        view.register.setOnClickListener {
            val password = view.password.text.toString()
            if (view.username.text.toString() == "" && view.mobile.text.toString() == "" && view.email.text.toString() == "") {
                Toast.makeText(requireContext(),"الرجاء أكمل البيانات",Toast.LENGTH_SHORT).show()

            } else if(!view.email.text.toString().trim { it <= ' ' }.matches(emailPattern.toRegex())){
                Toast.makeText(requireContext(), "valid email address", Toast.LENGTH_SHORT).show()

            }else

            {
                registerRequest()
            }


        }

        viewModel.processVisibility.observe(requireActivity()){
            view.progress.isVisible = it
        }
        viewModel.authLD?.observe(requireActivity() , Observer {
            viewModel.processVisibility.value = true

            if (it.data.token == null){
                Toast.makeText(context , "", Toast.LENGTH_SHORT).show()
                viewModel.processVisibility.value = false

            }else {
                viewModel.processVisibility.value = true

                Pref.userName = (it.data.username)

                Pref.token = (it.data.token)


                Toast.makeText(context , "تم انشاء حساب", Toast.LENGTH_SHORT).show()
                viewModel.processVisibility.value = false


            }

        })

        viewModel.errorMessage.observe(requireActivity(), Observer {
            Toast.makeText(context ,it, Toast.LENGTH_SHORT).show()

        })

        return view.root
    }

    fun registerRequest() {
        val registerInfo = User(username = view.username.text.toString(),
            password= view.password.text.toString()
            , active = 1
            , email = view.email.text.toString()
            , email_required =  "1")

        viewModel.register(registerInfo)
    }

}