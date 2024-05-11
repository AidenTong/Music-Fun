package com.example.musicfun.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicfun.R
import com.example.musicfun.models.MusicModel
import com.example.musicfun.databinding.MusicItemRecyclerRowBinding


class MusicAdapter(private val musicList : List<MusicModel>): RecyclerView.Adapter<MusicAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.music_item_recycler_row, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = musicList[position]
        holder.txv_title.text = currentItem.title
//        holder.cover_image.setImageResource(currentItem.coverPicture)
        holder.txv_author.text = currentItem.author
        Glide.with(holder.itemView.context)
            .load(currentItem.coverUrl)
            .placeholder(R.drawable.music_placeholder) // Replace with your placeholder image
            .error(R.drawable.google_icon) // Replace with your error image
            .into(holder.cover_image)

    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val cover_image = itemView.findViewById<ImageView>(R.id.cover_image_view)
        val txv_title = itemView.findViewById<TextView>(R.id.txv_title)
        val txv_author = itemView.findViewById<TextView>(R.id.txv_author)
    }

}