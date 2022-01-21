package com.codes_roots.golden_coupon.presentation.homefragment

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codes_roots.golden_coupon.R
import com.codes_roots.golden_coupon.databinding.HomeFragmentBinding
import com.codes_roots.golden_coupon.helper.BaseApplication
import com.codes_roots.golden_coupon.presentation.MainActivity
import com.codes_roots.golden_coupon.presentation.homefragment.adapter.BrandsAdapter
import com.codes_roots.golden_coupon.presentation.homefragment.mvi.MainIntent
import com.codes_roots.golden_coupon.presentation.homefragment.mvi.MainViewModel
import com.codes_roots.golden_coupon.presentation.homefragment.mvi.UserError

import kotlinx.android.synthetic.main.bottom_nav_content.*
import kotlinx.coroutines.flow.collect
import org.jetbrains.anko.support.v4.startActivityForResult
import java.util.*
import javax.inject.Inject


open class HomeFragment @Inject constructor() : Fragment(){
 private  val REQUEST_CODE_STT = 102
    lateinit var brandsAdapter: BrandsAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel by viewModels<MainViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        //   childFragmentManager.fragmentFactory = fragmentFactory!!

        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            BaseApplication.appComponent.inject(this)
        }
    }

    private lateinit var view: HomeFragmentBinding
//    lateinit var newOffersAdapter: New_Offers_Adapter
//    lateinit var MainAdapter: Main_Adapter




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        view = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)

        view.searchLayout.context = context as MainActivity

     //   view.searchLayout.listener = ClickHandler()

        view.context = context as MainActivity
      //  view.listener = ClickHandler()
      //  view.searchLayout.pref = (context as MainActivity).Pref

        brandsRecycleView()
       getAllData()
         view.searchLayout.microphone.setOnClickListener {

                 val sttIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                 sttIntent.putExtra(
                     RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                     RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                 )
                 sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                 sttIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now!")

                 try {
                     startActivityForResult(sttIntent, REQUEST_CODE_STT)
                 } catch (e: ActivityNotFoundException) {
                     e.printStackTrace()
                     Toast.makeText(requireContext(), "Your device does not support STT.", Toast.LENGTH_LONG).show()
                 }
             }



        myView()

        return view.root
    }

    fun brandsRecycleView() {
        brandsAdapter = BrandsAdapter(requireContext())
        view.brandsRecycleView.apply {
           layoutManager = LinearLayoutManager(context) // default orientation is vertical

            adapter = brandsAdapter;
            isNestedScrollingEnabled = false
            setHasFixedSize(true)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_STT -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    result?.let {
                        val recognizedText = it[0]
                        view.searchLayout.searchBar.setText(recognizedText)
                    }
                }
            }
        }
    }

    fun myView() {

//        getAllData()

  //      offersRecycleView()
    }

//    fun offersRecycleView() {
//        newOffersAdapter = New_Offers_Adapter()
//        view.offersRecycleView.apply {
//            adapter = newOffersAdapter;
//            setNestedScrollingEnabled(false)
//            setHasFixedSize(true)
//        }
//    }

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
                                viewModel?.intents.send(MainIntent.ErrorDisplayed(it))
                    }

                 else {
                        if (it.progress == true) {
                           // shimmer_view_container.startShimmerAnimation()
                            viewModel.intents.send(MainIntent.Initialize(it))
                        } else {
                            brandsAdapter.submitList(it.homepagedata?.brands)

                            //////// Slider viewPager
//                            view.pager.adapter = it.homepagedata?.maindata?.sliders.let { it ->
//                                SliderAdapter(requireContext(), it!!)
//                            }
//
//                            it.homepagedata?.maindata?.sliders.let { itS ->
//                                Permissions().init(itS?.size, context as MainActivity, view)
//                            }
//                            view.indicator.setViewPager(view.pager)
                            stopLoadingShimmer()
                        }

                    }
                }
                    }

                }
            }






    fun stopLoadingShimmer() {
//        shimmer_view_container?.setVisibility(View.GONE)
//        shimmer_view_container?.stopShimmerAnimation()
    }

}