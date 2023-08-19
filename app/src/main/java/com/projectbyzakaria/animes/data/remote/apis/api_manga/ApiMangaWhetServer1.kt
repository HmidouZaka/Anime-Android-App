package com.projectbyzakaria.animes.data.remote.apis.api_manga

import com.projectbyzakaria.animes.data.dao.characters_search.CharactersSearch
import com.projectbyzakaria.animes.data.dao.pepole.PeopleSearch
import com.projectbyzakaria.animes.data.dao.relotion.MovieDetail
import com.projectbyzakaria.animes.data.dao.relotion.characters.Character
import com.projectbyzakaria.animes.data.dao.relotion.characters.Characters
import com.projectbyzakaria.animes.data.dao.relotion.images.Images
import com.projectbyzakaria.animes.data.dao.relotion.recommendations.Recommendations
import com.projectbyzakaria.animes.data.dao.relotion.reviews.Reviews
import com.projectbyzakaria.animes.data.dao.top_anime_object.TopAnime
import com.projectbyzakaria.animes.data.dao.top_mnga_object.TopManga
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiMangaWhetServer1 {

    @GET("top/manga")
    suspend fun getTopManga(
        @Query("page") page:Int
    ):Response<TopManga>


    @GET("manga/{id}/recommendations")
    suspend fun getMangaRecommendations(
        @Path("id") idManga:Int
    ):Response<Recommendations>


    @GET("manga/{id}/pictures")
    suspend fun getMangaImages(
        @Path("id") idManga:Int
    ):Response<Images>

    @GET("manga/{id}/characters")
    suspend fun getMangaCharacters(
        @Path("id") idManga:Int
    ):Response<Characters>

    @GET("manga/{id}/reviews")
    suspend fun getMangaReviews(
        @Path("id") id:Int,
        @Query("page") page:Int
    ):Response<Reviews>

    @GET("manga")
    suspend fun searchManga(
        @Query("q") query:String,
        @Query("page") page:Int
    ):Response<TopManga>

    @GET("manga/{id}")
    suspend fun getMangaById(
        @Path("id") id:Int
    ):Response<MovieDetail>
}