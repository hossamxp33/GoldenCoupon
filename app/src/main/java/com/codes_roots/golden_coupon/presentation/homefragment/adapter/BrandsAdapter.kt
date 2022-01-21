package com.codes_roots.golden_coupon.presentation.homefragment.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codes_roots.golden_coupon.R
import com.codes_roots.golden_coupon.databinding.BrandItemAdapterBinding
import com.codes_roots.golden_coupon.entites.brandsmodel.Brand
import com.codes_roots.golden_coupon.presentation.MainActivity


class BrandsAdapter(var context: Context?) :
    ListAdapter<Brand, ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val binding: BrandItemAdapterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(p0.context),
            R.layout.brand_item_adapter, p0, false)

        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, currentList[position])


    }

}

private class DiffCallback : DiffUtil.ItemCallback<Brand>() {

    override fun areItemsTheSame(
        oldItem: Brand, newItem: Brand
    ) =
        oldItem.id == newItem.id

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: Brand, newItem: Brand
    ) =
        oldItem == newItem
}


class ViewHolder(
    val binding: BrandItemAdapterBinding
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(context: Context?, data: Brand) {

    //    binding.listener = ClickHandler()
          binding.data = data
        binding.context = context as MainActivity?
    }


}
