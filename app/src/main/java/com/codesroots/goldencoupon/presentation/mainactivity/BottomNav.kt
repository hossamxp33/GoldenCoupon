package com.codesroots.goldencoupon.presentation.mainactivity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

import androidx.fragment.app.Fragment
import com.codesroots.goldencoupon.R
import com.codesroots.goldencoupon.presentation.homefragment.HomeFragment
import com.codesroots.goldencoupon.presentation.menufragment.MenuFragment
import com.codesroots.goldencoupon.presentation.productoffersfragment.ProductOffersFragment


import kotlinx.android.synthetic.main.bottom_nav_content.*
import java.util.*


class BottomNav {

    private val mMenuItemSelected = 0
    var integerDeque: Deque<Int> = LinkedList()
    var flag: Boolean = true
     var fragment: Fragment = HomeFragment()
    lateinit var activity: AppCompatActivity


     fun getFragment(context: Context,itemId: Int): Fragment {

        with( (context as MainActivity).bottom_nav_bar){

            when (itemId) {
            R.id.home -> {
               menu.getItem(0).isChecked = true
                fragment = HomeFragment()
            }
            R.id.offer -> {
               menu.getItem(1).isChecked = true
                fragment = ProductOffersFragment()

            }
            R.id.fav -> {
                menu.getItem(2).isChecked = true
                fragment = HomeFragment()
            }


            R.id.menu -> {
               menu.getItem(3).isChecked = true
                fragment = MenuFragment()

            }
        }
        }

        return fragment
    }


}