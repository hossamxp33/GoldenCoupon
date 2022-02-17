package com.codes_roots.golden_coupon.presentation.productoffersfragment.adapter.category

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codes_roots.golden_coupon.R
import com.codes_roots.golden_coupon.databinding.BrandItemAdapterBinding
import com.codes_roots.golden_coupon.databinding.CategoryItemAdapterBinding
import com.codes_roots.golden_coupon.di.WARN_MotionToast
import com.codes_roots.golden_coupon.entites.brandsmodel.Brand
import com.codes_roots.golden_coupon.entites.category.Category
import com.codes_roots.golden_coupon.presentation.homefragment.mvi.MainViewState
import com.codes_roots.golden_coupon.presentation.mainactivity.MainActivity
import com.codes_roots.golden_coupon.presentation.productoffersfragment.mvi.MainIntent
import com.codes_roots.golden_coupon.presentation.productoffersfragment.mvi.ProductsViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow


class CategoryAdapter(var context: Context?, var viewModel: ProductsViewModel?) :
    ListAdapter<Category, ViewHolder>(DiffCallback()) {
    var Intent: Channel<MainIntent>?=null
    var row_index: Int? = -1

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val binding: CategoryItemAdapterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(p0.context),
            R.layout.category_item_adapter, p0, false
        )

        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, currentList[position])

        holder.binding.Mview.setOnClickListener {
  if (!currentList.isNullOrEmpty()) {
      row_index = position
      val viewState = viewModel?.state?.value
      viewModel?.FilterFileds?.put("Filter[cat_id]",currentList[position].id.toString())

      viewModel!!.intents.trySend(
          MainIntent.FilterData(
              viewState!!,
               viewModel?.FilterFileds,
                       viewState!!.country_id
          )
      )

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

private class DiffCallback : DiffUtil.ItemCallback<Category>() {

    override fun areItemsTheSame(
        oldItem: Category, newItem: Category
    ) =
        oldItem.id == newItem.id

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: Category, newItem: Category
    ) =
        oldItem == newItem
}


class ViewHolder(
    val binding: CategoryItemAdapterBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(context: Context?, data: Category) {

        //    binding.listener = ClickHandler()
        binding.data = data
        binding.context = context as MainActivity?
        binding.pref = (context as MainActivity).preferenceHelper
    }


}
