package com.projectbyzakaria.animes.model

data class ResponseResult<DATA,ERROR>(
    var isSuccess:Boolean,
    val data:DATA? = null ,
    val error:ERROR?=null,
)