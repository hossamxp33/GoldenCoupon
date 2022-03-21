package com.codesroots.goldencoupon.presentation.mainactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.codesroots.goldencoupon.R
import com.codesroots.goldencoupon.databinding.ActivityMainBinding
import com.codesroots.goldencoupon.presentation.homefragment.HomeFragment
import com.codesroots.goldencoupon.presentation.homefragment.mvi.MainIntent
import com.codesroots.goldencoupon.presentation.homefragment.mvi.MainViewModel
import com.codesroots.goldencoupon.presentation.homefragment.mvi.UserError
import com.codesroots.goldencoupon.presentation.menufragment.MenuFragment
import com.codesroots.goldencoupon.presentation.productoffersfragment.ProductOffersFragment
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


import com.codesroots.goldencoupon.presentation.favfragment.FavoriteFragment
import com.codesroots.goldencoupon.presentation.notificationfragment.NotificationFragment
import kotlinx.android.synthetic.main.activity_main_content.view.*


import android.app.Dialog
import android.os.Handler
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.codesroots.goldencoupon.entites.whatsapp.WhatsAppModel
import com.codesroots.goldencoupon.helper.*
import com.codesroots.goldencoupon.presentation.dealsfragment.DealsFragment
import com.codesroots.goldencoupon.presentation.mainactivity.whatsapp.WhatsAppAdapter
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.call_us_dialog.*
import kotlinx.android.synthetic.main.call_us_dialog.view.*
import kotlinx.android.synthetic.main.main_frame_content.*

import androidx.core.view.GravityCompat





class MainActivity : AppCompatActivity(), HasAndroidInjector {
    var integerDeque: Deque<Int> = LinkedList()

    var binding: ActivityMainBinding? = null

    private var isFirstBackPressed = false

    lateinit var whatsAppAdapter: WhatsAppAdapter

    var flag = false

    lateinit var fragment: Fragment

    @Inject
    lateinit var fragmentFactory: FragmentFactory

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var mAuth: FirebaseAuth

    val viewModel by viewModels<MainViewModel> { viewModelFactory }
    var whatsAppList : WhatsAppModel?=null
    override fun onCreate(savedInstanceState: Bundle?) {

        AndroidInjection.inject(this)

        supportFragmentManager.fragmentFactory = fragmentFactory

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        constraintLayout.setOnClickListener {

            ClickHandler().switchFragment(this, NotificationFragment())

        }
        mAuth = FirebaseAuth.getInstance()
        FirebaseApp.initializeApp(this)
        FirebaseMessaging.getInstance()
        FirebaseMessaging.getInstance()

            .subscribeToTopic("1")
        FirebaseMessaging.getInstance().subscribeToTopic("100")

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(0, 0, 0, 0)
                .replace(R.id.main_frame, HomeFragment())
                .addToBackStack(null).commit()
            topicTitle()
            getAllData()
        }
       this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        if(preferenceHelper.lang!!.contains("ar"))
        logo_icon.setImageResource(R.drawable.splash_logo_ar)
        else
        logo_icon.setImageResource(R.drawable.splash_logo_english)
        try {
            viewModel.getWhatsApp()
        } catch (e:Exception){

        }

        fab!!.setOnClickListener { view ->

            viewModel.whatsAppLD!!.observe(this,  {
                whatsAppList = it
            })


            val dialog = Dialog(this, R.style.Theme_AppCompat_Dialog)

            dialog.setContentView(R.layout.call_us_dialog)

            whatsAppAdapter = WhatsAppAdapter(viewModel,this)
            dialog.whats_recycleView.adapter = whatsAppAdapter
            dialog.whats_recycleView.layoutManager = LinearLayoutManager(this)
            whatsAppAdapter.submitList(whatsAppList?.data)

            dialog.show()
            //Intent myIntent = new Intent(view.getContext(), agones.class);
            //startActivityForResult(myIntent, 0);
        }
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
                    R.id.deals -> {
                        menu.getItem(2).isChecked = true
                        ClickHandler().switchFragment(this@MainActivity, DealsFragment())
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
        if ( bottom_nav_bar.menu.getItem(0).isChecked)
        moveTaskToBack(true);
        else
        super.onBackPressed()
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
