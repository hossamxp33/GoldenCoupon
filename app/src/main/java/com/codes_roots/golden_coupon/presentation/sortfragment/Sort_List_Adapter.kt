package com.codes_roots.golden_coupon.presentation.sortfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.codes_roots.golden_coupon.R
import com.codes_roots.golden_coupon.databinding.SortListBinding
import com.codes_roots.golden_coupon.presentation.productoffersfragment.mvi.ProductsViewModel
import com.codes_roots.golden_coupon.presentation.sortfragment.SortFragment


class Sort_List_Adapter(
    var viewModel: ProductsViewModel,
    var context: SortFragment?,
    var data: List<String>,
) : RecyclerView.Adapter<CustomViewHolders>() {

    var row_index: Int? = -1

    override fun getItemCount(): Int {
        return data.size
    }


    override fun onBindViewHolder(p0: CustomViewHolders, position: Int) {
        p0.bind(data[position])


        p0.binding.checkBox.setOnClickListener {
            row_index = position
            context!!.selectedSortOption = position
        }
        p0.binding.checkBox.isChecked = row_index == position

    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CustomViewHolders {
        val binding: SortListBinding = DataBindingUtil.inflate(LayoutInflater.from(p0.context),
            R.layout.sort_list, p0, false)


        return CustomViewHolders(binding)
    }


}

class CustomViewHolders(
    var binding: SortListBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: String) {
        binding.data = data
    }

}

