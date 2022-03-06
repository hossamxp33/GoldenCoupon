package com.codesroots.goldencoupon.presentation.menufragment

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codesroots.goldencoupon.R
import com.codesroots.goldencoupon.databinding.StaticPageItemBinding
import com.codesroots.goldencoupon.entites.staticpages.StaticPagesItem
import com.codesroots.goldencoupon.helper.ClickHandler
import com.codesroots.goldencoupon.presentation.mainactivity.MainActivity


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
