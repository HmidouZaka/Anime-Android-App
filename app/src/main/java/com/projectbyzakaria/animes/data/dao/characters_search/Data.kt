package com.projectbyzakaria.animes.data.dao.characters_search


data class Data(
    val about: String?,
    val favorites: Int?,
    val images: Images,
    val mal_id: Int?,
    val name: String?,
    val name_kanji: String?,
    val nicknames: List<String>,
    val url: String?
){
    override fun equals(other: Any?): Boolean {
        return try {
            val d = other as Data
            mal_id == d.mal_id
        }catch (ex:Exception){
            false
        }
    }
}