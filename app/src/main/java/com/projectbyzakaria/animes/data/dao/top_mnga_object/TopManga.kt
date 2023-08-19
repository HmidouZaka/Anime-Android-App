package com.projectbyzakaria.animes.data.dao.top_mnga_object

import com.projectbyzakaria.animes.data.dao.top_anime_object.Data
import com.projectbyzakaria.animes.data.dao.top_anime_object.Pagination

data class TopManga(
    val `data`: List<Data>,
    val pagination: Pagination
)