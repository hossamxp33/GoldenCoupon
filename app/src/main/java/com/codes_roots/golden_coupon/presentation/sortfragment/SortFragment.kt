package com.codes_roots.golden_coupon.presentation.sortfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codes_roots.golden_coupon.R
import com.codes_roots.golden_coupon.databinding.SortFragmentBinding
import com.codes_roots.golden_coupon.di.WARN_MotionToast
import com.codes_roots.golden_coupon.entites.allbrands.AllBrandsModel
import com.codes_roots.golden_coupon.entites.products.Product
import com.codes_roots.golden_coupon.helper.BaseApplication
import com.codes_roots.golden_coupon.helper.ClickHandler
import com.codes_roots.golden_coupon.presentation.homefragment.adapter.BrandsAdapter
import com.codes_roots.golden_coupon.presentation.mainactivity.MainActivity
import com.codes_roots.golden_coupon.presentation.productoffersfragment.adapter.category.CategoryAdapter
import com.codes_roots.golden_coupon.presentation.productoffersfragment.mvi.MainIntent
import com.codes_roots.golden_coupon.presentation.productoffersfragment.mvi.ProductsViewModel

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class SortFragment @Inject constructor(var viewModel: ProductsViewModel, var data: Product,var brandsData :AllBrandsModel) :
    BottomSheetDialogFragment() {

    lateinit var brandsAdapter: AllBrandsAdapter

    companion object {
        const val TAG = "TownBottomSheetDialogFragment"
    }


    lateinit var sortListAdapter: Sort_List_Adapter

    lateinit var view: SortFragmentBinding
    var selectedSortOption = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BaseApplication.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        view = DataBindingUtil.inflate(inflater,
            R.layout.sort_fragment, container, false)
        view.listener = ClickHandler()
        view.context = context as MainActivity
        val viewState = viewModel.state.value


        view.ButtonClick.setOnClickListener {

            if (data.productsizes!!.isNotEmpty()) {
                if (selectedSortOption == 0) {
                    viewModel.intents.trySend(MainIntent.InitializeData(viewState!!,
                        sort = "name",
                        country_id = 8,
                        cat_id = 4))
                } else if (selectedSortOption == 1) {
                    viewModel.intents.trySend(MainIntent.InitializeData(viewState!!,
                        sort = "price",
                        country_id = 8,
                        cat_id = 4))
                }
                //            else if (SelectedSortOption == 2) {
//                viewmodel.intents.trySend(MainIntent.SortByDeliveryCost(viewstate))
//            } else if (SelectedSortOption == 3) {
//                viewmodel.intents.trySend(MainIntent.SortByDeliveryRating(viewstate))
                //    }
                this.dismiss()
            }
        else {
                WARN_MotionToast("er", requireActivity())
            }

        }
        val data = arrayListOf(getString(R.string.sortbyName),
            getString(R.string.name),
            getString(R.string.price))

        sortListAdapter = Sort_List_Adapter(viewModel, this, data)

        view.sortRecycle.apply { adapter = sortListAdapter }
        brandsRecycleView()
        brandsAdapter.submitList(brandsData.Brands)

        return view.root
    }

    fun brandsRecycleView() {
        brandsAdapter = AllBrandsAdapter(requireContext(), viewModel)
        view.brandsRecycle.apply {
            layoutManager = LinearLayoutManager(
                context,
                RecyclerView.HORIZONTAL,
                false
            ) // default orientation is vertical
            adapter = brandsAdapter;
            isNestedScrollingEnabled = false
            setHasFixedSize(true)
        }

    }
}