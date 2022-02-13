package com.codes_roots.golden_coupon.presentation.sortfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.codes_roots.golden_coupon.R
import com.codes_roots.golden_coupon.databinding.SortFragmentBinding
import com.codes_roots.golden_coupon.helper.BaseApplication
import com.codes_roots.golden_coupon.helper.ClickHandler
import com.codes_roots.golden_coupon.presentation.mainactivity.MainActivity
import com.codes_roots.golden_coupon.presentation.productoffersfragment.mvi.ProductsViewModel

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class SortFragment @Inject constructor() : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "TownBottomSheetDialogFragment"
    }
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewmodel by viewModels<ProductsViewModel> { viewModelFactory }

    lateinit var sortListAdapter: Sort_List_Adapter

    lateinit var view: SortFragmentBinding
    var SelectedSortOption = 0
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
//        view.ButtonClick.setOnClickListener {
//            var viewstate = viewmodel.state.value
//            if (SelectedSortOption == 0) {
//                viewmodel.intents.trySend(RestaurantsListIntent.SortByName(viewstate))
//            } else if (SelectedSortOption == 1) {
//                viewmodel.intents.trySend(RestaurantsListIntent.SortByDeliveryTime(viewstate))
//            } else if (SelectedSortOption == 2) {
//                viewmodel.intents.trySend(RestaurantsListIntent.SortByDeliveryCost(viewstate))
//            } else if (SelectedSortOption == 3) {
//                viewmodel.intents.trySend(RestaurantsListIntent.SortByDeliveryRating(viewstate))
//            }
//            this.dismiss()
//        }

        val data = arrayListOf(getString(R.string.bestDiscount),
            getString(R.string.name),
            getString(R.string.price))

        sortListAdapter = Sort_List_Adapter(viewmodel, this, data)

        view.sortRecycle.apply { adapter = sortListAdapter }


        return view.root
    }


}