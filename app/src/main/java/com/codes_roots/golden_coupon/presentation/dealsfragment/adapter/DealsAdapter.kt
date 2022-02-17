package com.codes_roots.golden_coupon.presentation.dealsfragment.adapter

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
import com.codes_roots.golden_coupon.databinding.DealsAdapterBinding
import com.codes_roots.golden_coupon.databinding.FavItemAdapterBinding
import com.codes_roots.golden_coupon.entites.deals.DealsItem
import com.codes_roots.golden_coupon.entites.fav.FavoriteData
import com.codes_roots.golden_coupon.helper.ClickHandler


class DealsAdapter(var context: Context?) :
    ListAdapter<DealsItem, ViewHolder>(DiffCallback()) {
    var Intent: Channel<MainIntent>?=null
    var viewModel: MutableStateFlow<MainViewState?>?=null


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val binding: DealsAdapterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(p0.context),
            R.layout.deals_adapter, p0, false
        )



        return ViewHolder(binding)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        holder.bind(context, currentList[p1])
try {

}catch (e:Exception){}



    }

}

fun onClick(view: View?) {
    Navigation.findNavController(view!!).navigate(R.id.action_offer_to_coupons)
}

private class DiffCallback : DiffUtil.ItemCallback<DealsItem>() {

    override fun areItemsTheSame(
        oldItem: DealsItem, newItem: DealsItem,
    ) =
        oldItem.id == newItem.id

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: DealsItem, newItem: DealsItem,
    ) =
        oldItem == newItem
}


class ViewHolder(
    val binding: DealsAdapterBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(context: Context?, data: DealsItem) {
          binding.listener = ClickHandler()
        binding.data = data
        binding.context = context as MainActivity?
    //    binding.pref = (context as MainActivity).preferenceHelper

    }


}
