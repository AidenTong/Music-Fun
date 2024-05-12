package com.example.musicfun.adapter

import android.R
import android.content.Intent
import android.view.LayoutInflater
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.musicfun.databinding.PopularSongsRecyclerRowBinding
import com.example.musicfun.models.SongModel
import com.example.musicfun.ui.MV.VideoActivity


class PopularSongAdapter(private val songIdList : List<SongModel>) :
    RecyclerView.Adapter<PopularSongAdapter.MyViewHolder>() {

    class MyViewHolder(private val binding: PopularSongsRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){
        //bind data with view
        fun bindData(song: SongModel){
            Glide.with(binding.coverImageView).load(song.coverUrl)
                .apply(
                    RequestOptions().transform(RoundedCorners(32))
                )
                .into(binding.coverImageView)
            binding.root.setOnClickListener{
                VideoActivity.uri = song.url
                it.context.startActivity(Intent(it.context, VideoActivity::class.java))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = PopularSongsRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return Integer.MAX_VALUE
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(songIdList[position % songIdList.size])
    }

}