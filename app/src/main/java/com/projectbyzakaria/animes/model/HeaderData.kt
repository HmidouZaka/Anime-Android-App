package com.projectbyzakaria.animes.model

data class HeaderData(
    val image:String,
    val title:String,
    val description:String,
    val link:String?,
    val isFavorite:Boolean = false
)
