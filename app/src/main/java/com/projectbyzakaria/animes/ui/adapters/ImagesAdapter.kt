package com.projectbyzakaria.animes.ui.adapters

import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.projectbyzakaria.animes.R

class ImagesAdapter(
   val listOfImages:List<String?>,val onClick:(posi:Int)->Unit
) :RecyclerView.Adapter<ImagesAdapter.ItemImage>() {

    class ItemImage(view:View):RecyclerView.ViewHolder(view) {
        val image = view as ImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemImage {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.images,parent,false)
        return ItemImage(item)
    }

    override fun getItemCount(): Int {
        return listOfImages.size
    }

    override fun onBindViewHolder(holder: ItemImage, position: Int) {
        holder.image.setOnClickListener { onClick(position) }
        listOfImages[position]?.let {
            Glide.with(holder.itemView.context).load(it).placeholder(R.drawable.loading).error(R.drawable.error).into(holder.image)
        }

    }


}