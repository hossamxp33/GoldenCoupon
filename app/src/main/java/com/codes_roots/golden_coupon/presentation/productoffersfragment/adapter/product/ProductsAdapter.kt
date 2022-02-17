package com.codes_roots.golden_coupon.presentation.productoffersfragment.adapter.product

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
import com.codes_roots.golden_coupon.helper.ClickHandler


class ProductsAdapter(var context: Context?) :
    ListAdapter<Product, ViewHolder>(DiffCallback()) {
    var Intent: Channel<MainIntent>?=null
    var viewModel: MutableStateFlow<MainViewState?>?=null


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val binding: OfferItemAdapterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(p0.context),
            R.layout.offer_item_adapter, p0, false
        )
        return ViewHolder(binding)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        holder.bind(context, currentList[p1])
try {
    holder.binding.price.text = (context as MainActivity).getString(R.string.price) + currentList[p1].productsizes!![0].start_price + " " +currentList[p1].productsizes!![0].country!!.code
    holder.binding.priceWithCode.text = (context as MainActivity).getString(R.string.priceWithCode) + currentList[p1].productsizes!![0].current_price + " " + currentList!![p1].productsizes!![0].country!!.code


}catch (e:Exception){

}



    }

}

fun onClick(view: View?) {
    Navigation.findNavController(view!!).navigate(R.id.action_offer_to_coupons)
}

private class DiffCallback : DiffUtil.ItemCallback<Product>() {

    override fun areItemsTheSame(
        oldItem: Product, newItem: Product,
    ) =
        oldItem.id == newItem.id

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: Product, newItem: Product,
    ) =
        oldItem == newItem
}


class ViewHolder(
    val binding: OfferItemAdapterBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(context: Context?, data: Product) {
          binding.listener = ClickHandler()
        binding.productData = data
        binding.context = context as MainActivity?
        binding.pref = (context as MainActivity).preferenceHelper

    }


}
