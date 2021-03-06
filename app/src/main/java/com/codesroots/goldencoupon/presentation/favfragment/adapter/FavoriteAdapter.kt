package com.codesroots.goldencoupon.presentation.favfragment.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codesroots.goldencoupon.R

import com.codesroots.goldencoupon.presentation.mainactivity.MainActivity
import kotlinx.coroutines.channels.Channel
import android.view.View
import android.widget.Toast
import androidx.navigation.Navigation
import com.codesroots.goldencoupon.databinding.FavItemAdapterBinding
import com.codesroots.goldencoupon.entites.fav.FavoriteData
import com.codesroots.goldencoupon.helper.ClickHandler
import com.codesroots.goldencoupon.presentation.favfragment.FavoriteFragment
import com.codesroots.goldencoupon.presentation.favfragment.mvi.FavViewModel
import com.codesroots.goldencoupon.presentation.favfragment.mvi.MainIntent


class FavoriteAdapter(
    var context: Context?,
    var viewModel: FavViewModel,
    var fragment: FavoriteFragment,
) :
    ListAdapter<FavoriteData, ViewHolder>(DiffCallback()) {
    var Intent: Channel<MainIntent>? = null


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val binding: FavItemAdapterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(p0.context),
            R.layout.fav_item_adapter, p0, false)



        return ViewHolder(binding)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {

        holder.bind(context, currentList[p1])
        try {
            if ((context as MainActivity).preferenceHelper.lang!!.contains("ar"))
                holder.binding.brandName.text = currentList[p1].brand.name
            else
                holder.binding.brandName.text = currentList[p1].brand.name_en


        holder.binding.favIcon.setOnClickListener {
            try {
            holder.binding.favIcon.setImageResource(R.drawable.star_out)
            viewModel.intents.trySend(
                MainIntent
                    .DeleteFavorite(
                        viewModel.state.value!!,
                        currentList[p1].brand.id
                  ))
            fragment.data!!.removeAt(p1)
            notifyItemRemoved(p1)
            notifyItemChanged(p1)
            } catch (e: Exception) {
                Toast.makeText(context,"wait",Toast.LENGTH_SHORT).show()
            }
        }

        } catch (e: Exception) {
            Toast.makeText(context,"wait",Toast.LENGTH_SHORT).show()
        }
    }
}

fun onClick(view: View?) {
    Navigation.findNavController(view!!).navigate(R.id.action_offer_to_coupons)
}

private class DiffCallback : DiffUtil.ItemCallback<FavoriteData>() {
    override fun areItemsTheSame(
        oldItem: FavoriteData,
        newItem: FavoriteData,
    ) =
        oldItem.id == newItem.id

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: FavoriteData, newItem: FavoriteData) =
        oldItem == newItem
}

class ViewHolder(
    val binding: FavItemAdapterBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(context: Context?, data: FavoriteData) {

        binding.listener = ClickHandler()

        binding.productData = data
        binding.context = context as MainActivity?
        binding.pref = (context as MainActivity).preferenceHelper
        binding.string = data.brand.image
    }
}
