package com.codesroots.goldencoupon.presentation.dealsfragment.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codesroots.goldencoupon.R

import com.codesroots.goldencoupon.presentation.homefragment.mvi.MainIntent
import com.codesroots.goldencoupon.presentation.homefragment.mvi.MainViewState
import com.codesroots.goldencoupon.presentation.mainactivity.MainActivity
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import android.view.View
import androidx.navigation.Navigation
import com.codesroots.goldencoupon.databinding.DealsAdapterBinding
import com.codesroots.goldencoupon.entites.deals.DealsItem
import com.codesroots.goldencoupon.helper.ClickHandler


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
