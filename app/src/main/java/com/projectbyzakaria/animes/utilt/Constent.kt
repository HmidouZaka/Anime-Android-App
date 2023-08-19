package com.projectbyzakaria.animes.utilt

import com.projectbyzakaria.animes.data.dao.top_anime_object.Data

object Constent {
    const val BASE_URL_FOR_SERVER2 = ""
    const val BASE_URL_FOR_SERVER3 = ""
    const val BASE_URL_FOR_SERVER1 = ""
    const val KEY_FOR_PASS_TYPE_BETWEEN_ACTIVITY = "type_movie"
    const val KEY_FOR_PASS_ID_BETWEEN_ACTIVITY = "id_movie"
    fun getMapContent(data:Data?=null):HashMap<String,String?>{
        val map = HashMap<String,String?>()
        map["State"] = data?.status
        map["Date"] = data?.aired?.string
        map["Score"] = data?.score?.toString()
        map["Duration"] = data?.duration
        map["Type"] = data?.type
        map["Episodes"] = data?.episodes?.toString()
        map["source"] = data?.source
        return map
    }
    const val DATA_TAG = "data"
    const val IMAGES_TAG = "images"
    const val RECOMMENDATIONS_TAG = "recommendation"
    const val episodes_TAG = "episodes"
    const val STAFF_TAG = "staff"
    const val CHARACTERS_TAG = "characters"
    const val REVIEWS_TAG = "reviews"

}