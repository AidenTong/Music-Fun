package com.example.musicfun

import androidx.media3.exoplayer.ExoPlayer
import android.content.Context
import androidx.media3.common.MediaItem
import com.example.musicfun.models.MusicModel

object MyExoplayer {

    private var exoPlayer: ExoPlayer? = null
    private var currentMusic: MusicModel?= null

    fun getCurrentMusic(): MusicModel?{
        return currentMusic
    }
    fun getInstance() : ExoPlayer?{
        return exoPlayer
    }

    fun startPlaying(context: Context, music: MusicModel){
        if(exoPlayer==null){
            exoPlayer = ExoPlayer.Builder(context).build()
        }

        if(currentMusic!= music){
            currentMusic = music
            currentMusic?.MP3?.apply {
                val mediaItem = MediaItem.fromUri(this)
                exoPlayer?.setMediaItem(mediaItem)
                exoPlayer?.prepare()
                exoPlayer?.play()
            }
        }

    }



}