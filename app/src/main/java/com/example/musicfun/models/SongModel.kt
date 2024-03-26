package com.example.musicfun.models

data class SongModel(
    val id: String,
    val title : String,
    val coverPicture : Int,
    val url: String,
) {
    constructor() : this("","",0,"")
}
