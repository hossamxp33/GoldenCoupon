package com.codes_roots.golden_coupon.presentation.offersfragment

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
import androidx.lifecycle.lifecycleScope
import com.codes_roots.golden_coupon.R
import com.codes_roots.golden_coupon.databinding.HomeFragmentBinding
import com.codes_roots.golden_coupon.databinding.OffersFragmentBinding
import com.codes_roots.golden_coupon.helper.BaseApplication
import com.codes_roots.golden_coupon.presentation.MainActivity

import kotlinx.android.synthetic.main.bottom_nav_content.*
import kotlinx.coroutines.flow.collect
import org.jetbrains.anko.support.v4.startActivityForResult
import java.util.*
import javax.inject.Inject


open class OffersFragment @Inject constructor() : Fragment(){
 private  val REQUEST_CODE_STT = 102
    private val textToSpeechEngine: TextToSpeech by lazy {
        TextToSpeech(requireContext(),
            TextToSpeech.OnInitListener { status ->
                if (status == TextToSpeech.SUCCESS) {
                    textToSpeechEngine.language = Locale.UK
                }
            })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //   childFragmentManager.fragmentFactory = fragmentFactory!!

        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            BaseApplication.appComponent.inject(this)
        }
    }

    private lateinit var view: OffersFragmentBinding
//    lateinit var newOffersAdapter: New_Offers_Adapter
//    lateinit var MainAdapter: Main_Adapter




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        view = DataBindingUtil.inflate(inflater, R.layout.offers_fragment, container, false)


     //   view.searchLayout.listener = ClickHandler()

        view.context = context as MainActivity
      //  view.listener = ClickHandler()
      //  view.searchLayout.pref = (context as MainActivity).Pref

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

//    fun getAllData() {
//        lifecycleScope.launchWhenStarted {
//            viewModel?.state?.collect {
//                if (it != null) {
//                    if (it.error != null) {
//                        it.error?.let {
//                            when (it) {
//                                is UserError.InvalidId -> "Invalid id"
//                                is UserError.NetworkError -> it.throwable.message
//                                UserError.ServerError -> "Server error"
//                                UserError.Unexpected -> "Unexpected error"
//                                is UserError.UserNotFound -> "User not found"
//                                UserError.ValidationFailed -> "Validation failed"
//                            }
//                        }
//                                viewModel?.intents.send(MainIntent.ErrorDisplayed(it))
//                    }
//
//                 else {
//                        if (it.progress == true) {
//                            shimmer_view_container.startShimmerAnimation()
//                            viewModel.intents.send(MainIntent.Initialize(it))
//                        } else {
//                            //// favFoodRecycleViewData
//                            MainAdapter.submitList(it.homepagedata?.categories)
//                            ///newOffersAdapter
//                            newOffersAdapter.submitList(it.homepagedata?.newoffer)
//                            //////// Slider viewPager
//                            view.pager.adapter = it.homepagedata?.maindata?.sliders.let { it ->
//                                SliderAdapter(requireContext(), it!!)
//                            }
//
//                            it.homepagedata?.maindata?.sliders.let { itS ->
//                                Permissions().init(itS?.size, context as MainActivity, view)
//                            }
//                            view.indicator.setViewPager(view.pager)
//                            stopLoadingShimmer()
//                        }
//
//                    }
//                }
//                    }
//
//                }
//            }






    fun stopLoadingShimmer() {
//        shimmer_view_container?.setVisibility(View.GONE)
//        shimmer_view_container?.stopShimmerAnimation()
    }

}