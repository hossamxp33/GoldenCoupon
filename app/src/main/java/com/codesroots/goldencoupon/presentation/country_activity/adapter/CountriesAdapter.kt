package com.codesroots.goldencoupon.presentation.country_activity.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codesroots.goldencoupon.R
import com.codesroots.goldencoupon.databinding.CountryAdapterBinding

import com.codesroots.goldencoupon.presentation.homefragment.mvi.MainIntent
import com.codesroots.goldencoupon.presentation.homefragment.mvi.MainViewState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import com.codesroots.goldencoupon.entites.countries.Country

import com.codesroots.goldencoupon.helper.ClickHandler
import com.codesroots.goldencoupon.presentation.country_activity.CountryActivity


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
        binding.pref = (context as CountryActivity).Pref

    }


}
