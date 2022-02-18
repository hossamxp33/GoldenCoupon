package com.codes_roots.golden_coupon.presentation.productoffersfragment

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.codes_roots.golden_coupon.R
import com.codes_roots.golden_coupon.databinding.OffersFragmentBinding
import com.codes_roots.golden_coupon.di.WARN_MotionToast
import com.codes_roots.golden_coupon.helper.BaseApplication
import com.codes_roots.golden_coupon.helper.ClickHandler
import com.codes_roots.golden_coupon.helper.PreferenceHelper
import com.codes_roots.golden_coupon.presentation.homefragment.mvi.UserError
import com.codes_roots.golden_coupon.presentation.mainactivity.MainActivity
import com.codes_roots.golden_coupon.presentation.productoffersfragment.adapter.category.CategoryAdapter
import com.codes_roots.golden_coupon.presentation.productoffersfragment.adapter.product.ProductsAdapter
import com.codes_roots.golden_coupon.presentation.productoffersfragment.adapter.sub.SubCategoryAdapter
import com.codes_roots.golden_coupon.presentation.productoffersfragment.mvi.MainIntent
import com.codes_roots.golden_coupon.presentation.productoffersfragment.mvi.ProductsViewModel
import kotlinx.coroutines.flow.collect
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent.setEventListener
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener

import java.util.*
import javax.inject.Inject


open class ProductOffersFragment @Inject constructor() : Fragment() {
    private val REQUEST_CODE_STT = 102
    lateinit var categoryAdapter: CategoryAdapter
    lateinit var subcategoryAdapter: SubCategoryAdapter
    lateinit var productsAdapter: ProductsAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel by viewModels<ProductsViewModel> { viewModelFactory }


    override fun onCreate(savedInstanceState: Bundle?) {
        //   childFragmentManager.fragmentFactory = fragmentFactory!!
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            BaseApplication.appComponent.inject(this)
        }
    }

    private lateinit var view: OffersFragmentBinding

    @Inject
    lateinit var Pref: PreferenceHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        view = DataBindingUtil.inflate(inflater, R.layout.offers_fragment, container, false)


        //   view.searchLayout.listener = ClickHandler()

        view.context = context as MainActivity
         view.listener = ClickHandler()
        view.viewModel = viewModel

        //  view.searchLayout.pref = (context as MainActivity).Pref

        viewModel.intents.trySend(MainIntent.FilterData(viewModel.state.value?.copy(progress = true , country_id = Pref.CountryId),
            viewModel?.FilterFileds ,Pref.CountryId))
        //  view.searchBar.setError("assad")
        view.searchLayout.searchBar.doOnTextChanged { text, start, before, count ->


            viewModel?.FilterFileds?.put("Filter[name]",text.toString())

            viewModel.intents.trySend(

                        MainIntent.FilterData(
                    viewModel.state.value!!,
                            viewModel?.FilterFileds,
                            viewModel.state.value!!.country_id
                )
            )
        }

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
                Toast.makeText(
                    requireContext(),
                    "Your device does not support STT.",
                    Toast.LENGTH_LONG
                ).show()
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

        getAllData()
        categoryRecycleView()
        productsRecycleView()
        subcategoryRecycleView()
    }

    fun categoryRecycleView() {
        categoryAdapter = CategoryAdapter(requireContext(), viewModel)
        view.categoryRecycleView.apply {
            layoutManager = LinearLayoutManager(
                context,
                RecyclerView.HORIZONTAL,
                false
            ) // default orientation is vertical
            adapter = categoryAdapter;
            isNestedScrollingEnabled = false
            setHasFixedSize(true)
        }

    }
    fun subcategoryRecycleView() {
        subcategoryAdapter = SubCategoryAdapter(requireContext(), viewModel)
        view.subCategoryRecycleView.apply {
            layoutManager = LinearLayoutManager(
                context,
                RecyclerView.HORIZONTAL,
                false
            ) // default orientation is vertical
            adapter = subcategoryAdapter;
          isNestedScrollingEnabled = false
            setHasFixedSize(true)
        }

    }

    fun productsRecycleView() {
        productsAdapter = ProductsAdapter(requireContext())
        view.productsRecycleView.apply {
            layoutManager = LinearLayoutManager(context) // default orientation is vertical
            adapter = productsAdapter
            isNestedScrollingEnabled = false
            setHasFixedSize(true)

        }

    }

    fun getAllData() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect {
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

                                view.progress.isVisible = it.progress

                        } else {
                       //      view.subCategoryRecycleView.isVisible = it.subcategoryVisibility!!
                            //      productsAdapter.submitList(it.filterDataByCategory)
                         try {
                            view.brandsData = it.allBrandsData

                            productsAdapter.submitList(it.filteredData)

                            categoryAdapter.submitList(it.categoryData!!.categories)

                            subcategoryAdapter.submitList(it.categoryData!!.categories!![it.category_position].subcats)
                            view.progress.isVisible = false

                             view.productData= it.filteredData!![0]

                         }catch (e:Exception){
                          WARN_MotionToast("not found",requireActivity())
                         }
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