package com.projectbyzakaria.animes.repositorys

import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.Network
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import com.projectbyzakaria.animes.data.local.MovieDatabase
import com.projectbyzakaria.animes.data.remote.repository.RemoteMainRepository
import com.projectbyzakaria.animes.model.MovieLocal
import com.projectbyzakaria.animes.model.ResponseResult
import com.projectbyzakaria.animes.model.User
import com.projectbyzakaria.animes.utilt.ResponseState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val RemoteDataSores: RemoteMainRepository,
    @ApplicationContext private val context: Context,
    private val localDatabase: MovieDatabase
) {
    fun getTopAnime(page: Int): Flow<ResponseResult<TopAnime, String>> =
        flow {
            val data = RemoteDataSores.getTopAnime(page)
            emit(data)
        }.catch {
            Log.e("exceptions for call server", "MainRepository.getTopAnime: ${it.message}")
        }.flowOn(Dispatchers.IO)

    fun getTopManga(page: Int): Flow<ResponseResult<TopManga, String>> =
        flow {
            val data = RemoteDataSores.getTopManga(page)
            emit(data)
        }.catch {
            Log.e("exceptions for call server", "MainRepository.getTopManga: ${it.message}")
        }.flowOn(Dispatchers.IO)


    fun getImagesAnime(id: Int): Flow<ResponseResult<Images, String>> =
        flow {
            val data = RemoteDataSores.getAnimeImages(id)
            emit(data)
        }.catch {
            Log.e("exceptions for call server", "MainRepository.getTopManga: ${it.message}")
        }.flowOn(Dispatchers.IO)

    fun getImagesManga(id: Int): Flow<ResponseResult<Images, String>> =
        flow {
            val data = RemoteDataSores.getMangaImages(id)
            emit(data)
        }.catch {
            Log.e("exceptions for call server", "MainRepository.getTopManga: ${it.message}")
        }.flowOn(Dispatchers.IO)

    fun getMangaRecommendations(id: Int): Flow<ResponseResult<Recommendations, String>> =
        flow {
            val data = RemoteDataSores.getMangaRecommendations(id)
            emit(data)
        }.catch {
            Log.e("exceptions for call server", "MainRepository.getTopManga: ${it.message}")
        }.flowOn(Dispatchers.IO)

    fun getAnimeRecommendations(id: Int): Flow<ResponseResult<Recommendations, String>> =
        flow {
            val data = RemoteDataSores.getAnimeRecommendations(id)
            emit(data)
        }.catch {
            Log.e("exceptions for call server", "MainRepository.getTopManga: ${it.message}")
        }.flowOn(Dispatchers.IO)

    fun searchAnime(id: String, page: Int): Flow<ResponseResult<TopAnime, String>> =
        flow {
            val data = RemoteDataSores.searchAnime(id, page)
            emit(data)
        }.catch {
            Log.e("exceptions for call server", "MainRepository.getTopManga: ${it.message}")
        }.flowOn(Dispatchers.IO)

    fun searchManga(id: String, page: Int): Flow<ResponseResult<TopManga, String>> =
        flow {
            val data = RemoteDataSores.searchManga(id, page)
            emit(data)
        }.catch {
            Log.e("exceptions for call server", "MainRepository.getTopManga: ${it.message}")
        }.flowOn(Dispatchers.IO)


    fun searchCharacters(id: String, page: Int): Flow<ResponseResult<CharactersSearch, String>> =
        flow {
            val data = RemoteDataSores.searchCharacters(id, page)
            emit(data)
        }.catch {
            Log.e("exceptions for call server", "MainRepository.getTopManga: ${it.message}")
        }.flowOn(Dispatchers.IO)

    fun searchPeople(id: String, page: Int): Flow<ResponseResult<PeopleSearch, String>> =
        flow {
            val data = RemoteDataSores.searchPeople(id, page)
            emit(data)
        }.catch {
            Log.e("exceptions for call server", "MainRepository.getTopManga: ${it.message}")
        }.flowOn(Dispatchers.IO)


    fun getCharacterImages(id: Int): Flow<ResponseResult<ImageCharacters, String>> =
        flow {
            val data = RemoteDataSores.getCharacterImages(id)
            emit(data)
        }.catch {
            Log.e("exceptions for call server", "MainRepository.getTopManga: ${it.message}")
        }.flowOn(Dispatchers.IO)


    fun getCharacterInfo(id: Int): Flow<ResponseResult<CharacterInfo, String>> =
        flow {
            val data = RemoteDataSores.getCharacterInfo(id)
            emit(data)
        }.catch {
            Log.e("exceptions for call server", "MainRepository.getTopManga: ${it.message}")
        }.flowOn(Dispatchers.IO)


    fun getPeopleInfo(id: Int): Flow<ResponseResult<PeopleInfo, String>> =
        flow {
            val data = RemoteDataSores.getPeopleInfo(id)
            emit(data)
        }.catch {
            Log.e("exceptions for call server", "MainRepository.getTopManga: ${it.message}")
        }.flowOn(Dispatchers.IO)


    fun getPeopleImage(id: Int): Flow<ResponseResult<ImagePeople, String>> =
        flow {
            val data = RemoteDataSores.getPeopleImages(id)
            emit(data)
        }.catch {
            Log.e("exceptions for call server", "MainRepository.getTopManga: ${it.message}")
        }.flowOn(Dispatchers.IO)


    fun getAnimeById(id: Int): Flow<ResponseResult<MovieDetail, String>> =
        flow {
            val data = RemoteDataSores.getAnimeById(id)
            emit(data)
        }.catch {
            Log.e("exceptions for call server", "MainRepository.getTopManga: ${it.message}")
        }.flowOn(Dispatchers.IO)


    fun getMangaById(id: Int): Flow<ResponseResult<MovieDetail, String>> =
        flow {
            val data = RemoteDataSores.getMangaById(id)
            emit(data)
        }.catch {
            Log.e("exceptions for call server", "MainRepository.getTopManga: ${it.message}")
        }.flowOn(Dispatchers.IO)


    fun observeForInternet(viewModelScope: CoroutineScope) = callbackFlow {
        val system =
            context.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        val connectivity = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                viewModelScope.launch {
                    send(true)
                }
            }

            override fun onUnavailable() {
                viewModelScope.launch {
                    send(false)
                }
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                viewModelScope.launch {
                    send(false)
                }
            }
        }
        system.registerDefaultNetworkCallback(connectivity)
        awaitClose {
            system.unregisterNetworkCallback(connectivity)
        }
    }.distinctUntilChanged()


    fun getAnimeStaff(id: Int): Flow<ResponseResult<Stuf, String>> =
        flow {
            val data = RemoteDataSores.getAnimeStaff(id)
            emit(data)
        }.catch {
            Log.e("exceptions for call server", "MainRepository.getTopManga: ${it.message}")
        }.flowOn(Dispatchers.IO)

    fun getAnimeEpisodes(id: Int, page: Int): Flow<ResponseResult<Episodes, String>> =
        flow {
            val data = RemoteDataSores.getAnimeEpisodes(id, page)
            emit(data)
        }.catch {
            Log.e("exceptions for call server", "MainRepository.getTopManga: ${it.message}")
        }.flowOn(Dispatchers.IO)


    fun getAnimeReviews(id: Int, page: Int): Flow<ResponseResult<Reviews, String>> =
        flow {
            val data = RemoteDataSores.getAnimeReviews(id, page)
            emit(data)
        }.catch {
            Log.e("exceptions for call server", "MainRepository.getTopManga: ${it.message}")
        }.flowOn(Dispatchers.IO)

    fun getAnimeCharacters(id: Int): Flow<ResponseResult<Characters, String>> =
        flow {
            val data = RemoteDataSores.getAnimeCharacters(id)
            emit(data)
        }.catch {
            Log.e("exceptions for call server", "MainRepository.getTopManga: ${it.message}")
        }.flowOn(Dispatchers.IO)


    fun getMangaReviews(id: Int, page: Int): Flow<ResponseResult<Reviews, String>> =
        flow {
            val data = RemoteDataSores.getMangaReviews(id, page)
            emit(data)
        }.catch {
            Log.e("exceptions for call server", "MainRepository.getTopManga: ${it.message}")
        }.flowOn(Dispatchers.IO)

    fun getMangaCharacters(id: Int): Flow<ResponseResult<Characters, String>> =
        flow {
            val data = RemoteDataSores.getMangaCharacters(id)
            emit(data)
        }.catch {
            Log.e("exceptions for call server", "MainRepository.getTopManga: ${it.message}")
        }.flowOn(Dispatchers.IO)


    suspend fun insertMovies(movieLocal: MovieLocal) {
        val dao = localDatabase.getMoviesDao()
        dao.insertMovie(movieLocal)
    }

    suspend fun deleteMovie(movieLocal: MovieLocal) {
        val dao = localDatabase.getMoviesDao()
        dao.delete(movieLocal)
    }

    fun getAllMovies(): LiveData<ResponseState<List<MovieLocal>>> {
        var data = localDatabase.getMoviesDao().read()
        val liveData = MutableLiveData<ResponseState<List<MovieLocal>>>(ResponseState.Loading())
        data.observeForever {
            if (it.isEmpty()) {
                liveData.value = ResponseState.Error("No Data Found")
            } else {
                liveData.value = ResponseState.Success(it)
            }
        }
        return liveData
    }

    suspend fun isIdInDatabase(id: Int): Boolean {
        val value = localDatabase.getMoviesDao().isInDatabase(id)
        return value != 0
    }


    suspend fun getUser(): User {
        return localDatabase.getUserDao().getUser()
    }

    suspend fun upDateName(name: String): User {
        val user = getUser()
        val nowUser = user.copy(name = name)
        localDatabase.getUserDao().updateUser(nowUser)
        return nowUser
    }

    suspend fun upDateEmail(email: String): User {
        val user = getUser()
        val nowUser = user.copy(email = email)
        localDatabase.getUserDao().updateUser(nowUser)
        return nowUser
    }

    suspend fun upDateImage(image: Bitmap): User {
        val user = getUser()
        val nowUser = user.copy(image = image)
        localDatabase.getUserDao().updateUser(nowUser)
        return nowUser
    }

    suspend fun upDateFollow(follow: Int): User {
        val user = getUser()
        val nowUser = user.copy(followrs = follow)
        localDatabase.getUserDao().updateUser(nowUser)
        return nowUser
    }

    suspend fun isHaveUserInDataBase(): Boolean {
        return localDatabase.getUserDao().isThereUser() != 0
    }

    fun getNumberOfFavoret(): Flow<Int> {
        return localDatabase.getMoviesDao().getCountFavorite()
    }

    suspend fun insertUser(user: User): User {
        localDatabase.getUserDao().insertUser(user)
        return user
    }
}