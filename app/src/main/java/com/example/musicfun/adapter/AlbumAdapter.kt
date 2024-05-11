package com.example.musicfun.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.musicfun.databinding.NewSongsRecyclerRowBinding
import com.example.musicfun.models.AlbumModel
import com.example.musicfun.models.SongModel
import com.example.musicfun.ui.MV.SongListActivity

class AlbumAdapter(private val songIdList : List<AlbumModel>) :
    RecyclerView.Adapter<AlbumAdapter.MyViewHolder>() {

    class MyViewHolder(private val binding: NewSongsRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){
        //bind data with view
        fun bindData(song: AlbumModel){
            binding.nameTextView.text = song.title
            binding.nameSubtitleTextView.text = song.author
            Glide.with(binding.coverImageView).load(song.coverUrl)
                .apply(
                    RequestOptions().transform(RoundedCorners(32))
                )
                .into(binding.coverImageView)

            val context = binding.root.context
            binding.root.setOnClickListener {
                SongListActivity.albumList = song
                SongListActivity.type = "album"
                context.startActivity(Intent(context, SongListActivity::class.java))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = NewSongsRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return songIdList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(songIdList[position])
    }

}