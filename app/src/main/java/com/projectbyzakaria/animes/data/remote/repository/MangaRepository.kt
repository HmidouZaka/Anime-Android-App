package com.projectbyzakaria.animes.data.remote.repository

import android.util.Log
import com.projectbyzakaria.animes.data.dao.relotion.MovieDetail
import com.projectbyzakaria.animes.data.dao.relotion.characters.Character
import com.projectbyzakaria.animes.data.dao.relotion.characters.Characters
import com.projectbyzakaria.animes.data.dao.relotion.images.Images
import com.projectbyzakaria.animes.data.dao.relotion.recommendations.Recommendations
import com.projectbyzakaria.animes.data.dao.relotion.reviews.Reviews
import com.projectbyzakaria.animes.data.dao.top_anime_object.TopAnime
import com.projectbyzakaria.animes.data.dao.top_mnga_object.TopManga
import com.projectbyzakaria.animes.data.remote.apis.api_manga.ApiMangaWhetServer1
import com.projectbyzakaria.animes.model.ResponseResult
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class MangaRepository @Inject constructor(
    private val apiServer1: ApiMangaWhetServer1
) {

    suspend fun getTopManga(page:Int): ResponseResult<TopManga, String> {
        return  try {
            val result = apiServer1.getTopManga(page)
            if (result.isSuccessful && result.body() != null){
                ResponseResult(true,result.body()!!,null)
            }else{
                ResponseResult(false,null,result.message())
            }
        }
        catch (ex: HttpException){
            Log.e("exceptions for call server", "getTopAnime: ${ex.message}", )
            ResponseResult(false,null,ex.cause?.message)
        }
        catch (ex:IOException){
            Log.e("exceptions for call server", "getTopAnime: ${ex.message}", )
            ResponseResult(false,null,"No Internet")
        }catch (ex:Exception){
            Log.e("exceptions for call server", "MangaRepository: ${ex.message}", )
            ResponseResult(false,null,ex.message)
        }
    }

    suspend fun getMangaImages(id:Int): ResponseResult<Images,String>{
        return  try {
            val result = apiServer1.getMangaImages(id)
            if (result.isSuccessful && result.body() != null){
                ResponseResult(true,result.body()!!,null)
            }else{
                ResponseResult(false,null,result.message())
            }
        }catch (ex:HttpException){
            Log.e("exceptions for call server", "getTopAnime: ${ex.message}", )
            ResponseResult(false,null,ex.cause?.message)
        }
        catch (ex:IOException){
            Log.e("exceptions for call server", "getTopAnime: ${ex.message}", )
            ResponseResult(false,null,"No Internet")
        }
        catch (ex:Exception){
            Log.e("exceptions for call server", "getTopAnime: ${ex.message}", )
            ResponseResult(false,null,ex.message)
        }
    }

    suspend fun getMangaRecommendations(id:Int): ResponseResult<Recommendations,String>{
        return  try {
            val result = apiServer1.getMangaRecommendations(id)
            if (result.isSuccessful && result.body() != null){
                ResponseResult(true,result.body()!!,null)
            }else{
                ResponseResult(false,null,result.message())
            }
        }catch (ex:HttpException){
            Log.e("exceptions for call server", "getTopAnime: ${ex.message}", )
            ResponseResult(false,null,ex.cause?.message)
        }
        catch (ex:IOException){
            Log.e("exceptions for call server", "getTopAnime: ${ex.message}", )
            ResponseResult(false,null,"No Internet")
        }
        catch (ex:Exception){
            Log.e("exceptions for call server", "getTopAnime: ${ex.message}", )
            ResponseResult(false,null,ex.message)
        }
    }

    suspend fun searchManga(q:String,page: Int): ResponseResult<TopManga, String> {
        return  try {
            val result = apiServer1.searchManga(q,page)
            if (result.isSuccessful && result.body() != null){
                ResponseResult(true,result.body()!!,null)
            }else{
                ResponseResult(false,null,result.message())
            }
        }
        catch (ex: HttpException){
            Log.e("exceptions for call server", "getTopAnime: ${ex.message}", )
            ResponseResult(false,null,ex.cause?.message)
        }
        catch (ex:IOException){
            Log.e("exceptions for call server", "getTopAnime: ${ex.message}", )
            ResponseResult(false,null,"No Internet")
        }catch (ex:Exception){
            Log.e("exceptions for call server", "MangaRepository: ${ex.message}", )
            ResponseResult(false,null,ex.message)
        }
    }

    suspend fun getMangaById(id: Int): ResponseResult<MovieDetail, String> {
        return  try {
            val result = apiServer1.getMangaById(id)
            if (result.isSuccessful && result.body() != null){
                ResponseResult(true,result.body()!!,null)
            }else{
                ResponseResult(false,null,result.message())
            }
        }
        catch (ex: HttpException){
            Log.e("exceptions for call server", "getTopAnime: ${ex.message}", )
            ResponseResult(false,null,ex.cause?.message)
        }
        catch (ex:IOException){
            Log.e("exceptions for call server", "getTopAnime: ${ex.message}", )
            ResponseResult(false,null,"No Internet")
        }catch (ex:Exception){
            Log.e("exceptions for call server", "MangaRepository: ${ex.message}", )
            ResponseResult(false,null,ex.message)
        }
    }

    suspend fun getMangaReviews(id: Int,page: Int): ResponseResult<Reviews, String> {
        return  try {
            val result = apiServer1.getMangaReviews(id,page)
            if (result.isSuccessful && result.body() != null){
                ResponseResult(true,result.body()!!,null)
            }else{
                ResponseResult(false,null,result.message())
            }
        }
        catch (ex: HttpException){
            Log.e("exceptions for call server", "getTopAnime: ${ex.message}", )
            ResponseResult(false,null,ex.cause?.message)
        }
        catch (ex:IOException){
            Log.e("exceptions for call server", "getTopAnime: ${ex.message}", )
            ResponseResult(false,null,"No Internet")
        }catch (ex:Exception){
            Log.e("exceptions for call server", "MangaRepository: ${ex.message}", )
            ResponseResult(false,null,ex.message)
        }
    }

    suspend fun getMangaCharacters(id: Int): ResponseResult<Characters, String> {
        return  try {
            val result = apiServer1.getMangaCharacters(id)
            if (result.isSuccessful && result.body() != null){
                ResponseResult(true,result.body()!!,null)
            }else{
                ResponseResult(false,null,result.message())
            }
        }
        catch (ex: HttpException){
            Log.e("exceptions for call server", "getTopAnime: ${ex.message}", )
            ResponseResult(false,null,ex.cause?.message)
        }
        catch (ex:IOException){
            Log.e("exceptions for call server", "getTopAnime: ${ex.message}", )
            ResponseResult(false,null,"No Internet")
        }catch (ex:Exception){
            Log.e("exceptions for call server", "MangaRepository: ${ex.message}", )
            ResponseResult(false,null,ex.message)
        }
    }
}