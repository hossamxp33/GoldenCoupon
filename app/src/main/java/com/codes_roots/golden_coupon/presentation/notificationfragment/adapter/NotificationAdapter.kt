package com.codes_roots.golden_coupon.presentation.notificationfragment.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codes_roots.golden_coupon.R
import com.codes_roots.golden_coupon.databinding.NotificationAdapterBinding
import com.codes_roots.golden_coupon.di.WARN_MotionToast
import com.codes_roots.golden_coupon.di.setDatetext
import com.codes_roots.golden_coupon.entites.notification.NotificationData
import com.codes_roots.golden_coupon.entites.notification.NotificationModel
import com.codes_roots.golden_coupon.helper.ClickHandler
import com.codes_roots.golden_coupon.presentation.mainactivity.MainActivity
import com.codes_roots.golden_coupon.presentation.notificationfragment.viewmodel.NotificationViewModel


class NotificationAdapter(
    var viewModel: NotificationViewModel, var context: Context?
) :
    ListAdapter<NotificationData, NotificationViewHolder>(BranchesDiffCallback()) {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): NotificationViewHolder {
        val binding: NotificationAdapterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(p0.context),
            R.layout.notification_adapter, p0, false)
        if (currentList[p1].created!=null)
            setDatetext(binding.date,currentList[p1].created)
        else
            WARN_MotionToast("error data",context as MainActivity)
        return NotificationViewHolder(binding)

    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(context, currentList[position])


    }

}

private class BranchesDiffCallback : DiffUtil.ItemCallback<NotificationData
        >() {

    override fun areItemsTheSame(
        oldItem: NotificationData, newItem: NotificationData
    ) =
        oldItem.id == newItem.id

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: NotificationData, newItem: NotificationData
    ) =
        oldItem == newItem
}


class NotificationViewHolder(
    val binding: NotificationAdapterBinding
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(context: Context?, data: NotificationData) {

        binding.listener = ClickHandler()
        binding.data = data
        binding.context = context as MainActivity?
    }


}
