package com.example.musicfun.models

data class CategoryModel(
    val name : String,
    val picture : Int,
) {
    constructor() : this("",0)
}