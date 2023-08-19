package com.projectbyzakaria.animes.data.remote.apis.apI_anime

import com.projectbyzakaria.animes.data.dao.characters_search.CharactersSearch
import com.projectbyzakaria.animes.data.dao.pepole.PeopleSearch
import com.projectbyzakaria.animes.data.dao.relotion.MovieDetail
import com.projectbyzakaria.animes.data.dao.relotion.character_info.CharacterInfo
import com.projectbyzakaria.animes.data.dao.relotion.images.Images
import com.projectbyzakaria.animes.data.dao.relotion.recommendations.Recommendations
import com.projectbyzakaria.animes.data.dao.top_anime_object.TopAnime
import com.projectbyzakaria.animes.data.dao.relotion.characters.Character
import com.projectbyzakaria.animes.data.dao.relotion.characters.Characters
import com.projectbyzakaria.animes.data.dao.relotion.epsedies.Episodes
import com.projectbyzakaria.animes.data.dao.relotion.image_people.ImagePeople
import com.projectbyzakaria.animes.data.dao.relotion.images_characters.ImageCharacters
import com.projectbyzakaria.animes.data.dao.relotion.peopel_all_info.PeopleInfo
import com.projectbyzakaria.animes.data.dao.relotion.reviews.Reviews
import com.projectbyzakaria.animes.data.dao.relotion.stuf.Stuf
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiAnimeWhetServer1 {

    @GET("top/anime")
    suspend fun getTopAnime(
        @Query("page") page:Int
    ):Response<TopAnime>

    @GET("anime/{id}/recommendations")
    suspend fun getAnimeRecommendations(
        @Path("id") idAnime:Int
    ):Response<Recommendations>


    @GET("anime/{id}/pictures")
    suspend fun getAnimeImages(
        @Path("id") idAnime:Int
    ):Response<Images>




    @GET("anime")
    suspend fun searchAnime(
        @Query("q") query:String,
        @Query("page") page:Int
    ):Response<TopAnime>

    @GET("characters")
    suspend fun searchCharacters(
        @Query("q") query:String,
        @Query("page") page:Int
    ):Response<CharactersSearch>


    @GET("people")
    suspend fun searchPeople(
        @Query("q") query:String,
        @Query("page") page:Int
    ):Response<PeopleSearch>



    @GET("characters/{id}/full")
    suspend fun getDataInfoForCharacter(
        @Path("id") id:Int
    ):Response<CharacterInfo>


    @GET("characters/{id}/pictures")
    suspend fun getImagersForCharacter(
        @Path("id") id:Int
    ):Response<ImageCharacters>

    @GET("people/{id}/full")
    suspend fun getDataInfoForPeople(
        @Path("id") id:Int
    ):Response<PeopleInfo>


    @GET("people/{id}/pictures")
    suspend fun getImagersForPeople(
        @Path("id") id:Int
    ):Response<ImagePeople>

    @GET("anime/{id}")
    suspend fun getAnimeById(
        @Path("id") id:Int
    ):Response<MovieDetail>


    @GET("anime/{id}/staff")
    suspend fun getAnimeStaff(
        @Path("id") id:Int
    ):Response<Stuf>

    @GET("anime/{id}/videos/episodes")
    suspend fun getAnimeEpisodes(
        @Path("id") id:Int,
        @Query("page") page:Int
    ):Response<Episodes>

    @GET("anime/{id}/reviews")
    suspend fun getAnimeReviews(
        @Path("id") id:Int,
        @Query("page") page:Int
    ):Response<Reviews>


    @GET("anime/{id}/characters")
    suspend fun getAnimeCharacters(
        @Path("id") idAnime:Int
    ):Response<Characters>
}