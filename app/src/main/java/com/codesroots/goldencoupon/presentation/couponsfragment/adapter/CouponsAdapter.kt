package com.codesroots.goldencoupon.presentation.couponsfragment.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
import com.codesroots.goldencoupon.databinding.CouponItemAdapterBinding
import com.codesroots.goldencoupon.entites.coupons.CouponItem

import com.codesroots.goldencoupon.helper.ClickHandler
import com.codesroots.goldencoupon.presentation.couponsfragment.CouponsFragment
import com.codesroots.goldencoupon.presentation.couponsfragment.viewmodel.CouponsViewModel
import com.codesroots.goldencoupon.presentation.web_view.WebViewActivity


class CouponsAdapter(
    var context: Context?,
    var fragment: CouponsFragment,
    var viewmodel: CouponsViewModel,
) :
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
        holder.binding.image = fragment.brandImage



        holder.binding.copyButton.setOnClickListener {
            viewmodel.getUsedCoupons(currentList[position].id)
            ClickHandler().setClipboard(context as MainActivity,currentList[position].discount_code!!,currentList[position].discount_code!!)
        }
    holder.binding.visitSiteButton.setOnClickListener {

        if (!currentList[position].url.isNullOrEmpty()||!currentList[position].url_en.isNullOrEmpty()) {

            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra("url", currentList[position].url);
            intent.putExtra("url_en", currentList[position].url_en);

            (context as MainActivity).startActivity(intent)
          //  notifyItemChanged(position)
        }

        }



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
        binding.pref = (context as MainActivity).preferenceHelper

    }


}
