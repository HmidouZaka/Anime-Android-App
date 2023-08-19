package com.projectbyzakaria.animes.data.dao.relotion.character_info

data class Data(
    val about: String?,
    val anime: List<Anime>,
    val favorites: Int?,
    val images: ImagesX,
    val mal_id: Int?,
    val manga: List<Manga>,
    val name: String?,
    val name_kanji: String?,
    val nicknames: List<Any>,
    val url: String?,
    val voices: List<Voice>
)