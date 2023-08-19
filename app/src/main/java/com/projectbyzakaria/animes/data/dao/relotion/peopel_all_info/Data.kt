package com.projectbyzakaria.animes.data.dao.relotion.peopel_all_info

data class Data(
    val about: String?,
    val alternate_names: List<Any>,
    val anime: List<Anime>,
    val birthday: String?,
    val family_name: String?,
    val favorites: Int?,
    val given_name: String?,
    val images: ImagesX,
    val mal_id: Int?,
    val manga: List<Manga>,
    val name: String?,
    val url: String?,
    val voices: List<Voice>,
    val website_url: Any?
)