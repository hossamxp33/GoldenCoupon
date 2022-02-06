package com.codes_roots.golden_coupon.presentation.couponsfragment.adapter

import android.R.attr
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codes_roots.golden_coupon.R
import com.codes_roots.golden_coupon.databinding.CategoryItemAdapterBinding
import com.codes_roots.golden_coupon.databinding.OfferItemAdapterBinding

import com.codes_roots.golden_coupon.entites.category.Category
import com.codes_roots.golden_coupon.entites.products.Product
import com.codes_roots.golden_coupon.presentation.homefragment.mvi.MainIntent
import com.codes_roots.golden_coupon.presentation.homefragment.mvi.MainViewState
import com.codes_roots.golden_coupon.presentation.mainactivity.MainActivity
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import android.view.View
import androidx.navigation.Navigation
import com.codes_roots.golden_coupon.databinding.CouponItemAdapterBinding
import com.codes_roots.golden_coupon.entites.coupons.CouponItem
import android.R.attr.label

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Build
import androidx.core.content.ContextCompat

import androidx.core.content.ContextCompat.getSystemService
import com.codes_roots.golden_coupon.helper.ClickHandler


class CouponsAdapter(var context: Context?) :
    ListAdapter<CouponItem, ViewHolder>(DiffCallback()) {
    var Intent: Channel<MainIntent>? = null
    var viewModel: MutableStateFlow<MainViewState?>? = null


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val binding: CouponItemAdapterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(p0.context),
            R.layout.coupon_item_adapter, p0, false
        )



        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, currentList[position])


    }

}




private class DiffCallback : DiffUtil.ItemCallback<CouponItem>() {

    override fun areItemsTheSame(
        oldItem: CouponItem, newItem: CouponItem,
    ) =
        oldItem.id == newItem.id

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: CouponItem, newItem: CouponItem,
    ) =
        oldItem == newItem
}


class ViewHolder(
    val binding: CouponItemAdapterBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(context: Context?, data: CouponItem) {
          binding.listener = ClickHandler()
        binding.data = data
        binding.context = context as MainActivity?
    }


}
