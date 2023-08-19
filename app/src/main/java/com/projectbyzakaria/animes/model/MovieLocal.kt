package com.projectbyzakaria.animes.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.projectbyzakaria.animes.data.local.convereters.ConvertImage
import com.projectbyzakaria.animes.data.local.convereters.ConvertType
import com.projectbyzakaria.animes.utilt.TypeMovies

@Entity(tableName = "movies")
data class MovieLocal(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val mel_id:Int,
    val name:String,
    val image:Bitmap,
    val description:String,
    var type:TypeMovies
)