package com.example.musicfun.models

data class SongModel(
    val id: String,
    val title : String,
    val author: String,
    val url : String,
    val coverUrl: String,
    val mp3: String
) {
    constructor() : this("","","","", "", "")
}
