package com.codes_roots.golden_coupon.presentation.menufragment

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codes_roots.golden_coupon.R
import com.codes_roots.golden_coupon.databinding.StaticPageItemBinding
import com.codes_roots.golden_coupon.entites.staticpages.StaticPagesItem
import com.codes_roots.golden_coupon.helper.ClickHandler
import com.codes_roots.golden_coupon.presentation.mainactivity.MainActivity


class StaticPagesAdapter(var context: Context?) :
    ListAdapter<StaticPagesItem, NotificationViewHolder>(BranchesDiffCallback()) {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): NotificationViewHolder {
        val binding: StaticPageItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(p0.context),
            R.layout.static_page_item, p0, false)

        return NotificationViewHolder(binding)

    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(context, currentList[position])

    }

}

private class BranchesDiffCallback : DiffUtil.ItemCallback<StaticPagesItem
        >() {

    override fun areItemsTheSame(
        oldItem: StaticPagesItem, newItem: StaticPagesItem
    ) =
        oldItem.id == newItem.id

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: StaticPagesItem, newItem: StaticPagesItem
    ) =
        oldItem == newItem
}


class NotificationViewHolder(
    val binding: StaticPageItemBinding
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(context: Context?, data: StaticPagesItem) {

        binding.listener = ClickHandler()
        binding.data = data
        binding.context = context as MainActivity?
        binding.pref = (context as MainActivity).preferenceHelper

    }


}
