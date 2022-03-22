package com.codesroots.goldencoupon.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.codesroots.goldencoupon.R
import com.codesroots.goldencoupon.entites.auth.User
import com.codesroots.goldencoupon.helper.ClickHandler
import com.codesroots.goldencoupon.helper.PreferenceHelper
import com.codesroots.goldencoupon.presentation.auth.viewmodel.AuthViewModel
import com.codesroots.goldencoupon.presentation.mainactivity.MainActivity
import com.codesroots.goldencoupon.presentation.ratefragment.RateFragment.Companion.TAG

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException

import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.register_activity.*

import javax.inject.Inject
import com.google.firebase.auth.FirebaseAuth

import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector


class RegisterActivity @Inject constructor() : AppCompatActivity(), HasAndroidInjector {
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
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        google_button.setSize(SignInButton.SIZE_STANDARD)

        google_button.setOnClickListener {
            signIn()
        }
        viewModel.authLD?.observe(this, {

            if (it.data.token == null) {

                Toast.makeText(this, "خطأ", Toast.LENGTH_SHORT).show()

            } else {
                pref.userName = (it.data.username)
                pref.token = (it.data.token)
                pref.UserId = it.data.id!!
                ClickHandler().switchToActivity(this, MainActivity())

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
                val token = account.id
                val personName = account.displayName

                Toast.makeText(this, "user name is : $personName", Toast.LENGTH_SHORT).show()

                pref.token = token

                pref.userName = personName

                val loginInfo = User(username = personName,
                    google_token = token,
                    active = 1,
                    email = personEmail,
                    email_required = "1")

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