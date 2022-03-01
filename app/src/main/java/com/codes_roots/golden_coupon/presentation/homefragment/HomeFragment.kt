package com.codes_roots.golden_coupon.presentation.homefragment

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codes_roots.golden_coupon.R
import com.codes_roots.golden_coupon.databinding.HomeFragmentBinding
import com.codes_roots.golden_coupon.di.Error_MotionToast
import com.codes_roots.golden_coupon.entites.brandsmodel.Brand
import com.codes_roots.golden_coupon.helper.BaseApplication
import com.codes_roots.golden_coupon.helper.ClickHandler
import com.codes_roots.golden_coupon.presentation.favfragment.mvi.MainViewState
import com.codes_roots.golden_coupon.presentation.mainactivity.MainActivity
import com.codes_roots.golden_coupon.presentation.homefragment.adapter.BrandsAdapter
import com.codes_roots.golden_coupon.presentation.homefragment.mvi.MainIntent
import com.codes_roots.golden_coupon.presentation.homefragment.mvi.MainViewModel
import com.codes_roots.golden_coupon.presentation.homefragment.mvi.UserError

import kotlinx.android.synthetic.main.bottom_nav_content.*
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.coroutines.flow.collect
import org.jetbrains.anko.inputMethodManager
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


open class HomeFragment @Inject constructor() : Fragment() {
    private val REQUEST_CODE_STT = 102
    lateinit var brandsAdapter: BrandsAdapter
    var filteredData = ArrayList<Brand>()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel by viewModels<MainViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        //   childFragmentManager.fragmentFactory = fragmentFactory!!
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            BaseApplication.appComponent.inject(this)
            viewModel.intents.trySend(MainIntent.Initialize(viewModel.state.value!!.copy(progress = true),
                page))

        }
    }

    internal var page = 1

    private lateinit var view: HomeFragmentBinding
//    lateinit var newOffersAdapter: New_Offers_Adapter
//    lateinit var MainAdapter: Main_Adapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {

        view = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)

        view.searchLayout.context = context as MainActivity

        //   view.searchLayout.listener = ClickHandler()

        view.context = context as MainActivity


        brandsRecycleView()

        getAllData()


        view.searchLayout.searchBar.setOnEditorActionListener { text, actionId, event ->
            if (actionId == IME_ACTION_SEARCH) {
                filteredData.clear()
                shimmer_view_container.startShimmerAnimation()
                hideKeyboard(text)
                viewModel.intents.trySend(MainIntent.SearchByName(viewModel.state.value!!,
                    view.searchLayout.searchBar.text!!.toString()
                ))
                true
            }
            false
        };

//        view.searchLayout.searchBar.doOnTextChanged { text, start, before, count ->
//            viewModel.intents.trySend(MainIntent.SearchByName(viewModel.state.value!!,
//                text.toString()))
//        }

        view.searchLayout.searchIcon.setOnClickListener {
            filteredData.clear()
            shimmer_view_container.startShimmerAnimation()
            viewModel.intents.trySend(MainIntent.SearchByName(viewModel.state.value!!,
                view.searchLayout.searchBar.text!!.toString()

            ))
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
                Toast.makeText(requireContext(),
                    "Your device does not support STT.",
                    Toast.LENGTH_LONG).show()
            }
        }




        return view.root
    }

    private fun hideKeyboard(view: View) {
        view.apply {
            val imm = requireContext().inputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun brandsRecycleView() {
        page = 1
        brandsAdapter = BrandsAdapter(requireContext(), viewModel)
        view.brandsRecycleView.apply {
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val lastVisibleItem =
                        (Objects.requireNonNull(recyclerView.layoutManager) as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                 if (filteredData!=null)
                    if (lastVisibleItem == brandsAdapter.itemCount - 1 && brandsAdapter.itemCount >= 19 && lastVisibleItem != filteredData.size) {
                        page++
                        viewModel.intents.trySend(MainIntent.Initialize(viewModel.state.value!!,
                            page))
                        view.progress.isVisible = true

                    }
                }

            })
            layoutManager = LinearLayoutManager(context) // default orientation is vertical
            adapter = brandsAdapter;
            //  isNestedScrollingEnabled = false
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
                        filteredData.clear()
                        viewModel.intents.trySend(MainIntent.SearchByName(viewModel.state.value!!,
                                      recognizedText.toString()))

                    }
                }
            }
        }
    }


    fun getAllData() {

        lifecycleScope.launchWhenStarted {
            viewModel.state.collect {
                if (it != null) {
                    if (it.error != null) {
                        it.error?.let {Error ->
                            when (Error) {
                                is UserError.InvalidId -> "Invalid id"
                                is UserError.NetworkError -> Error.throwable.message
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
                        } else {
                            try {
                                filteredData.addAll(it.filteredData!!)
                                brandsAdapter.submitList(filteredData)
                                brandsAdapter.notifyDataSetChanged()

                            } catch (e: Exception) {
                                Toast.makeText(requireContext(),
                                    "There is no other coupons",
                                    Toast.LENGTH_LONG).show()
                            }

                            stopLoadingShimmer()
                            view.progress.isVisible = false

                        }

                    }
                }
            }

        }
    }


    fun stopLoadingShimmer() {
        shimmer_view_container?.visibility = View.GONE
        shimmer_view_container?.stopShimmerAnimation()
    }

}