package com.codes_roots.golden_coupon.presentation.homefragment.adapter

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
import com.codes_roots.golden_coupon.databinding.BrandItemAdapterBinding
import com.codes_roots.golden_coupon.entites.brandsmodel.Brand
import com.codes_roots.golden_coupon.helper.ClickHandler
import com.codes_roots.golden_coupon.helper.PreferenceHelper
import com.codes_roots.golden_coupon.presentation.homefragment.mvi.MainIntent
import com.codes_roots.golden_coupon.presentation.homefragment.mvi.MainViewModel
import com.codes_roots.golden_coupon.presentation.homefragment.mvi.MainViewState
import com.codes_roots.golden_coupon.presentation.mainactivity.MainActivity
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


class BrandsAdapter(var context: Context?, var viewModel: MainViewModel) :
    ListAdapter<Brand, ViewHolder>(DiffCallback()) {
    var Intent: Channel<MainIntent>? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val binding: BrandItemAdapterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(p0.context),
            R.layout.brand_item_adapter, p0, false
        )

        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, currentList[position])
        val viewState = viewModel.state.value

        if (!currentList[position].favourite_items.isNullOrEmpty())
            holder.binding.favoriteIcon.setImageResource(R.drawable.star)
        else
            holder.binding.favoriteIcon.setImageResource(R.drawable.star_out)

        try {
            val couponText = context!!.getString(R.string.coupon)
            val num = currentList[position].items?.get(0)?.sum.toString()
            holder.binding.couponNum.text = "$num $couponText"

            holder.binding.favoriteIcon.setOnClickListener {

                viewModel.intents.trySend(
                    MainIntent
                        .AddToFavorite(
                            viewModel.state.value!!,
                            currentList[position].id,
                            (context as MainActivity).preferenceHelper.UserId
                        )
                )
                if (currentList[position].favourite_items.isNullOrEmpty())
                    holder.binding.favoriteIcon.setImageResource(R.drawable.star)
                else
                    holder.binding.favoriteIcon.setImageResource(R.drawable.star_out)



            }


        } catch (e: Exception) {
        }


    }

}


private class DiffCallback : DiffUtil.ItemCallback<Brand>() {

    override fun areItemsTheSame(
        oldItem: Brand, newItem: Brand,
    ) =
        oldItem.id == newItem.id

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: Brand, newItem: Brand,
    ) =
        oldItem == newItem
}


class ViewHolder(
    val binding: BrandItemAdapterBinding,
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(context: Context?, data: Brand) {

        binding.listener = ClickHandler()
        binding.data = data
        binding.context = context as MainActivity?
        binding.string = data.image
        binding.pref = (context as MainActivity).preferenceHelper

    }


}
