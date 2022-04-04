package com.codesroots.goldencoupon.presentation.productoffersfragment

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
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
import com.codesroots.goldencoupon.R
import com.codesroots.goldencoupon.databinding.OffersFragmentBinding
import com.codesroots.goldencoupon.di.WARN_MotionToast
import com.codesroots.goldencoupon.entites.products.Product
import com.codesroots.goldencoupon.helper.BaseApplication
import com.codesroots.goldencoupon.helper.ClickHandler
import com.codesroots.goldencoupon.helper.PreferenceHelper
import com.codesroots.goldencoupon.presentation.homefragment.mvi.UserError
import com.codesroots.goldencoupon.presentation.mainactivity.MainActivity
import com.codesroots.goldencoupon.presentation.productoffersfragment.adapter.category.CategoryAdapter
import com.codesroots.goldencoupon.presentation.productoffersfragment.adapter.product.ProductsAdapter
import com.codesroots.goldencoupon.presentation.productoffersfragment.adapter.sub.SubCategoryAdapter
import com.codesroots.goldencoupon.presentation.productoffersfragment.mvi.MainIntent
import com.codesroots.goldencoupon.presentation.productoffersfragment.mvi.ProductsViewModel
import kotlinx.android.synthetic.main.bottom_nav_content.*
import kotlinx.coroutines.flow.collect

import java.util.*
import javax.inject.Inject


open class ProductOffersFragment @Inject constructor() : Fragment() {
    private val REQUEST_CODE_STT = 102
    lateinit var categoryAdapter: CategoryAdapter
    lateinit var subcategoryAdapter: SubCategoryAdapter
    lateinit var productsAdapter: ProductsAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    internal var page = 1

    val viewModel by viewModels<ProductsViewModel> { viewModelFactory }

    var filteredData = ArrayList<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            BaseApplication.appComponent.inject(this)
        }
        viewModel.intents.trySend(MainIntent.InitializeData(viewModel.state.value?.copy(
            progress = true,
            country_id = Pref.CountryId)!!,null,
            0,Pref.CountryId,country_id = Pref.CountryId))


    }

    private lateinit var view: OffersFragmentBinding

    @Inject
    lateinit var Pref: PreferenceHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {

        view = DataBindingUtil.inflate(inflater, R.layout.offers_fragment, container, false)

        view.context = context as MainActivity
        view.listener = ClickHandler()
        view.viewModel = viewModel
        (context as MainActivity).bottom_nav_bar.menu.getItem(1).isChecked = true

        view.searchLayout.searchBar.doOnTextChanged { text, start, before, count ->
            viewModel.FilterFileds.put("Filter[name]", text.toString())
            viewModel.filteredData.clear()
            viewModel.intents.trySend(
                MainIntent.FilterData(
                    viewModel.state.value!!,
                    viewModel.FilterFileds,
                    viewModel.state.value!!.country_id
                )
            )
        }
        view.productsRecycleView.apply {
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)


                    val lastVisibleItem =
                        (Objects.requireNonNull(recyclerView.layoutManager) as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                    if (filteredData!=null)
                        if (lastVisibleItem == productsAdapter.itemCount - 1 && productsAdapter.itemCount >= 19 && lastVisibleItem != filteredData.size) {

                            page++

                            viewModel.intents.trySend(MainIntent.Paging(viewModel.state.value?.copy(
                                progress = true,
                                page=page,
                                country_id = Pref.CountryId)!!,
                                Pref.CountryId,  page))
                            view.progress.isVisible = true

                        }


                }

            })
            layoutManager = LinearLayoutManager(context) // default orientation is vertical
          //  adapter = productsAdapter;
            //  isNestedScrollingEnabled = false
            setHasFixedSize(true)
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

    private fun myView() {
        getAllData()
        categoryRecycleView()
        productsRecycleView()
        subcategoryRecycleView()
    }

    private fun categoryRecycleView() {
        categoryAdapter = CategoryAdapter(requireContext(), viewModel, this)
        view.categoryRecycleView.apply {
            layoutManager = LinearLayoutManager(
                context,
                HORIZONTAL,
                false
            )
            adapter = categoryAdapter;
            isNestedScrollingEnabled = false
            setHasFixedSize(true)
        }

    }

    private fun subcategoryRecycleView() {
        subcategoryAdapter = SubCategoryAdapter(requireContext(), viewModel,this)
        view.subCategoryRecycleView.apply {
            layoutManager = LinearLayoutManager(
                context,
                HORIZONTAL,
                false
            )
            adapter = subcategoryAdapter;
            isNestedScrollingEnabled = false
            setHasFixedSize(true)
        }

    }

    private fun productsRecycleView() {
        page = 1
        productsAdapter = ProductsAdapter(requireContext())
        view.productsRecycleView.apply {
            layoutManager = LinearLayoutManager(context) // default orientation is vertical
            adapter = productsAdapter
            //setHasFixedSize(true)

        }

    }

    private fun getAllData() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect {
                if (it != null) {
                    if (it.error != null) {
                        view.progress.isVisible = false

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

                        } else {
                            view.progress.isVisible = false

                            try {
                          viewModel.filteredData.addAll(it.filteredData!!)


                                view.brandsData = it.allBrandsData

                                productsAdapter.submitList( viewModel.filteredData)
                             productsAdapter.notifyDataSetChanged()

                                categoryAdapter.submitList(it.categoryData!!.categories)

                                subcategoryAdapter.submitList(it.categoryData!!.categories!![it.category_position].subcats)


                                view.productData = it.filteredData!![0]

                            } catch (e: Exception) {
                                WARN_MotionToast("not found", requireActivity())
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