package com.projectbyzakaria.animes.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userinfo")
data class User(
    var name: String? = null,
    var email: String? = null,
    var image: Bitmap? = null,
    @ColumnInfo(defaultValue = "0")
    var followrs: Int = 0
){
    @PrimaryKey(true)
    var id:Int = 1
}
