package com.codes_roots.golden_coupon.presentation.country_activity.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codes_roots.golden_coupon.R
import com.codes_roots.golden_coupon.databinding.CountryAdapterBinding

import com.codes_roots.golden_coupon.presentation.homefragment.mvi.MainIntent
import com.codes_roots.golden_coupon.presentation.homefragment.mvi.MainViewState
import com.codes_roots.golden_coupon.presentation.mainactivity.MainActivity
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import com.codes_roots.golden_coupon.databinding.CouponItemAdapterBinding
import com.codes_roots.golden_coupon.entites.countries.Country
import com.codes_roots.golden_coupon.entites.coupons.CouponItem

import com.codes_roots.golden_coupon.helper.ClickHandler
import com.codes_roots.golden_coupon.presentation.country_activity.CountryActivity


class CountriesAdapter(var context: Context?) :
    ListAdapter<Country, ViewHolder>(DiffCallback()) {
    var Intent: Channel<MainIntent>? = null
    var viewModel: MutableStateFlow<MainViewState?>? = null
    var row_index: Int? = -1


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val binding: CountryAdapterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(p0.context),
            R.layout.country_adapter, p0, false
        )



        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, currentList[position])
        holder.binding.Mview.setOnClickListener {
            row_index = position

            notifyDataSetChanged()
            notifyItemChanged(position)

        }

    }

}




private class DiffCallback : DiffUtil.ItemCallback<Country>() {

    override fun areItemsTheSame(
        oldItem: Country, newItem: Country,
    ) =
        oldItem.id == newItem.id

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: Country, newItem: Country,
    ) =
        oldItem == newItem
}


class ViewHolder(
    val binding: CountryAdapterBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(context: Context?, data: Country) {
          binding.listener = ClickHandler()
        binding.data = data
        binding.context = context as CountryActivity?
    }


}
