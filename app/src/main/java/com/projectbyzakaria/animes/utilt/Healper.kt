package com.projectbyzakaria.animes.utilt

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.compose.material.FabPosition
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlin.random.Random

fun String.take(size:Int = 15):String{
    if (this.length > size){
        return this.substring(0,14)
    }else{
        return this
    }
}



fun String.runIfQueryNotEmpty(runCode:(String)->Unit){
    if (this.isNotEmpty()){
        runCode(this)
    }
}



fun String?.formatDate():String{
    return this?.substring(0,10) ?: ""
}


fun <T>List<T>.getOrNull(position: Int = 0):T?{
    return try {
        this[position]
    }catch (ex:Exception){
        null
    }
}


fun <T>List<T>.uniqueList():List<T>{
    val newList = mutableListOf<T>()
    this.forEach {
        if (it !in newList){
            newList.add(it)
        }
    }
    return newList
}


fun getUniqueNumber(list: List<Int>):Int{
    var curnet:Int? = null
    while (true){
        val target  = Random.nextInt(0,1000000)
        if (target !in list){
            curnet = target
            break
        }
    }
    return curnet!!
}

suspend fun String.toBitmap(context:Context):Bitmap{
    val imageLoader = ImageLoader(context)
    val imageRequest = ImageRequest.Builder(context)
        .data(this)
        .build()
    val result = (imageLoader.execute(imageRequest) as SuccessResult).drawable
    return (result as BitmapDrawable).bitmap
}

fun String.whereType():TypeMovies{
    return when (this){
        TypeMovies.Manga.name -> TypeMovies.Manga
        else -> TypeMovies.Anime
    }
}


fun String.isValidEmail():Boolean{
    return this.matches(Regex("^[a-zA-Z\\d]{4,20}@[a-z]{1,6}\\.com"))
}



