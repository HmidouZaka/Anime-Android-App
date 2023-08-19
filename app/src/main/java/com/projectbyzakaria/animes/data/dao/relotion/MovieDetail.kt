package com.projectbyzakaria.animes.data.dao.relotion

import com.google.gson.annotations.SerializedName
import com.projectbyzakaria.animes.data.dao.top_anime_object.Data

data class MovieDetail(
    @SerializedName("data")
    val movieData:Data
)
