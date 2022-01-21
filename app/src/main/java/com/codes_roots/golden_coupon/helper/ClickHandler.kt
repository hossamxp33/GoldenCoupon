package com.codes_roots.golden_coupon.helper

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.codes_roots.golden_coupon.R
import com.codes_roots.golden_coupon.presentation.MainActivity

import kotlinx.android.synthetic.main.bottom_nav_content.*

class ClickHandler {





    fun switchFragment(context: Context, fragment: Fragment) {
        (context as MainActivity).supportFragmentManager.beginTransaction()
            .setCustomAnimations(0, 0, 0, 0)
            .replace(R.id.main_frame, fragment).addToBackStack(null).commit()
    }


    fun switchToActivity(context: Context, activity: AppCompatActivity) {

        val i = Intent(context, activity::class.java)
        (context).startActivity(i);

    }
}
