package com.codesroots.goldencoupon.presentation.sortfragment

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codesroots.goldencoupon.R
import com.codesroots.goldencoupon.databinding.BrandsItemAdapterBinding
import com.codesroots.goldencoupon.di.WARN_MotionToast
import com.codesroots.goldencoupon.entites.allbrands.Brand
import com.codesroots.goldencoupon.presentation.mainactivity.MainActivity
import com.codesroots.goldencoupon.presentation.productoffersfragment.mvi.MainIntent
import com.codesroots.goldencoupon.presentation.productoffersfragment.mvi.ProductsViewModel
import kotlinx.coroutines.channels.Channel


class AllBrandsAdapter(var context: Context?, var viewModel: ProductsViewModel?) :
    ListAdapter<Brand, ViewHolder>(DiffCallback()) {
    var Intent: Channel<MainIntent>?=null
    var row_index: Int? = -1

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val binding: BrandsItemAdapterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(p0.context),
            R.layout.brands_item_adapter, p0, false
        )

        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, currentList[position])

        holder.binding.Mview.setOnClickListener {
            if (!currentList.isNullOrEmpty()) {
                row_index = position

                val viewState = viewModel?.state?.value
                viewModel?.FilterFileds?.put("Filter[brand_id]",currentList[position].id.toString())

                notifyDataSetChanged()
                notifyItemChanged(position)
            }else
                WARN_MotionToast("",context as MainActivity)
        }
/// Text  underLine  when selected
        if (row_index == position) {
            holder.binding.Mview.setBackgroundDrawable(
                ContextCompat
                    .getDrawable(context!!, R.drawable.red_cat_bg))
        } else {
            holder.binding.Mview.setBackgroundDrawable(
                ContextCompat
                    .getDrawable(context!!, R.drawable.coupon_bg))
        }
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
    val binding: BrandsItemAdapterBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(context: Context?, data: Brand) {

        //    binding.listener = ClickHandler()
        binding.data = data
        binding.context = context as MainActivity?
        binding.pref = (context as MainActivity).preferenceHelper
    }


}
