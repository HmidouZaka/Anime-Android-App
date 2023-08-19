package com.projectbyzakaria.animes.data.local.convereters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class ConvertImageToNull  {

    @TypeConverter
    fun fromBitmap(bitmap: Bitmap?):ByteArray?{
        if (bitmap != null) {
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            return outputStream.toByteArray()
        }else{
            return null
        }
    }
    @TypeConverter
    fun toBitmap(byteArray: ByteArray?):Bitmap?{
        if (byteArray != null){
            val bitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
            return bitmap
        }else{
            return null
        }

    }
}