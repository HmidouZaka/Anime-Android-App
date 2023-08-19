package com.projectbyzakaria.views.ui.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.projectbyzakaria.views.R
import com.projectbyzakaria.views.databinding.ItemImagesBinding

class ImagesAdapter(var onLongClick:(image:Drawable?,Url:String)->Unit) : RecyclerView.Adapter<ImagesAdapter.ImageItem>() {
    class ImageItem(view: ItemImagesBinding) : RecyclerView.ViewHolder(view.root) {
        var image = view.myImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageItem {
        val view = ItemImagesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageItem(view)
    }

    override fun getItemCount(): Int = diffList.currentList.size

    override fun onBindViewHolder(holder: ImageItem, position: Int) {
        Glide.with(holder.image).load(diffList.currentList[position]).error(R.drawable.error)
            .placeholder(R.drawable.loading).into(holder.image)
        holder.image.setOnLongClickListener {
            onLongClick(holder.image.drawable,diffList.currentList[position])
            true
        }
    }

    var differ = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    var diffList = AsyncListDiffer(this, differ)
    fun setList(listImage: List<String>) {
        diffList.submitList(listImage)
    }


}