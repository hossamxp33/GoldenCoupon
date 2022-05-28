package com.codesroots.goldencoupon.presentation.mainactivity.whatsapp

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codesroots.goldencoupon.R
import com.codesroots.goldencoupon.databinding.WhatsappAdapterBinding

import com.codesroots.goldencoupon.entites.whatsapp.Data
import com.codesroots.goldencoupon.helper.ClickHandler
import com.codesroots.goldencoupon.helper.ResourceUtil
import com.codesroots.goldencoupon.presentation.homefragment.mvi.MainViewModel
import com.codesroots.goldencoupon.presentation.mainactivity.MainActivity


class WhatsAppAdapter(
    var viewModel: MainViewModel, var context: Context?
) :
    ListAdapter<Data, NotificationViewHolder>(BranchesDiffCallback()) {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): NotificationViewHolder {
        val binding: WhatsappAdapterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(p0.context),
            R.layout.whatsapp_adapter, p0, false)

        return NotificationViewHolder(binding)

    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(context, currentList[position])
        val phone =  currentList[position].phone
        holder.binding.Mview.setOnClickListener {
            ResourceUtil().openWhatsApp(context as MainActivity,
                phone,  ""
                )
        }
    }

}

private class BranchesDiffCallback : DiffUtil.ItemCallback<Data>() {

    override fun areItemsTheSame(
        oldItem: Data, newItem: Data
    ) =
        oldItem.id == newItem.id

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: Data, newItem: Data
    ) =
        oldItem == newItem
}


class NotificationViewHolder(
    val binding: WhatsappAdapterBinding
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(context: Context?, data: Data) {

        binding.listener = ClickHandler()
        binding.data = data
        binding.context = context as MainActivity?
    }


}
