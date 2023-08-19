package com.projectbyzakaria.animes.data.dao.top_anime_object

data class Pagination(
    val current_page: Int?,
    val has_next_page: Boolean?,
    val items: Items?,
    val last_visible_page: Int?
)