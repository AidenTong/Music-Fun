package com.example.musicfun.models

data class MusicModel(
    val id: String,
    val title : String,
    val author: String,
    val coverPicture : Int,
    val url: String,
    val MP3: String,
){
    constructor() : this("","","",0,"","")

}
