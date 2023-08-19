package com.projectbyzakaria.animes.utilt

sealed interface ResponseState<T>{
    data class Success<T>(val data:T):ResponseState<T>
    data class Loading<T>(val any:Any? = null):ResponseState<T>
    data class Error<T>(val message:String):ResponseState<T>
}