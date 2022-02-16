package com.codes_roots.golden_coupon.presentation.auth

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.codes_roots.golden_coupon.R
import com.codes_roots.golden_coupon.helper.PreferenceHelper
import com.codes_roots.golden_coupon.presentation.auth.viewmodel.AuthViewModel

import com.facebook.*

import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.register_activity.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.inject.Inject


class RegisterActivity: AppCompatActivity() {
  //  var callbackManager: CallbackManager? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<AuthViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val info: PackageInfo
        try {
            info = packageManager.getPackageInfo(
                "com.example.satafood",
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                var md: MessageDigest
                md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val something = String(Base64.encode(md.digest(), 0))
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.i("hash key", something)
            }
        } catch (e1: PackageManager.NameNotFoundException) {
            Log.e("name not found", e1.toString())
        } catch (e: NoSuchAlgorithmException) {
            Log.e("no such an algorithm", e.toString())
        } catch (e: Exception) {
            Log.e("exception", e.toString())
        }


        PreferenceHelper(this)
      //  FacebookSdk.sdkInitialize(this);

        setContentView(R.layout.register_activity)


        tab_layout.setBackgroundColor(ContextCompat.getColor(this, R.color.white))

        printKeyHash()

//        facebook_button.setReadPermissions(listOf("user_status"))
//        LoginManager.getInstance().logInWithReadPermissions(
//            this,
//            Arrays.asList("email"));
 //       LoginManager.getInstance().logOut()
//        facebook_button.setOnClickListener {
////
//            callbackManager = CallbackManager.Factory.create()
//
//            LoginManager.getInstance().registerCallback(callbackManager,
//                object : FacebookCallback<LoginResult?> {
//                    override fun onSuccess(loginResult: LoginResult?) {
//                        val image_url =
//                            "http://graph.facebook.com/" + Profile.getCurrentProfile()!!.id
//                                .toString() + "/picture?type=large"
////
////                        viewModel.facebookLogin(
////                            User(facebook_id = loginResult!!.accessToken.userId,username = Profile.getCurrentProfile().name
////                            ,photo=image_url)
////
////                        )
//
//                       // PreferenceHelper.setPhoto(image_url)
//               Toast.makeText(this@RegisterActivity, "Its toast!", Toast.LENGTH_SHORT).show()
//
//                    }
//
//                    override fun onCancel() {
//                        Toast.makeText(this@RegisterActivity, "Its onCancel!", Toast.LENGTH_SHORT).show()
//                    }
//
//                    override fun onError(exception: FacebookException) {
//                        Toast.makeText(this@RegisterActivity, "Its onError!", Toast.LENGTH_SHORT).show()
//
//                        // App code
//                    }
//                })
//        }

        // Number Of Tabs
        val numberOfTabs = 2
        // Show all Tabs in screen
        tab_layout.tabMode = TabLayout.MODE_FIXED
        // Scroll to see all Tabs
        //tab_layout.tabMode = TabLayout.MODE_SCROLLABLE
        // Set divider
        tab_layout.isInlineLabel = true

        // Set the ViewPager Adapter
        val adapter =
            TabsPagerAdapter(supportFragmentManager, lifecycle, numberOfTabs)
        tabs_viewpager.adapter = adapter
        // Enable Swipe
        tabs_viewpager.isUserInputEnabled = true
        // Link the TabLayout and the ViewPager2 together and Set Text & Icons
        TabLayoutMediator(tab_layout, tabs_viewpager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = this.getText(R.string.login)
                    tab.setIcon(R.drawable.login_icon)


                }
                1 -> {
                     tab.text = this.getText(R.string.register)
                     tab.setIcon(R.drawable.add)
                }
            }

        }.attach()


    }


    /// get Hash KEy
    private fun printKeyHash() {
        try {
            val info: PackageInfo = getPackageManager()
                .getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey = String(Base64.encode(md.digest(), 0))
                Log.i("AppLog", "key:$hashKey=")
            }
        } catch (e: java.lang.Exception) {
            Log.e("AppLog", "error:", e)
        }
    }
}