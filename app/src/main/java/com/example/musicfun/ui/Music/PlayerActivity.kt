package com.example.musicfun.ui.Music

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.exoplayer.ExoPlayer
import com.bumptech.glide.Glide
import com.example.musicfun.MyExoplayer
import com.example.musicfun.R
import com.example.musicfun.databinding.ActivityPlayerBinding


class PlayerActivity : AppCompatActivity() {
    lateinit var binding: ActivityPlayerBinding
    lateinit var exoPlayer: ExoPlayer



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MyExoplayer.getCurrentMusic()?.apply {
            binding.txvMusicTitle.text = title
            binding.txvAuthor.text = author
            Glide.with(binding.ivMusicCover).load(coverUrl)
                .circleCrop()
                .into(binding.ivMusicCover)
            exoPlayer = MyExoplayer.getInstance()!!
            binding.playerView.player = exoPlayer
            binding.playerView.showController()
        }


    }
}