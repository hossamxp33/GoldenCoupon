package com.codesroots.goldencoupon.presentation.productoffersfragment.adapter.sub

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codesroots.goldencoupon.R
import com.codesroots.goldencoupon.databinding.SubCategoryItemAdapterBinding
import com.codesroots.goldencoupon.entites.category.Subcat
import com.codesroots.goldencoupon.presentation.mainactivity.MainActivity
import com.codesroots.goldencoupon.presentation.productoffersfragment.ProductOffersFragment
import com.codesroots.goldencoupon.presentation.productoffersfragment.mvi.MainIntent
import com.codesroots.goldencoupon.presentation.productoffersfragment.mvi.ProductsViewModel
import kotlinx.android.synthetic.main.offers_fragment.*
import kotlinx.coroutines.channels.Channel


class SubCategoryAdapter(var context: Context?, var viewModel: ProductsViewModel?,var fragment: ProductOffersFragment) :
    ListAdapter<Subcat, ViewHolder>(DiffCallback()) {
    var Intent: Channel<MainIntent>? = null
    var row_index: Int? = -1

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val binding: SubCategoryItemAdapterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(p0.context),
            R.layout.sub_category_item_adapter, p0, false
        )

        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, currentList[position])

        holder.binding.Mview.setOnClickListener {
            viewModel!!.filteredData.clear()
            fragment.progress.isVisible = true
            row_index = position
            val viewState = viewModel?.state?.value
            viewModel?.FilterFileds?.put("Filter[subcat_id]",currentList[position].id.toString())

            viewModel!!.intents.trySend(
                MainIntent.FilterData(
                    viewState?.copy( page = 1),
                    viewModel?.FilterFileds!!,
                    viewState?.country_id
                )
            )

            notifyDataSetChanged()
            notifyItemChanged(position)

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

private class DiffCallback : DiffUtil.ItemCallback<Subcat>() {

    override fun areItemsTheSame(
        oldItem: Subcat, newItem: Subcat,
    ) =
        oldItem.id == newItem.id

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: Subcat, newItem: Subcat,
    ) =
        oldItem == newItem
}


class ViewHolder(
    val binding: SubCategoryItemAdapterBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(context: Context?, data: Subcat) {

        //    binding.listener = ClickHandler()
        binding.data = data
        binding.context = context as MainActivity?
        binding.pref = (context as MainActivity).preferenceHelper

    }


}
