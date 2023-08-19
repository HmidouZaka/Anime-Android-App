package com.projectbyzakaria.animes.data.remote.repository

import android.util.Log
import com.projectbyzakaria.animes.data.dao.characters_search.CharactersSearch
import com.projectbyzakaria.animes.data.dao.pepole.PeopleSearch
import com.projectbyzakaria.animes.data.dao.relotion.MovieDetail
import com.projectbyzakaria.animes.data.dao.relotion.character_info.CharacterInfo
import com.projectbyzakaria.animes.data.dao.relotion.characters.Character
import com.projectbyzakaria.animes.data.dao.relotion.characters.Characters
import com.projectbyzakaria.animes.data.dao.relotion.epsedies.Episodes
import com.projectbyzakaria.animes.data.dao.relotion.image_people.ImagePeople
import com.projectbyzakaria.animes.data.dao.relotion.images.Images
import com.projectbyzakaria.animes.data.dao.relotion.images_characters.ImageCharacters
import com.projectbyzakaria.animes.data.dao.relotion.peopel_all_info.PeopleInfo
import com.projectbyzakaria.animes.data.dao.relotion.recommendations.Recommendations
import com.projectbyzakaria.animes.data.dao.relotion.reviews.Reviews
import com.projectbyzakaria.animes.data.dao.relotion.stuf.Stuf
import com.projectbyzakaria.animes.data.dao.top_anime_object.TopAnime
import com.projectbyzakaria.animes.data.remote.apis.apI_anime.ApiAnimeWhetServer1
import com.projectbyzakaria.animes.model.ResponseResult
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class AnimeRepository @Inject constructor(
    private val ApiServer1 :ApiAnimeWhetServer1
){
    suspend fun getTopAnime(page:Int): ResponseResult<TopAnime,String>{
        return  try {
            val result = ApiServer1.getTopAnime(page)
            Log.e("exceptions for call server", "getTopAnime: ${result.body()}", )
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

    suspend fun getAnimeImages(id:Int): ResponseResult<Images,String>{
        return  try {
            val result = ApiServer1.getAnimeImages(id)
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

    suspend fun getAnimeRecommendations(id:Int): ResponseResult<Recommendations,String>{
        return  try {
            val result = ApiServer1.getAnimeRecommendations(id)
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

    suspend fun searchAnime(q:String,page: Int): ResponseResult<TopAnime,String>{
        return  try {
            val result = ApiServer1.searchAnime(q,page)
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

    suspend fun searchPeople(q:String,page: Int): ResponseResult<PeopleSearch,String>{
        return  try {
            val result = ApiServer1.searchPeople(q,page)
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

    suspend fun searchCharacters(q:String,page: Int): ResponseResult<CharactersSearch,String>{
        return  try {
            val result = ApiServer1.searchCharacters(q,page)
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

    suspend fun getCharacterInfo(id: Int): ResponseResult<CharacterInfo,String>{
        return  try {
            val result = ApiServer1.getDataInfoForCharacter(id)
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

    suspend fun getCharacterImages(id: Int): ResponseResult<ImageCharacters,String>{
        return  try {
            val result = ApiServer1.getImagersForCharacter(id)
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
    suspend fun getPeopleInfo(id: Int): ResponseResult<PeopleInfo,String>{
        return  try {
            val result = ApiServer1.getDataInfoForPeople(id)
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

    suspend fun getPeopleImages(id: Int): ResponseResult<ImagePeople,String>{
        return  try {
            val result = ApiServer1.getImagersForPeople(id)
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

    suspend fun getAnimeById(id: Int): ResponseResult<MovieDetail,String>{
        return  try {
            val result = ApiServer1.getAnimeById(id)
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

    suspend fun getAnimeStaff(id: Int): ResponseResult<Stuf,String>{
        return  try {
            val result = ApiServer1.getAnimeStaff(id)
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

    suspend fun getAnimeEpisodes(id: Int,page: Int): ResponseResult<Episodes,String>{
        return  try {
            val result = ApiServer1.getAnimeEpisodes(id,page)
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
    suspend fun getAnimeReviews(id: Int,page: Int): ResponseResult<Reviews,String>{
        return  try {
            val result = ApiServer1.getAnimeReviews(id,page)
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
    suspend fun getAnimeCharacters(id: Int): ResponseResult<Characters,String>{
        return  try {
            val result = ApiServer1.getAnimeCharacters(id)
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
}