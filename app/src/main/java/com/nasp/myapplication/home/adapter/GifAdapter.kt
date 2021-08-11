package com.nasp.myapplication.home.adapter

import android.net.Uri
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.backends.pipeline.Fresco
import com.nasp.myapplication.R
import com.nasp.myapplication.data.local.Items
import com.nasp.myapplication.databinding.GifItemBinding


class GifAdapter(private val itemClickListener: OnItemClickListener) :
    ListAdapter<Items, GifAdapter.GifViewHolder>(object :
        DiffUtil.ItemCallback<Items>() {
        override fun areItemsTheSame(oldItem: Items, newItem: Items): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Items, newItem: Items): Boolean {
            return oldItem == newItem
        }
    }) {

    var favourites: List<String> = emptyList()
        set(value) {
            if (field == value)
                return
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val itemBinding = GifItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val holder = GifViewHolder(itemBinding)

        itemBinding.favouriteButton.setOnClickListener {
            it?.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
            val item = getItem(holder.adapterPosition)
            itemClickListener.onItemClicked(
                item.copy(
                    isFavorites = !item.isFavorites
                )
            )
        }

        itemBinding.shareButton.setOnClickListener {
            it?.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
            val item = getItem(holder.adapterPosition)
            itemClickListener.onItemClicked(item, true)
        }

        return holder
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        val item = getItem(position)
        item.isFavorites = favourites.contains(item.id)
        holder.bind(item)
    }

    class GifViewHolder(private val itemBinding: GifItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(items: Items) {
            with(itemBinding) {
                val controller = Fresco.newDraweeControllerBuilder()
                    .setUri(Uri.parse(items.webpUrl))
                    .setOldController(gif.controller)
                    .setAutoPlayAnimations(true)
                    .build()

                gif.hierarchy.setPlaceholderImage(R.drawable.progress_anim)
                gif.controller = controller

                favouriteButton.isSelected = items.isFavorites

            }
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(items: Items, share: Boolean = false)
    }
}