package com.example.musicfun.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.musicfun.R
import com.example.musicfun.databinding.NewSongsRecyclerRowBinding
import com.example.musicfun.databinding.PopularSongsRecyclerRowBinding
import com.example.musicfun.models.SongModel

//import com.google.firebase.firestore.FirebaseFirestore
//import np.com.bimalkafle.musicstream.SongsListActivity
//import np.com.bimalkafle.musicstream.databinding.SongListItemRecyclerRowBinding
//import np.com.bimalkafle.musicstream.models.SongModel

class NewSongAdapter(private val songIdList : List<SongModel>) :
    RecyclerView.Adapter<NewSongAdapter.MyViewHolder>() {

    class MyViewHolder(private val binding: NewSongsRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){
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