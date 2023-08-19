package com.projectbyzakaria.animes.data.dao.relotion.image_people

import com.google.gson.annotations.SerializedName

data class ImagePeople(
    @SerializedName("data")
    val dataImages: List<DataImages>
)