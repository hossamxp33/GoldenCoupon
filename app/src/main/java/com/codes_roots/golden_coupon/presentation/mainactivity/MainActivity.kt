package com.codes_roots.golden_coupon.presentation.mainactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.codes_roots.golden_coupon.R
import com.codes_roots.golden_coupon.databinding.ActivityMainBinding
import com.codes_roots.golden_coupon.helper.BottomNavigationBehavior
import com.codes_roots.golden_coupon.helper.ClickHandler
import com.codes_roots.golden_coupon.helper.Permissions
import com.codes_roots.golden_coupon.presentation.homefragment.HomeFragment
import com.codes_roots.golden_coupon.presentation.homefragment.mvi.MainIntent
import com.codes_roots.golden_coupon.presentation.homefragment.mvi.MainViewModel
import com.codes_roots.golden_coupon.presentation.homefragment.mvi.UserError
import com.codes_roots.golden_coupon.presentation.menufragment.MenuFragment
import com.codes_roots.golden_coupon.presentation.productoffersfragment.ProductOffersFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.activity_main_content.*
import kotlinx.android.synthetic.main.bottom_nav_content.*
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.top_bar.*
import kotlinx.android.synthetic.main.top_bar.shimmer_view_container
import kotlinx.android.synthetic.main.top_bar.view.*
import kotlinx.coroutines.flow.collect
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener
import java.util.*
import javax.inject.Inject


import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.codes_roots.golden_coupon.data_layer.OnBackPressedListener
import com.codes_roots.golden_coupon.generated.callback.OnClickListener
import com.codes_roots.golden_coupon.helper.PreferenceHelper
import com.codes_roots.golden_coupon.presentation.favfragment.FavoriteFragment
import com.codes_roots.golden_coupon.presentation.notificationfragment.NotificationFragment
import kotlinx.android.synthetic.main.activity_main_content.view.*


class MainActivity : AppCompatActivity(), HasAndroidInjector {
    var integerDeque: Deque<Int> = LinkedList()
    var doubleBackToExitPressedOnce: Boolean = false

    private var bottomNavigationView: BottomNavigationView? = null
    var navHostFragment: NavHostFragment? = null

    var binding: ActivityMainBinding? = null

    var flag = false


    lateinit var fragment: Fragment

    @Inject
    lateinit var fragmentFactory: FragmentFactory

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel by viewModels<MainViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        supportFragmentManager.fragmentFactory = fragmentFactory

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        notification_background.setOnClickListener {
         ClickHandler().switchFragment(this, NotificationFragment())

        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(0, 0, 0, 0)
                .replace(R.id.main_frame, HomeFragment())
                .addToBackStack(null).commit()
            topicTitle()
            getAllData()
        }
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        KeyboardVisibilityEvent.setEventListener(this,
            KeyboardVisibilityEventListener { view ->
                if (view) include?.visibility = View.GONE
                else include?.visibility = View.VISIBLE
            })

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
                        ClickHandler().switchFragment(this@MainActivity, ProductOffersFragment())

                    }
                    R.id.fav -> {
                        menu.getItem(2).isChecked = true
                        ClickHandler().checkForToken(context, FavoriteFragment())


                    }

                    R.id.menu -> {
                        menu.getItem(3).isChecked = true
                        ClickHandler().switchFragment(this@MainActivity, MenuFragment())

                    }
                    else -> {
                        menu.getItem(0).isChecked = true
                        ClickHandler().switchFragment(this@MainActivity, HomeFragment())
                    }
                }


            }


            true

        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        integerDeque.poll()
        if (!integerDeque.isNullOrEmpty()) {
            ClickHandler().switchFragment(
                this,
                BottomNav().getFragment(this, integerDeque.peek()!!)
            )
            doubleBackToExitPressedOnce = true
        } else if (doubleBackToExitPressedOnce)

            finish()
        else if (fragmentManager.backStackEntryCount == 0) {
            bottom_nav_bar.selectedItemId = R.id.homeFragment
            doubleBackToExitPressedOnce = true
        }



//       Handler(Looper.getMainLooper()).postDelayed(Runnable {  }, 2000)

    }

//
//    private fun setupNavigation() {
//        val layoutParams = bottom_nav_bar.layoutParams as CoordinatorLayout.LayoutParams
//        layoutParams.behavior = BottomNavigationBehavior()
//        navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
//        bottomNavigationView = findViewById(R.id.bottom_nav_bar)
//        if (navHostFragment != null) {
//            NavigationUI.setupWithNavController(bottomNavigationView!!,
//                navHostFragment!!.navController)
//        }
//    }


    private fun topicTitle() {
        //// top topic title
        val Enjoy = getString(R.string.enjoy)
        val first = "<font color='#cb0909'>$Enjoy</font>"
        val next = getString(R.string.title)
        include.title.setText(Html.fromHtml(first + next))
    }

    fun getAllData() {
        lifecycleScope.launchWhenStarted {
            viewModel?.state?.collect {
                if (it != null) {
                    if (it.error != null) {
                        it.error?.let {
                            when (it) {
                                is UserError.InvalidId -> "Invalid id"
                                is UserError.NetworkError -> it.throwable.message
                                UserError.ServerError -> "Server error"
                                UserError.Unexpected -> "Unexpected error"
                                is UserError.UserNotFound -> "User not found"
                                UserError.ValidationFailed -> "Validation failed"
                            }
                        }
                        viewModel.intents.send(MainIntent.ErrorDisplayed(it))
                    } else {

                        if (it.progress == true) {
                            shimmer_view_container.startShimmerAnimation()
                            viewModel.intents.send(MainIntent.Initialize(it, 1))
                        } else {
                            //////// Slider viewPager
                            pager.adapter = it.homepagedata?.sliders?.let { it ->
                                SliderAdapter(this@MainActivity, it)
                            }
                            it.homepagedata?.sliders?.let { itS ->
                                Permissions().init(itS.size, this@MainActivity, this@MainActivity)
                            }
                            indicator.setViewPager(pager)
                            stopLoadingShimmer()
                        }

                    }
                }
            }

        }
    }

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

    fun stopLoadingShimmer() {
        shimmer_view_container?.visibility = View.GONE
        shimmer_view_container?.stopShimmerAnimation()
    }

    override fun onResume() {
        super.onResume()
        getAllData()
    }
}
