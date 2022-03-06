package com.codesroots.goldencoupon.presentation.homefragment.adapter

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
import com.codesroots.goldencoupon.databinding.BrandItemAdapterBinding
import com.codesroots.goldencoupon.di.WARN_MotionToast
import com.codesroots.goldencoupon.entites.brandsmodel.Brand
import com.codesroots.goldencoupon.helper.ClickHandler
import com.codesroots.goldencoupon.presentation.auth.RegisterActivity
import com.codesroots.goldencoupon.presentation.homefragment.mvi.MainIntent
import com.codesroots.goldencoupon.presentation.homefragment.mvi.MainViewModel
import com.codesroots.goldencoupon.presentation.mainactivity.MainActivity
import kotlinx.coroutines.channels.Channel
import org.jetbrains.anko.image


class BrandsAdapter(var context: Context?, var viewModel: MainViewModel) :
    ListAdapter<Brand, ViewHolder>(DiffCallback()) {
    var Intent: Channel<MainIntent>? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val binding: BrandItemAdapterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(p0.context),
            R.layout.brand_item_adapter, p0,false)

        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, currentList[position])
        val viewState = viewModel.state.value

        try {

            if (!currentList[position].favourite_items.isNullOrEmpty()) {
                holder.binding.favoriteIcon.setImageResource(R.drawable.star)
                holder.binding.favoriteIcon.tag = "full"
            }
            else {
                holder.binding.favoriteIcon.setImageResource(R.drawable.star_out)
                holder.binding.favoriteIcon.tag = "outline"

            }

            val couponText = context!!.getString(R.string.coupon)
            val num = currentList[position].items?.get(0)?.sum.toString()
            holder.binding.couponNum.text = "$num $couponText"


            holder.binding.favoriteIcon.setOnClickListener {
                if ((context as MainActivity).preferenceHelper.token.isNullOrEmpty()) {
                    WARN_MotionToast(
                        (context as MainActivity).getString(R.string.loginFirst),
                        context as MainActivity
                    )
                    val i = Intent(context, RegisterActivity::class.java)
                    (context as MainActivity).startActivityForResult(i, 100)
                } else {
                    if (holder.binding.favoriteIcon.tag  != "full"
                    ) {


                        holder.binding.favoriteIcon.setImageResource(R.drawable.star)
                        holder.binding.favoriteIcon.tag = "full"
                        viewModel.intents.trySend(
                            MainIntent
                                .AddToFavorite(
                                    viewModel.state.value!!,
                                    currentList[position].id

                                )
                        )
                    } else {
                        viewModel.intents.trySend(
                            MainIntent
                                .DeleteFavorite(
                                    viewModel.state.value!!,
                                    currentList[position].id

                                )
                        )
                        holder.binding.favoriteIcon.tag = "outline"

                        holder.binding.favoriteIcon.setImageResource(R.drawable.star_out)
                    }

                }

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
