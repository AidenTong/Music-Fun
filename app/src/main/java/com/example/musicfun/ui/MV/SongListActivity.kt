package com.example.musicfun.ui.MV

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.musicfun.R
import com.example.musicfun.adapter.CategoryAdapter
import com.example.musicfun.adapter.SongListAdapter
import com.example.musicfun.databinding.ActivitySongListBinding
import com.example.musicfun.models.AlbumModel
import com.example.musicfun.models.CategoryModel

class SongListActivity : AppCompatActivity() {

    companion object {
        lateinit var category: CategoryModel
        lateinit var albumList: AlbumModel
        lateinit var type: String
    }

    lateinit var binding: ActivitySongListBinding
    lateinit var songListAdapter: SongListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (type == "category") {
            binding.nameTextView.text = category.name
            Glide.with(binding.coverImageView).load(category.headerUrl)
                .apply(
                    RequestOptions().transform(RoundedCorners(32))
                )
                .into(binding.coverImageView)

            setUpCategorySongListRecyclerView()
        } else if (type == "album") {
            binding.nameTextView.text = albumList.title
            Glide.with(binding.coverImageView).load(albumList.coverUrl)
                .apply(
                    RequestOptions().transform(RoundedCorners(32))
                )
                .into(binding.coverImageView)

            setUpAlbumSongListRecyclerView()
        }
    }

    fun setUpCategorySongListRecyclerView() {
        songListAdapter = SongListAdapter(category.songs)
        binding.songsListRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.songsListRecyclerView.adapter = songListAdapter
    }

    fun setUpAlbumSongListRecyclerView() {
        songListAdapter = SongListAdapter(albumList.songs)
        binding.songsListRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.songsListRecyclerView.adapter = songListAdapter
    }
}