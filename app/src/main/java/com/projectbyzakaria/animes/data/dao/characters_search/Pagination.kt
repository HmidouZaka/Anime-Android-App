package com.projectbyzakaria.animes.data.dao.characters_search

data class Pagination(
    val current_page: Int?,
    val has_next_page: Boolean?,
    val items: Items,
    val last_visible_page: Int?
)