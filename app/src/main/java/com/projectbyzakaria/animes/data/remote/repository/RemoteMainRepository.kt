package com.projectbyzakaria.animes.data.remote.repository

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
import com.projectbyzakaria.animes.data.dao.top_mnga_object.TopManga
import com.projectbyzakaria.animes.model.ResponseResult
import javax.inject.Inject

class RemoteMainRepository @Inject constructor(
    private val animeRepository: AnimeRepository,
    private val mangaRepository: MangaRepository,
) {

    suspend fun getTopAnime(page:Int):ResponseResult<TopAnime,String>{
        return animeRepository.getTopAnime(page)
    }

    suspend fun getTopManga(page:Int):ResponseResult<TopManga,String>{
        return mangaRepository.getTopManga(page)
    }



    suspend fun getAnimeImages(id:Int):ResponseResult<Images,String>{
        return animeRepository.getAnimeImages(id)
    }

    suspend fun getMangaImages(id:Int):ResponseResult<Images,String>{
        return mangaRepository.getMangaImages(id)
    }

    suspend fun getAnimeRecommendations(id:Int):ResponseResult<Recommendations,String>{
        return animeRepository.getAnimeRecommendations(id)
    }
    suspend fun getMangaRecommendations(id:Int):ResponseResult<Recommendations,String>{
        return mangaRepository.getMangaRecommendations(id)
    }


    suspend fun searchAnime(q: String, page: Int):ResponseResult<TopAnime,String>{
        return animeRepository.searchAnime(q,page)
    }
    suspend fun searchManga(q:String,page: Int):ResponseResult<TopManga,String>{
        return mangaRepository.searchManga(q,page)
    }

    suspend fun searchPeople(q:String,page: Int):ResponseResult<PeopleSearch,String>{
        return animeRepository.searchPeople(q,page)
    }

    suspend fun searchCharacters(q:String,page: Int):ResponseResult<CharactersSearch,String>{
        return animeRepository.searchCharacters(q,page)
    }

    suspend fun getCharacterInfo(id: Int):ResponseResult<CharacterInfo,String>{
        return animeRepository.getCharacterInfo(id)
    }

    suspend fun getCharacterImages(id: Int):ResponseResult<ImageCharacters,String>{
        return animeRepository.getCharacterImages(id)
    }

    suspend fun getPeopleImages(id: Int):ResponseResult<ImagePeople,String>{
        return animeRepository.getPeopleImages(id)
    }

    suspend fun getPeopleInfo(id: Int):ResponseResult<PeopleInfo,String>{
        return animeRepository.getPeopleInfo(id)
    }

    suspend fun getAnimeById(id: Int):ResponseResult<MovieDetail,String>{
        return animeRepository.getAnimeById(id)
    }
    suspend fun getMangaById(id: Int):ResponseResult<MovieDetail,String>{
        return mangaRepository.getMangaById(id)
    }
    suspend fun getAnimeStaff(id: Int): ResponseResult<Stuf, String> {
        return animeRepository.getAnimeStaff(id)
    }
    suspend fun getAnimeEpisodes(id: Int,page: Int): ResponseResult<Episodes, String> {
        return animeRepository.getAnimeEpisodes(id,page)
    }
    suspend fun getAnimeReviews(id: Int,page: Int): ResponseResult<Reviews, String> {
        return animeRepository.getAnimeReviews(id,page)
    }
    suspend fun getAnimeCharacters(id: Int): ResponseResult<Characters, String> {
        return animeRepository.getAnimeCharacters(id)
    }

    suspend fun getMangaReviews(id: Int,page: Int): ResponseResult<Reviews, String> {
        return mangaRepository.getMangaReviews(id,page)
    }
    suspend fun getMangaCharacters(id: Int): ResponseResult<Characters, String> {
        return mangaRepository.getMangaCharacters(id)
    }
}