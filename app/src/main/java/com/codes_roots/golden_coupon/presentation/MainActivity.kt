package com.codes_roots.golden_coupon.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import com.codes_roots.golden_coupon.R
import com.codes_roots.golden_coupon.helper.ClickHandler
import com.codes_roots.golden_coupon.presentation.homefragment.HomeFragment
import com.codes_roots.golden_coupon.presentation.mainactivity.BottomNav
import com.codes_roots.golden_coupon.presentation.offersfragment.OffersFragment
import kotlinx.android.synthetic.main.activity_main_content.*
import kotlinx.android.synthetic.main.bottom_nav_content.*
import kotlinx.android.synthetic.main.top_bar.view.*
import java.util.*

class MainActivity : AppCompatActivity() {
    var integerDeque: Deque<Int> = LinkedList()
    var flag = false
    lateinit var fragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(0, 0, 0, 0)
                .replace(R.id.main_frame, HomeFragment())
                .addToBackStack(null).commit()
           topicTitle()
        }
        bottom_nav_bar.setOnNavigationItemSelectedListener {
            val id = it.itemId
            if (integerDeque.contains(id)) {

                if (id == R.id.home) {
                    if (integerDeque.size != 0) {
                        if (flag) {
                            integerDeque.addFirst(R.id.home)
                            flag = false
                        }

                    }
                }
                integerDeque.remove(id)
            }
            integerDeque.push(id)
        //    ClickHandler().switchFragment(this, BottomNav().getFragment(this, it.itemId))
            with(bottom_nav_bar) {
                when (it.itemId) {
                    R.id.homeFragment -> {
                        menu.getItem(0).isChecked = true
                        ClickHandler().switchFragment(this@MainActivity, HomeFragment())
                    }

                    R.id.offer -> {
                        menu.getItem(1).isChecked = true
                        ClickHandler().switchFragment(this@MainActivity, OffersFragment())

                    }
                    R.id.fav -> {
                        menu.getItem(2).isChecked = true
                        ClickHandler().switchFragment(this@MainActivity, HomeFragment())
                    }

                    R.id.menu -> {
                        menu.getItem(3).isChecked = true
                      fragment = HomeFragment()
                    }
                    else ->
                    {
                        menu.getItem(0).isChecked = true
                        ClickHandler().switchFragment(this@MainActivity, HomeFragment())
                    }
                }


            }


            true

        }


    }


    override fun onBackPressed() {
        integerDeque.pop()
        if (!integerDeque.isNullOrEmpty()) {
            ClickHandler().switchFragment(
                this,
                BottomNav().getFragment(this, integerDeque.peek()!!)
            )

        } else
            finish()
    }

    private fun topicTitle() {
        //// top topic title
        val Enjoy = getString(R.string.enjoy)
        val first = "<font color='#cb0909'>$Enjoy</font>"
        val next = getString(R.string.title)
            include.title.setText(Html.fromHtml(first + next))
    }

}
