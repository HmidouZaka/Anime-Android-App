package com.projectbyzakaria.animes.data.dao.pepole

data class Data(
    val about: String?,
    val alternate_names: List<String?>,
    val birthday: String?,
    val family_name: String?,
    val favorites: Int?,
    val given_name: String?,
    val images: Images,
    val mal_id: Int?,
    val name: String?,
    val url: String?,
    val website_url: String?
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