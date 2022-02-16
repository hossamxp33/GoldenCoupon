package com.codes_roots.golden_coupon.presentation.auth

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.codes_roots.golden_coupon.presentation.auth.loginfragment.LoginFragment
import com.codes_roots.golden_coupon.presentation.auth.signupfragment.SignUpFragment


class TabsPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle, private var numberOfTabs: Int) :
    FragmentStateAdapter(fm, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
           //     val bundle = Bundle()
             //   bundle.putString("fragmentName", "Music Fragment")
                val login = LoginFragment()
          //      login.arguments = bundle
                return login
            }
            1 -> {
                return   SignUpFragment()
            }

            else -> return LoginFragment()
        }
    }

    override fun getItemCount(): Int {
        return numberOfTabs
    }
}