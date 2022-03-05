package com.codes_roots.golden_coupon.presentation.auth

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.codes_roots.golden_coupon.R
import com.codes_roots.golden_coupon.entites.auth.User
import com.codes_roots.golden_coupon.helper.ClickHandler
import com.codes_roots.golden_coupon.helper.PreferenceHelper
import com.codes_roots.golden_coupon.presentation.auth.viewmodel.AuthViewModel
import com.codes_roots.golden_coupon.presentation.mainactivity.MainActivity
import com.codes_roots.golden_coupon.presentation.ratefragment.RateFragment.Companion.TAG

import com.facebook.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task

import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.register_activity.*

import java.util.*
import javax.inject.Inject
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.android.material.snackbar.Snackbar

import com.google.firebase.auth.AuthResult

import com.google.firebase.auth.GoogleAuthProvider

import com.google.firebase.auth.AuthCredential
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector


class RegisterActivity @Inject constructor(): AppCompatActivity() , HasAndroidInjector {
    //  var callbackManager: CallbackManager? = null
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    val RC_SIGN_IN = 1000

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<AuthViewModel> { viewModelFactory }

    @Inject
    lateinit var pref: PreferenceHelper

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)



        PreferenceHelper(this)
        //  FacebookSdk.sdkInitialize(this);

        setContentView(R.layout.register_activity)
        mAuth = FirebaseAuth.getInstance();


        tab_layout.setBackgroundColor(ContextCompat.getColor(this, R.color.white))


// Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        google_button.setSize(SignInButton.SIZE_STANDARD)

        google_button.setOnClickListener {
            signIn()
        }
        viewModel.authLD?.observe(this ,  {

            if (it.data.token == null){

                Toast.makeText(this , "خطأ", Toast.LENGTH_SHORT).show()

            }else {
                pref.userName = (it.data.username)
                pref.token = (it.data.token)
                pref.UserId = it.data.id!!
                ClickHandler().switchToActivity(this,MainActivity())

            }

        })
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

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!

                val personEmail = account.email
                val token = account.idToken
                val personName = account.displayName
                Toast.makeText(this, "user name is : $personName", Toast.LENGTH_SHORT).show()

                val loginInfo = User(username = personName, google_token = token)
                viewModel.loginByGoogleResponse(loginInfo)


                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)


            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }


    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

    fun loginRequest() {


    }

}