package com.codesroots.goldencoupon.presentation.menufragment

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.codesroots.goldencoupon.R
import com.codesroots.goldencoupon.databinding.MenuFragmentBinding
import com.codesroots.goldencoupon.helper.BaseApplication
import com.codesroots.goldencoupon.helper.ClickHandler
import com.codesroots.goldencoupon.helper.PreferenceHelper
import com.codesroots.goldencoupon.presentation.auth.RegisterActivity
import com.codesroots.goldencoupon.presentation.homefragment.mvi.UserError
import com.codesroots.goldencoupon.presentation.mainactivity.MainActivity
import com.codesroots.goldencoupon.presentation.menufragment.mvi.MainIntent
import com.codesroots.goldencoupon.presentation.menufragment.mvi.StaticPagesViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

import kotlinx.android.synthetic.main.bottom_nav_content.*
import kotlinx.coroutines.flow.collect
import java.util.*
import javax.inject.Inject


open class MenuFragment @Inject constructor() : Fragment() {

    @Inject
    lateinit var pref: PreferenceHelper

    lateinit var staticPagesAdapter: StaticPagesAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel by viewModels<StaticPagesViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        //   childFragmentManager.fragmentFactory = fragmentFactory!!

        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            BaseApplication.appComponent.inject(this)
        }
    }

    private lateinit var view: MenuFragmentBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {

        view = DataBindingUtil.inflate(inflater, R.layout.menu_fragment, container, false)
        view.context = context as MainActivity
        view.listener = ClickHandler()

        view.myAccount.isVisible = pref.token.isNullOrEmpty()
        view.logout.isVisible = !pref.token.isNullOrEmpty()


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

// Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);

        view.logout.setOnClickListener {
            mGoogleSignInClient.signOut()
           pref.token = ""
            pref.userName=""
            Toast.makeText(requireContext(), "تم تسجيل خروجك", Toast.LENGTH_SHORT).show()
            val homeIntent = Intent(requireContext(), RegisterActivity::class.java)
           startActivity(homeIntent, ActivityOptions.makeSceneTransitionAnimation(requireActivity()).toBundle())

        }
//        if (pref.lang!!.contains("en")) {
//            view.rateApp.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_next, 0)
//            view.lang.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_next, 0)
//            view.myAccount.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_next, 0)
//            view.deals.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_next, 0)
//
//
//            //            chat.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_next, 0);
//        }
        // viewModel.intents.trySend(MainIntent.Initialize(viewModel.state.value!!.copy(progress = false)))

        myOrderRecycleView()

        getAllData()
        return view.root
    }


    fun getAllData() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect {
                if (it != null) {
                    if (it.error != null) {
                        it.error?.let { userError ->
                            when (userError) {
                                is UserError.InvalidId -> "Invalid id"
                                is UserError.NetworkError -> userError.throwable.message
                                UserError.ServerError -> "Server error"
                                UserError.Unexpected -> "Unexpected error"
                                is UserError.UserNotFound -> "User not found"
                                UserError.ValidationFailed -> "Validation failed"
                            }
                        }
                        viewModel.intents.trySend(MainIntent.ErrorDisplayed(it))
                    } else {

                        if (it.progress == true) {
                            viewModel.intents.send(MainIntent.Initialize(it))

                        } else {

                            if (it.data?.isNotEmpty() == true)

                                staticPagesAdapter.submitList(it.data!!)
                            view.progress.isVisible = it.progress!!


                        }
                    }
                }

            }
        }
    }

    fun myOrderRecycleView() {
        staticPagesAdapter = StaticPagesAdapter(requireContext())
        view.pagesRecycle.apply {
            layoutManager = LinearLayoutManager(
                context
            )
            adapter = staticPagesAdapter;
            isNestedScrollingEnabled = false
            setHasFixedSize(true)
        }
    }


}