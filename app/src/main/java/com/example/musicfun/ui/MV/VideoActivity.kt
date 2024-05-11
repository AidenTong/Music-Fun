package com.example.musicfun.ui.MV

import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import android.widget.MediaController
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.musicfun.R
import com.example.musicfun.models.AlbumModel
import com.example.musicfun.models.CategoryModel

class VideoActivity : AppCompatActivity() {
    companion object {
        lateinit var uri: String
    }
    private lateinit var musicVideo: VideoView
    private var pos = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        pos = savedInstanceState?.getInt("pos", 0) ?: 0

        musicVideo = findViewById(R.id.music_video)
        val mediaCtrl = MediaController(this)
        musicVideo.setMediaController(mediaCtrl)
        musicVideo.setVideoURI(Uri.parse(uri))
    }

    override fun onResume() {
        super.onResume()
        musicVideo.seekTo(pos)
        musicVideo.start()
    }

    override fun onPause() {
        super.onPause()
        pos = musicVideo.currentPosition
        musicVideo.stopPlayback()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("pos", pos)
    }
}