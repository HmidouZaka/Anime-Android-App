package com.projectbyzakaria.animes.data.dao.relotion.images_characters

import com.google.gson.annotations.SerializedName

data class ImageCharacters(
    @SerializedName("data")
    val imagesData: List<ImagesData>
)