package com.projectbyzakaria.animes.data.dao.relotion.reviews

data class Data(
    val date: String?,
    val episodes_watched: Any?,
    val is_preliminary: Boolean?,
    val is_spoiler: Boolean?,
    val mal_id: Int?,
    val reactions: Reactions,
    val review: String?,
    val score: Int?,
    val tags: List<String>,
    val type: String?,
    val url: String?,
    val user: User
)