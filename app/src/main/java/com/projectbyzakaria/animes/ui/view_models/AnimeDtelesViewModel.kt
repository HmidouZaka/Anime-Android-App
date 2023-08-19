package com.projectbyzakaria.animes.ui.view_models

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.projectbyzakaria.animes.data.dao.relotion.MovieDetail
import com.projectbyzakaria.animes.data.dao.relotion.character_info.CharacterInfo
import com.projectbyzakaria.animes.data.dao.relotion.epsedies.Episodes
import com.projectbyzakaria.animes.data.dao.relotion.image_people.DataImages
import com.projectbyzakaria.animes.data.dao.relotion.images.Images
import com.projectbyzakaria.animes.data.dao.relotion.images_characters.ImagesData
import com.projectbyzakaria.animes.data.dao.relotion.peopel_all_info.PeopleInfo
import com.projectbyzakaria.animes.data.dao.relotion.recommendations.Recommendations
import com.projectbyzakaria.animes.data.dao.relotion.reviews.Pagination
import com.projectbyzakaria.animes.data.dao.relotion.reviews.Reviews
import com.projectbyzakaria.animes.data.dao.relotion.stuf.Stuf
import com.projectbyzakaria.animes.data.dao.top_anime_object.Data
import com.projectbyzakaria.animes.model.MovieLocal
import com.projectbyzakaria.animes.model.ResponseResult
import com.projectbyzakaria.animes.model.TowType
import com.projectbyzakaria.animes.repositorys.MainRepository
import com.projectbyzakaria.animes.repositorys.ViewModelMplm
import com.projectbyzakaria.animes.utilt.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class AnimeDtelesViewModel @Inject constructor(
    val repository: MainRepository
) : ViewModel(), ViewModelMplm {


    var paginationReviews: Pagination? = null
    var paginationEpisodes: com.projectbyzakaria.animes.data.dao.relotion.epsedies.Pagination? =
        null


    private var _data = MutableStateFlow<ResponseState<Data>>(ResponseState.Loading())
    val data = _data.asStateFlow()

    private var _images = MutableStateFlow<ResponseState<Images>>(ResponseState.Loading())
    val images = _images.asStateFlow()

    private var _recommendations =
        MutableStateFlow<ResponseState<Recommendations>>(ResponseState.Loading())
    val recommendations = _recommendations.asStateFlow()

    private var _episodes = MutableStateFlow<ResponseState<Episodes>>(ResponseState.Loading())
    val episodes = _episodes.asStateFlow()

    private var _characters =
        MutableStateFlow<ResponseState<com.projectbyzakaria.animes.data.dao.relotion.characters.Characters>>(
            ResponseState.Loading()
        )
    val characters = _characters.asStateFlow()

    private var _staff = MutableStateFlow<ResponseState<Stuf>>(ResponseState.Loading())
    val staff = _staff.asStateFlow()

    private var _review = MutableStateFlow<ResponseState<Reviews>>(ResponseState.Loading())
    val review = _review.asStateFlow()

    var isLoadData = false
    fun initM() {
        _data.value = ResponseState.Loading()
        _images.value = ResponseState.Loading()
        _episodes.value = ResponseState.Loading()
        _characters.value = ResponseState.Loading()
        _staff.value = ResponseState.Loading()
        _review.value = ResponseState.Loading()
        _recommendations.value = ResponseState.Loading()
    }

    fun getAllData(id: Int, typeMovies: String) {
        if (isLoadData) {
            return
        }
        Log.d("AnimeDetailes", "viewModel:       getAllData ")
        isLoadData = true
        isLoadDataStart = false
        initM()
        if (typeMovies == TypeMovies.Anime.name) {
            loadDataAnime(id)
            loadImagesAnime(id)
            loadRecommendationAnime(id)
            return
        }
        if (typeMovies == TypeMovies.Manga.name) {
            loadDataManga(id)
            loadImagesManga(id)
            loadRecommendationManga(id)
            return
        }
    }

    fun refrechEpsides(id: Int) {
        viewModelScope.launch {
            loadEpisodesAnime(id)
        }
    }

    fun refrechStuff(id: Int) {
        viewModelScope.launch {
            loadStaffAnime(id)
        }
    }

    var isLoadDataStart = false
    fun getAllDataStart(id: Int, typeMovies: String) {
        if (isLoadDataStart) {
            return
        }
        isLoadDataStart = true
        if (typeMovies == TypeMovies.Anime.name) {
            viewModelScope.launch {
                async { loadStaffAnime(id) }.await()
                async { loadEpisodesAnime(id) }.await()
                delay(1000)
                loadReviewsAnime(id)
                loadCharactersAnime(id)
            }
            return
        }
        if (typeMovies == TypeMovies.Manga.name) {
            loadReviewsManga(id)
            loadCharactersManga(id)
            return
        }
    }

    var isStartRequestForRecommendationAnime = false
    fun loadRecommendationAnime(id: Int) {
        if (isStartRequestForRecommendationAnime) {
            return
        }
        if (_recommendations.value !is ResponseState.Success) {
            _recommendations.value = ResponseState.Loading()
        }
        isStartRequestForRecommendationAnime = true
        viewModelScope.launch(Dispatchers.IO) {
            val recommendations = repository.getAnimeRecommendations(id)
            recommendations.collectLatest {
                if (it.isSuccess) {
                    if (it.data != null) {
                        _recommendations.emit(ResponseState.Success(it.data))
                    } else {
                        _recommendations.emit(ResponseState.Error("No Result Found"))
                    }
                } else {
                    _recommendations.emit(ResponseState.Error(it.error ?: "Error"))
                }
                isStartRequestForRecommendationAnime = false
            }
        }
    }

    var isStartRequestForRecommendationManga = false
    fun loadRecommendationManga(id: Int) {
        if (isStartRequestForRecommendationManga) {
            return
        }
        if (_recommendations.value !is ResponseState.Success) {
            _recommendations.value = ResponseState.Loading()
        }
        isStartRequestForRecommendationManga = true
        viewModelScope.launch(Dispatchers.IO) {
            val recommendations = repository.getMangaRecommendations(id)
            recommendations.collectLatest {
                if (it.isSuccess) {
                    if (it.data != null) {
                        _recommendations.emit(ResponseState.Success(it.data))
                    } else {
                        _recommendations.emit(ResponseState.Error("No Result Found"))
                    }
                } else {
                    _recommendations.emit(ResponseState.Error(it.error ?: "Error"))
                }
                isStartRequestForRecommendationManga = false
            }
        }
    }

    var isStartRequestForImagesAnime = false
    fun loadImagesAnime(id: Int) {
        if (isStartRequestForImagesAnime) {
            return
        }
        if (_images.value !is ResponseState.Success) {
            _images.value = ResponseState.Loading()
        }
        isStartRequestForImagesAnime = true
        viewModelScope.launch(Dispatchers.IO) {
            val images = repository.getImagesAnime(id)
            images.collectLatest {
                if (it.isSuccess) {
                    if (it.data != null) {
                        _images.emit(ResponseState.Success(it.data))
                    } else {
                        _images.emit(ResponseState.Error("No Result Found"))
                    }
                } else {
                    _images.emit(ResponseState.Error(it.error ?: "Error"))
                }
                isStartRequestForImagesAnime = false
            }
        }
    }

    var isStartRequestForImagesManga = false
    fun loadImagesManga(id: Int) {

        if (isStartRequestForImagesManga) {
            return
        }
        if (_images.value !is ResponseState.Success) {
            _images.value = ResponseState.Loading()
        }
        isStartRequestForImagesManga = true
        viewModelScope.launch(Dispatchers.IO) {
            val images = repository.getImagesManga(id)
            images.collectLatest {
                if (it.isSuccess) {
                    if (it.data != null) {
                        _images.emit(ResponseState.Success(it.data))
                    } else {
                        _images.emit(ResponseState.Error("No Result Found"))
                    }
                } else {
                    _images.emit(ResponseState.Error(it.error ?: "Error"))
                }
                isStartRequestForImagesManga = false
            }
        }
    }

    var isStartRequestForReviewsAnime = false
    fun loadReviewsAnime(id: Int) {
        if (isStartRequestForReviewsAnime) {
            return
        }
        if (_review.value !is ResponseState.Success) {
            _review.value = ResponseState.Loading()
        }
        isStartRequestForReviewsAnime = true
        viewModelScope.launch(Dispatchers.IO) {
            val reviews = repository.getAnimeReviews(id, 1)
            reviews.collectLatest {
                if (it.isSuccess) {
                    paginationReviews = it.data?.pagination
                    if (it.data != null) {
                        _review.emit(ResponseState.Success(it.data))
                    } else {
                        _review.emit(ResponseState.Error("No Result Found"))
                    }
                } else {
                    _review.emit(ResponseState.Error(it.error ?: "Error"))
                }
                isStartRequestForReviewsAnime = false
            }
        }
    }

    var isStartRequestForReviewsManga = false
    fun loadReviewsManga(id: Int) {
        if (isStartRequestForReviewsManga) {
            return
        }
        if (_review.value !is ResponseState.Success) {
            _review.value = ResponseState.Loading()
        }
        isStartRequestForReviewsManga = true
        viewModelScope.launch(Dispatchers.IO) {
            val reviews = repository.getMangaReviews(id, 1)
            reviews.collectLatest {
                if (it.isSuccess) {
                    paginationReviews = it.data?.pagination
                    if (it.data != null) {
                        _review.emit(ResponseState.Success(it.data))
                    } else {
                        _review.emit(ResponseState.Error("No Result Found"))
                    }
                    paginationReviews = it.data?.pagination
                } else {
                    _review.emit(ResponseState.Error(it.error ?: "Error"))
                }
                isStartRequestForReviewsManga = false
            }
        }
    }

    var isStartRequestForStaffAnime = false
    suspend fun loadStaffAnime(id: Int) {
        if (isStartRequestForStaffAnime) {
            return
        }
        if (_staff.value !is ResponseState.Success) {
            _staff.value = ResponseState.Loading()
        }
        isStartRequestForStaffAnime = true
        val staffs = repository.getAnimeStaff(id)
        staffs.collectLatest {
            if (it.isSuccess) {
                if (it.data != null) {
                    _staff.emit(ResponseState.Success(it.data))
                } else {
                    _staff.emit(ResponseState.Error("No Result Found"))
                }
            } else {
                _staff.emit(ResponseState.Error(it.error ?: "Error"))
            }
            isStartRequestForStaffAnime = false
        }
    }


    var isStartRequestForDataAnime = false
    fun loadDataAnime(id: Int) {
        if (isStartRequestForDataAnime) {
            return
        }
        if (_data.value !is ResponseState.Success) {
            _data.value = ResponseState.Loading()
        }
        isStartRequestForDataAnime = true
        viewModelScope.launch(Dispatchers.IO) {
            val anime = repository.getAnimeById(id)
            anime.collectLatest {
                if (it.isSuccess) {
                    if (it.data != null) {
                        _data.emit(ResponseState.Success(it.data.movieData))
                    } else {
                        _data.emit(ResponseState.Error("No Result Found"))
                    }
                } else {
                    _data.emit(ResponseState.Error(it.error ?: "Error"))
                }
                isStartRequestForDataAnime = false
            }
        }
    }

    var isStartRequestForDataManga = false
    fun loadDataManga(id: Int) {
        if (isStartRequestForDataManga) {
            return
        }
        if (_data.value !is ResponseState.Success) {
            _data.value = ResponseState.Loading()
        }
        isStartRequestForDataManga = true
        viewModelScope.launch(Dispatchers.IO) {
            val anime = repository.getMangaById(id)
            anime.collectLatest {
                if (it.isSuccess) {
                    if (it.data != null) {
                        _data.emit(ResponseState.Success(it.data.movieData))
                    } else {
                        _data.emit(ResponseState.Error("No Result Found"))
                    }
                } else {
                    _data.emit(ResponseState.Error(it.error ?: "Error"))
                }
                isStartRequestForDataManga = false
            }
        }
    }

    var isStartRequestForCharactersAnime = false
    fun loadCharactersAnime(id: Int) {
        if (isStartRequestForCharactersAnime) {
            return
        }
        if (_characters.value !is ResponseState.Success) {
            _characters.value = ResponseState.Loading()
        }
        isStartRequestForCharactersAnime = true
        viewModelScope.launch(Dispatchers.IO) {
            val charactersList = repository.getAnimeCharacters(id)
            charactersList.collectLatest {
                if (it.isSuccess) {
                    if (it.data != null) {
                        _characters.emit(ResponseState.Success(it.data))
                    } else {
                        _characters.emit(ResponseState.Error("No Result Found"))
                    }
                } else {
                    _characters.emit(ResponseState.Error(it.error ?: "Error"))
                }
                isStartRequestForCharactersAnime = false
            }
        }
    }

    var isStartRequestForCharactersManga = false
    fun loadCharactersManga(id: Int) {
        if (isStartRequestForCharactersManga) {
            return
        }
        if (_characters.value !is ResponseState.Success) {
            _characters.value = ResponseState.Loading()
        }
        isStartRequestForCharactersManga = true
        viewModelScope.launch(Dispatchers.IO) {
            val charactersList = repository.getMangaCharacters(id)
            charactersList.collectLatest {
                if (it.isSuccess) {
                    if (it.data != null) {
                        _characters.emit(ResponseState.Success(it.data))
                    } else {
                        _characters.emit(ResponseState.Error("No Result Found"))
                    }
                } else {
                    _characters.emit(ResponseState.Error(it.error ?: "Error"))
                }
                isStartRequestForCharactersManga = false
            }
        }
    }


    var isStartRequestForEpisodes = false
    suspend fun loadEpisodesAnime(id: Int) {
        if (isStartRequestForEpisodes) {
            return
        }
        isStartRequestForEpisodes = true
        if (_episodes.value !is ResponseState.Success) {
            _episodes.value = ResponseState.Loading()
        }
        val episodesList = repository.getAnimeEpisodes(id, 1)
        episodesList.collectLatest {
            if (it.isSuccess) {
                if (it.data != null) {
                    _episodes.emit(ResponseState.Success(it.data))
                } else {
                    _episodes.emit(ResponseState.Error("No Result Found"))
                }
                paginationEpisodes = it.data?.pagination
            } else {
                _episodes.emit(ResponseState.Error(it.error ?: "Error"))
            }
            isStartRequestForEpisodes = false
        }
    }

    private var _imagesCharacters =
        MutableStateFlow<ResponseState<List<ImagesData>>>(ResponseState.Loading())
    override val imagesCharacters = _imagesCharacters.asStateFlow()

    private var _charactersInfo =
        MutableStateFlow<ResponseState<CharacterInfo>>(ResponseState.Loading())
    override val charactersInfo = _charactersInfo.asStateFlow()


    override fun getCharacterInfo(id: Int) {
        viewModelScope.launch {
            if (_charactersInfo.value !is ResponseState.Loading) {
                _charactersInfo.value = ResponseState.Loading()
            }
            val info = repository?.getCharacterInfo(id)
            info?.collectLatest {
                if (it.isSuccess) {
                    if (it.data != null) {
                        _charactersInfo.emit(ResponseState.Success(it.data))
                    } else {
                        val error = it.error ?: it.toString()
                        _charactersInfo.emit(ResponseState.Error(error))
                    }
                } else {
                    val error = it.error ?: "No Internet"
                    _charactersInfo.emit(ResponseState.Error(error))
                }
            }
        }
    }

    override fun getImagesCharacter(id: Int) {
        viewModelScope.launch {
            if (_imagesCharacters.value !is ResponseState.Loading) {
                _imagesCharacters.value = ResponseState.Loading()
            }
            val info = repository?.getCharacterImages(id)
            info?.collectLatest {
                if (it.isSuccess) {
                    if (it.data?.imagesData != null) {
                        _imagesCharacters.emit(ResponseState.Success(it.data.imagesData.uniqueList()))
                    } else {
                        val error = it.error ?: "No DataImages Found"
                        _imagesCharacters.emit(ResponseState.Error(error))
                    }
                } else {
                    val error = it.error ?: "No Internet"
                    _imagesCharacters.emit(ResponseState.Error(error))
                }
            }
        }
    }


    fun refresh(id: Int, typeMovies: String) {
        viewModelScope.launch {
            _data.emit(ResponseState.Loading())
            loadDataAnime(id)

            if (typeMovies == TypeMovies.Anime.name) {
                if (_images.value !is ResponseState.Success) {
                    loadImagesAnime(id)
                }
                if (_episodes.value !is ResponseState.Success) {
                    loadEpisodesAnime(id)
                }
                if (_recommendations.value !is ResponseState.Success) {
                    loadRecommendationAnime(id)
                }
                if (_staff.value !is ResponseState.Success) {
                    loadStaffAnime(id)
                }
                if (_review.value !is ResponseState.Success) {
                    loadReviewsAnime(id)
                }
                if (_characters.value !is ResponseState.Success) {
                    loadCharactersAnime(id)
                }
            }
        }
    }

    var isLastVisibleIndexItemReviews by mutableStateOf(false)
        private set
    var isStartLoadingReviews = false
    var numberOfRecommendationsReviews = 1
    var pageReviews = 1
    fun getMoreReviews(id: Int, typeMovies: TypeMovies) {
        if (paginationReviews?.has_next_page == false || isStartLoadingReviews || !isUserHaveInternet) {
            return
        }
        ++pageReviews
        isLastVisibleIndexItemReviews = true
        isStartLoadingReviews = true
        ++numberOfRecommendationsReviews
        Log.d(
            "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq",
            "getMoreEpsides: ${paginationReviews?.has_next_page}"
        )
        val dataEp = when (typeMovies) {
            TypeMovies.Manga -> repository.getMangaReviews(id = id, page = pageReviews)
            else -> repository.getAnimeReviews(id = id, page = pageReviews)

        }
        viewModelScope.launch {
            dataEp.collectLatest {
                if (it.isSuccess) {
                    val oldList = (_review.value as ResponseState.Success).data.data
                    val nowList = it.data?.data?.uniqueList() ?: listOf()
                    val targetList = oldList.toMutableList()
                    targetList.addAll(nowList)
                    paginationReviews = it.data?.pagination
                    var result = Reviews(targetList, paginationReviews!!)
                    _review.emit(ResponseState.Success(result))
                } else {
                    --pageReviews
                }
                isStartLoadingReviews = false
                if (numberOfRecommendationsReviews <= 2) {
                    isLastVisibleIndexItemReviews = false
                }
            }
        }

    }

    init {
        var observerCallback = repository.observeForInternet(viewModelScope)
        viewModelScope.launch {
            observerCallback.collectLatest {
                isUserHaveInternet = it
            }
        }
    }

    var isUserHaveInternet = false

    var isLastVisibleIndexItem by mutableStateOf(false)
        private set
    var isStartLoading = false
    var numberOfRecommendations = 1
    var pageEpisodes = 1
    fun getMoreEpsides(id: Int) {
        if (paginationEpisodes?.has_next_page == false || isStartLoading || !isUserHaveInternet) {
            return
        }
        ++pageEpisodes
        isLastVisibleIndexItem = true
        isStartLoading = true
        ++numberOfRecommendations
        Log.d("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq", "getMoreEpsides: QQQQQQQQQQQQQQQQQQ")
        val dataEp = repository.getAnimeEpisodes(id = id, page = pageEpisodes)
        viewModelScope.launch {
            dataEp.collectLatest {
                if (it.isSuccess) {
                    val oldList = (_episodes.value as ResponseState.Success).data.data
                    val nowList = it.data?.data?.uniqueList() ?: listOf()
                    val targetList = oldList.toMutableList()
                    targetList.addAll(nowList)
                    paginationEpisodes = it.data?.pagination
                    var result = Episodes(targetList, paginationEpisodes!!)
                    _episodes.emit(ResponseState.Success(result))
                } else {
                    --pageEpisodes
                }
                isStartLoading = false
                if (numberOfRecommendations <= 2) {
                    isLastVisibleIndexItem = false
                }
            }
        }
    }


    override var isCallApi: Boolean = false


    override var isCallApiPeople: Boolean = false


    private val _imagesPeople =
        MutableStateFlow<ResponseState<List<DataImages>>>(ResponseState.Loading())

    private val _infoPeople = MutableStateFlow<ResponseState<PeopleInfo>>(ResponseState.Loading())


    override val imagesPeople: StateFlow<ResponseState<List<DataImages>>>
        get() = _imagesPeople.asStateFlow()
    override val infoPeople: StateFlow<ResponseState<PeopleInfo>>
        get() = _infoPeople.asStateFlow()


    override fun getPeopleInfo(id: Int) {
        viewModelScope.launch {
            if (_infoPeople.value !is ResponseState.Loading) {
                _infoPeople.value = ResponseState.Loading()
            }
            val info = repository?.getPeopleInfo(id)
            info?.collectLatest {
                if (it.isSuccess) {
                    if (it.data != null) {
                        _infoPeople.emit(ResponseState.Success(it.data))
                    } else {
                        val error = it.error ?: it.toString()
                        _infoPeople.emit(ResponseState.Error(error))
                    }
                } else {
                    val error = it.error ?: "No Internet"
                    _infoPeople.emit(ResponseState.Error(error))
                }
            }
        }
    }

    override fun getImagesPeople(id: Int) {
        viewModelScope.launch {
            if (_imagesPeople.value !is ResponseState.Loading) {
                _imagesPeople.value = ResponseState.Loading()
            }
            val info = repository?.getPeopleImage(id)
            info?.collectLatest {
                if (it.isSuccess) {
                    if (it.data?.dataImages != null) {
                        _imagesPeople.emit(ResponseState.Success(it.data.dataImages.uniqueList()))
                    } else {
                        val error = it.error ?: "No DataImages Found"
                        _imagesPeople.emit(ResponseState.Error(error))
                    }
                } else {
                    val error = it.error ?: "No Internet"
                    _imagesPeople.emit(ResponseState.Error(error))
                }
            }
        }
    }


    suspend fun insertMovies(movieLocal: MovieLocal) {
        repository.insertMovies(movieLocal)
    }
    suspend fun isIdInDatabase(id:Int): Boolean {
        return repository.isIdInDatabase(id)
    }

}