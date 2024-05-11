package com.example.musicfun.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicfun.R
import com.example.musicfun.databinding.FragmentMusicBinding
import com.example.musicfun.models.MusicModel
import com.example.musicfun.databinding.MusicItemRecyclerRowBinding


class MusicAdapter(private val musicList : List<MusicModel>): RecyclerView.Adapter<MusicAdapter.MyViewHolder>() {
    private var onClickListener: OnClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.music_item_recycler_row, parent, false)


        return MyViewHolder( MusicItemRecyclerRowBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
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
        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, currentItem )
            }
        }
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, model: MusicModel)
    }

    class MyViewHolder(binding: MusicItemRecyclerRowBinding): RecyclerView.ViewHolder(binding.root){
        val cover_image = binding.coverImageView
        val txv_title = binding.txvTitle
        val txv_author = binding.txvAuthor
    }

}