package com.example.musicfun.models

data class AlbumModel(
    val id: String,
    val title : String,
    val author: String,
    val coverUrl: String,
    val songs: List<String>
) {
    constructor() : this("","","","", listOf())
}
