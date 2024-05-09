package com.example.musicfun.adapter

import android.R
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


class PopularSongAdapter(private val songIdList : List<SongModel>) :
    RecyclerView.Adapter<PopularSongAdapter.MyViewHolder>() {

    class MyViewHolder(private val binding: PopularSongsRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){
        //bind data with view
        fun bindData(song: SongModel){
            binding.nameTextView.text = song.title
            Glide.with(binding.coverImageView).load(song.coverPicture)
                .apply(
                    RequestOptions().transform(RoundedCorners(32))
                )
                .into(binding.coverImageView)

//
//            FirebaseFirestore.getInstance().collection("songs")
//                .document(songId).get()
//                .addOnSuccessListener {
//                    val song = it.toObject(SongModel::class.java)
//                    song?.apply {
//                        binding.songTitleTextView.text = title
//                        binding.songSubtitleTextView.text = subtitle
//                        Glide.with(binding.songCoverImageView).load(coverUrl)
//                            .apply(
//                                RequestOptions().transform(RoundedCorners(32))
//                            )
//                            .into(binding.songCoverImageView)
//                    }
//                }

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