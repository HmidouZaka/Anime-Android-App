package com.projectbyzakaria.animes.data.local.convereters

import androidx.room.TypeConverter
import com.projectbyzakaria.animes.utilt.TypeMovies

class ConvertType {

    @TypeConverter
    fun fromObject(typeMovies: TypeMovies):String{
        return typeMovies.name
    }
    @TypeConverter
    fun toObject(name: String):TypeMovies{
        return when(name){
            TypeMovies.Manga.name->TypeMovies.Manga
            else -> TypeMovies.Anime
        }
    }
}