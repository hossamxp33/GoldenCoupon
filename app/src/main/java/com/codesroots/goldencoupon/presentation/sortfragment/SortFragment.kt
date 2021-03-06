package com.codesroots.goldencoupon.presentation.sortfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codesroots.goldencoupon.R
import com.codesroots.goldencoupon.databinding.SortFragmentBinding
import com.codesroots.goldencoupon.helper.BaseApplication
import com.codesroots.goldencoupon.helper.ClickHandler
import com.codesroots.goldencoupon.presentation.mainactivity.MainActivity
import com.codesroots.goldencoupon.presentation.productoffersfragment.mvi.MainIntent
import com.codesroots.goldencoupon.presentation.productoffersfragment.mvi.ProductsViewModel

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class SortFragment @Inject constructor(var viewModel: ProductsViewModel) :
    BottomSheetDialogFragment() {

    lateinit var brandsAdapter: AllBrandsAdapter

    companion object {
        const val TAG = "TownBottomSheetDialogFragment"
    }


    lateinit var sortListAdapter: Sort_List_Adapter
    var sortValue:String? = ""
    lateinit var view: SortFragmentBinding
    var selectedSortOption = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BaseApplication.appComponent.inject(this)
     }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        view = DataBindingUtil.inflate(inflater,
            R.layout.sort_fragment, container, false)
        view.listener = ClickHandler()
        view.context = context as MainActivity
        val viewState = viewModel.state.value

        viewModel.intents.trySend(MainIntent.GetBrandList(viewModel.state.value!!))

        view.close.setOnClickListener {
            this.dismiss()



        }

        view.ButtonClick.setOnClickListener {
                if (selectedSortOption == 0) {
                    sortValue = "name"
                } else if (selectedSortOption == 1) {
                    sortValue = "percentage"
                }
            viewModel.filteredData.clear()

            viewModel.intents.trySend(
                    MainIntent.FilterData(
                        viewState!!,
                        viewModel.FilterFileds,
                        viewState.country_id, sort = sortValue
                    )
                )

                this.dismiss()



        }
        val data = arrayListOf(
            getString(R.string.name),
            getString(R.string.price))

        sortListAdapter = Sort_List_Adapter(viewModel, this, data)

        view.sortRecycle.apply { adapter = sortListAdapter }
        brandsRecycleView()
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect {
                brandsAdapter.submitList(it?.allBrandsData?.Brands)

            }
            }

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