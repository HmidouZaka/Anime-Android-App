package com.projectbyzakaria.animes.ui.view_models

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectbyzakaria.animes.data.dao.relotion.character_info.CharacterInfo
import com.projectbyzakaria.animes.data.dao.relotion.image_people.DataImages
import com.projectbyzakaria.animes.data.dao.relotion.images_characters.ImagesData
import com.projectbyzakaria.animes.data.dao.relotion.peopel_all_info.PeopleInfo
import com.projectbyzakaria.animes.data.dao.top_anime_object.Data
import com.projectbyzakaria.animes.data.dao.top_anime_object.Pagination
import com.projectbyzakaria.animes.model.MovieLocal
import com.projectbyzakaria.animes.model.User
import com.projectbyzakaria.animes.repositorys.MainRepository
import com.projectbyzakaria.animes.repositorys.ViewModelMplm
import com.projectbyzakaria.animes.utilt.ResponseState
import com.projectbyzakaria.animes.utilt.TypeMovies
import com.projectbyzakaria.animes.utilt.uniqueList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.ceil

@HiltViewModel
class DataViewModel @Inject constructor(
    private val repository: MainRepository,
) : ViewModel(), ViewModelMplm {
    var query: String? = null


    private var _topAnime =
        MutableStateFlow<ResponseState<MutableList<Data>>>(ResponseState.Loading())
    val topAnime = _topAnime.asStateFlow()

    private var _topManga =
        MutableStateFlow<ResponseState<MutableList<Data>>>(ResponseState.Loading())
    val topManga = _topManga.asStateFlow()


    private var responseInfoAnime: Pagination? = null
    private var responseInfoManga: Pagination? = null

    init {
        getTopAnime()
    }

    private fun getTopAnime() {
        viewModelScope.launch {
            val result = repository.getTopAnime(1)
            result.collectLatest {
                if (it.isSuccess) {
                    _topAnime.emit(
                        ResponseState.Success(
                            it.data?.data?.uniqueList()?.toMutableList() ?: mutableListOf()
                        )
                    )
                    responseInfoAnime = it.data?.pagination
                } else {
                    _topAnime.emit(ResponseState.Error(it.error ?: "error "))
                }
                cancel()
            }
        }
    }

    private fun getTopManga() {
        viewModelScope.launch {
            val result = repository.getTopManga(1)
            result.collectLatest {
                if (it.isSuccess) {
                    _topManga.emit(
                        ResponseState.Success(
                            it.data?.data?.uniqueList()?.toMutableList() ?: mutableListOf()
                        )
                    )
                    responseInfoManga = it.data?.pagination
                } else {
                    _topManga.emit(ResponseState.Error(it.error ?: "error "))
                }
                cancel()
            }
        }
    }


    fun loadTopManga() {
        if (_topManga.value is ResponseState.Loading) {
            getTopManga()
        }
    }

    fun refresh(typeMovies: TypeMovies) {
        when (typeMovies) {
            TypeMovies.Anime -> {
                if (topAnime.value !is ResponseState.Success) {
                    _topAnime.value = (ResponseState.Loading())
                    getTopAnime()
                }
            }
            TypeMovies.Manga -> {
                if (topManga.value !is ResponseState.Success) {
                    _topManga.value = (ResponseState.Loading())
                    getTopManga()
                }
            }
        }
    }

    var internetIsAvailable = false
    fun observerInternet() = viewModelScope.launch {
        repository.observeForInternet(viewModelScope).collectLatest {
            internetIsAvailable = it
            Log.d("testsssssssssssssssssssssssssss", "observerInternet: observerInternet $it")
        }
    }

    private fun loadMoreAnime(onError: (String) -> Unit) {
        Log.d("testsssssssssssssssssssssssssss", "loadMoreAnime: Anime")
        responseInfoAnime?.let {
            ++numberOfRecomposition
            if (it.has_next_page == true) {
                Log.d(
                    "testsssssssssssssssssssssssssss",
                    "numberOfRecomposition: $numberOfRecomposition"
                )
                viewModelScope.launch(Dispatchers.IO) {
                    val position = responseInfoAnime?.current_page ?: 1
                    val newData = repository.getTopAnime(position + 1)
                    newData.collectLatest {
                        isStartLoadingAnime = false
//                            Log.d("testsssssssssssssssssssssssssss", "loadMoreAnime: $it")

                        if (it.isSuccess) {
                            numberOfRecomposition = 1
                            Log.d("testsssssssssssssssssssssssssss", "loadMoreAnime: $it")
                            val nowData = it.data?.data ?: emptyList()
                            val oldList = (_topAnime.value as ResponseState.Success).data
                            oldList.addAll(nowData)
                            responseInfoAnime = it.data?.pagination
                            _topAnime.emit(
                                ResponseState.Success(
                                    oldList.uniqueList().toMutableList()
                                )
                            )
                        } else {
                            val error = it.error ?: "No Internet"
                            onError(error)
                        }
                        if (numberOfRecomposition <= 2) {
                            isLastVisibleItemForAnime = false
                        }
                        cancel()
                    }
                }
            }
        }
    }


    private fun loadMoreManga(onError: (String) -> Unit) {
        Log.d("testsssssssssssssssssssssssssss", "loadMoreManga: manga")
        ++numberOfRecompositionManga
        responseInfoManga?.let {
            Log.d(
                "testsssssssssssssssssssssssssss",
                "numberOfRecompositionManga: $numberOfRecompositionManga"
            )
            if (it.has_next_page == true) {
                viewModelScope.launch(Dispatchers.IO) {
                    val position = responseInfoManga?.current_page ?: 1
                    val newData = repository.getTopManga(position + 1)
                    newData.collectLatest {
                        if (it.isSuccess) {
                            numberOfRecompositionManga = 1
                            val nowData = it.data?.data ?: emptyList()
                            val oldList = (_topManga.value as ResponseState.Success).data
                            oldList.addAll(nowData)
                            responseInfoManga = it.data?.pagination
                            _topManga.emit(
                                ResponseState.Success(
                                    oldList.uniqueList().toMutableList()
                                )
                            )
                        } else {
                            val error = it.error ?: "No Internet"
                            onError(error)
                        }
                        if (numberOfRecompositionManga <= 2) {
                            isLastVisibleItemForManga = false
                        }
                        isStartLoadingManga = false
                        cancel()
                    }
                }
            }
        }
    }

    var numberOfRecomposition = 1
    var numberOfRecompositionManga = 1

    var isStartLoadingAnime = false
    var isStartLoadingManga = false
    var isLastVisibleItemForManga by mutableStateOf(false)
    var isLastVisibleItemForAnime by mutableStateOf(false)
        private set

    fun showProgressBarAnime(onError: (String) -> Unit) {
        if (!isStartLoadingAnime && internetIsAvailable) {
            isStartLoadingAnime = true
            isLastVisibleItemForAnime = true
            loadMoreAnime(onError)
        }
    }

    fun showProgressBarManga(onError: (String) -> Unit) {
        if (!isStartLoadingManga && internetIsAvailable) {
            isStartLoadingManga = true
            isLastVisibleItemForManga = true
            loadMoreManga(onError)
        }
    }


    private var _searchAnime =
        MutableStateFlow<ResponseState<MutableList<Data>>>(ResponseState.Error("Enter Su-meting"))
    val searchAnime = _searchAnime.asStateFlow()

    private var _searchManga =
        MutableStateFlow<ResponseState<MutableList<Data>>>(ResponseState.Error("Enter Su-meting"))
    val searchManga = _searchManga.asStateFlow()

    private var _searchCharacters =
        MutableStateFlow<ResponseState<MutableList<com.projectbyzakaria.animes.data.dao.characters_search.Data>>>(
            ResponseState.Error("Enter Su-meting")
        )
    val searchCharacters = _searchCharacters.asStateFlow()

    private var _searchPeople =
        MutableStateFlow<ResponseState<MutableList<com.projectbyzakaria.animes.data.dao.pepole.Data>>>(
            ResponseState.Error("Enter Su-meting")
        )
    val searchPeople = _searchPeople.asStateFlow()


    fun searchAnimeFirstFilter(query: String) {
        if (_searchAnime.value !is ResponseState.Loading) _searchAnime.value =
            ResponseState.Loading()
        if (!this@DataViewModel.query.isNullOrEmpty()) {
            viewModelScope.launch {
                val result = repository.searchAnime(this@DataViewModel.query ?: "", 1)
                result.collectLatest {
                    if (it.isSuccess) {
                        if (!it.data?.data.isNullOrEmpty()) {
                            _searchAnime.emit(
                                ResponseState.Success(
                                    it.data?.data?.toMutableList() ?: mutableListOf()
                                )
                            )
                            responseInfoAnimeSearch = it.data?.pagination
                        } else {
                            _searchAnime.value = (ResponseState.Error("No Result"))
                        }
                    } else {
                        _searchAnime.emit(ResponseState.Error(it.error ?: "error "))
                    }
                    cancel()
                }

            }
        } else {
            _searchAnime.value = (ResponseState.Error("Enter Su-meting"))
        }
    }


    fun searchMangaFirstFilter(query: String) {
        if (_searchAnime.value !is ResponseState.Loading) _searchManga.value =
            ResponseState.Loading()
        if (!this@DataViewModel.query.isNullOrEmpty()) {
            viewModelScope.launch {
                val result = repository.searchManga(this@DataViewModel.query ?: "", 1)
                result.collectLatest {
                    if (it.isSuccess) {
                        if (!it.data?.data.isNullOrEmpty()) {
                            _searchManga.emit(
                                ResponseState.Success(
                                    it.data?.data?.toMutableList() ?: mutableListOf()
                                )
                            )
                            responseInfoMangaSearch = it.data?.pagination
                        } else {
                            _searchManga.value = (ResponseState.Error("No Result"))
                        }
                    } else {
                        _searchManga.emit(ResponseState.Error(it.error ?: "error "))
                    }
                    cancel()
                }

            }
        } else {
            _searchManga.value = (ResponseState.Error("Enter Su-meting"))
        }
    }

    fun searchPeopleFirstFilter() {
        if (_searchPeople.value !is ResponseState.Loading) _searchPeople.value =
            ResponseState.Loading()
        if (!this@DataViewModel.query.isNullOrEmpty()) {
            viewModelScope.launch {
                val result = repository.searchPeople(this@DataViewModel.query ?: "", 1)
                result.collectLatest {
                    if (it.isSuccess) {
                        if (!it.data?.data.isNullOrEmpty()) {
                            _searchPeople.emit(
                                ResponseState.Success(
                                    it.data?.data?.toMutableList() ?: mutableListOf()
                                )
                            )
                            responseInfoPeopleSearch = it.data?.pagination
                            Log.d(
                                "testsssssssssssssssssssssssssss",
                                "showProgressBarCharactersSearch: ${it.data?.pagination}"
                            )

                        } else {
                            _searchPeople.value = (ResponseState.Error("No Result"))
                        }
                    } else {
                        _searchPeople.emit(ResponseState.Error(it.error ?: "error "))
                    }
                    cancel()
                }
            }
        } else {
            _searchPeople.value = (ResponseState.Error("Enter Su-meting"))
        }
    }

    fun searchCharacterFirstFilter() {

        if (_searchCharacters.value !is ResponseState.Loading) _searchCharacters.value =
            ResponseState.Loading()
        if (!this@DataViewModel.query.isNullOrEmpty()) {
            viewModelScope.launch {
                val result = repository.searchCharacters(this@DataViewModel.query ?: "", 1)
                result.collectLatest {
                    if (it.isSuccess) {
                        if (!it.data?.data.isNullOrEmpty()) {
                            _searchCharacters.emit(
                                ResponseState.Success(
                                    it.data?.data?.toMutableList() ?: mutableListOf()
                                )
                            )
                            responseInfoCharactersSearch = it.data?.pagination
                        } else {
                            _searchCharacters.value = (ResponseState.Error("No Result"))
                        }
                    } else {
                        _searchCharacters.emit(ResponseState.Error(it.error ?: "error "))
                    }
                    cancel()
                }

            }
        } else {
            _searchCharacters.value = (ResponseState.Error("Enter Su-meting"))
        }
    }

    private var responseInfoCharactersSearch: com.projectbyzakaria.animes.data.dao.characters_search.Pagination? =
        null
    var numberOfRecompositionCharactersSearch = 1
    var isStartLoadingCharactersSearch = false
    var isLastVisibleItemForCharactersSearch by mutableStateOf(false)
    fun showProgressBarCharactersSearch(onError: (String) -> Unit) {
        Log.d(
            "testsssssssssssssssssssssssssss",
            "showProgressBarCharactersSearch: ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;"
        )
        if (!isStartLoadingCharactersSearch && internetIsAvailable && responseInfoCharactersSearch?.has_next_page == true) {
            isStartLoadingCharactersSearch = true
            isLastVisibleItemForCharactersSearch = true
            Log.d(
                "testsssssssssssssssssssssssssss",
                "showProgressBarCharactersSearch: $internetIsAvailable"
            )
            loadMoreCharacters(onError)
        }
    }

    var responseInfoPeopleSearch: com.projectbyzakaria.animes.data.dao.pepole.Pagination? = null
    var numberOfRecompositionPeopleSearch = 1
    var isLastVisibleItemForPeopleSearch by mutableStateOf(false)
    var isStartLoadingPeopleSearch = false
    fun showProgressBarPeopleSearch(onError: (String) -> Unit) {
        Log.d(
            "testsssssssssssssssssssssssssss",
            "showProgressBarCharactersSearch: ${responseInfoPeopleSearch?.has_next_page}"
        )
        if (!isStartLoadingPeopleSearch && internetIsAvailable && responseInfoPeopleSearch?.has_next_page == true) {
            isStartLoadingCharactersSearch = true
            isLastVisibleItemForPeopleSearch = true
            Log.d(
                "testsssssssssssssssssssssssssss",
                "showProgressBarCharactersSearch: $internetIsAvailable"
            )
            loadMorePeople(onError)
        }
    }

    var pagePeople = 1

    private fun loadMorePeople(onError: (String) -> Unit) {
        Log.d("testsssssssssssssssssssssssssss", "loadMoreManga: manga")
        ++numberOfRecompositionPeopleSearch
        responseInfoPeopleSearch?.let {
            Log.d(
                "testsssssssssssssssssssssssssss",
                "numberOfRecompositionManga: $numberOfRecompositionPeopleSearch"
            )
            Log.d(
                "testsssssssssssssssssssssssssss",
                "numberOfRecompositionManga: ${it.has_next_page}"
            )
            if (it.has_next_page == true) {
                viewModelScope.launch(Dispatchers.IO) {
                    val position = pagePeople + 1
                    val newData = repository.searchPeople(query ?: "", position)
                    newData.collectLatest {
                        if (it.isSuccess) {
                            numberOfRecompositionPeopleSearch = 1
                            val nowData = it.data?.data ?: emptyList()
                            val oldList = (_searchPeople.value as ResponseState.Success).data
                            oldList.addAll(nowData)
                            responseInfoPeopleSearch = it.data?.pagination
                            ++pagePeople
                            _searchPeople.emit(ResponseState.Success(oldList.toMutableList()))
                        } else {
                            val error = it.error ?: "No Internet"
                            onError(error)
                        }
                        if (numberOfRecompositionPeopleSearch <= 2) {
                            isLastVisibleItemForPeopleSearch = false
                        }
                        isStartLoadingPeopleSearch = false
                    }
                }
            }
        }
    }

    private fun loadMoreCharacters(onError: (String) -> Unit) {
        Log.d("testsssssssssssssssssssssssssss", "loadMoreManga: manga")
        ++numberOfRecompositionCharactersSearch
        responseInfoCharactersSearch?.let {
            Log.d(
                "testsssssssssssssssssssssssssss",
                "numberOfRecompositionManga: $numberOfRecompositionCharactersSearch"
            )
            Log.d(
                "testsssssssssssssssssssssssssss",
                "numberOfRecompositionManga: ${it.has_next_page}"
            )
            if (it.has_next_page == true) {
                viewModelScope.launch(Dispatchers.IO) {
                    val position = responseInfoCharactersSearch?.current_page ?: 1
                    val newData = repository.searchCharacters(query ?: "", position + 1)
                    newData.collectLatest {
                        if (it.isSuccess) {
                            numberOfRecompositionCharactersSearch = 1
                            val nowData = it.data?.data ?: emptyList()
                            val oldList = (_searchCharacters.value as ResponseState.Success).data
                            oldList.addAll(nowData)
                            responseInfoCharactersSearch = it.data?.pagination
                            _searchCharacters.emit(ResponseState.Success(oldList.toMutableList()))
                        } else {
                            val error = it.error ?: "No Internet"
                            onError(error)
                        }
                        if (numberOfRecompositionCharactersSearch <= 2) {
                            isLastVisibleItemForCharactersSearch = false
                        }
                        isStartLoadingCharactersSearch = false
                    }
                }
            }
        }
    }


    private var responseInfoAnimeSearch: Pagination? = null
    var numberOfRecompositionAnimeSearch = 1
    var isStartLoadingAnimeSearch = false
    var isLastVisibleItemForAnimeSearch by mutableStateOf(false)
    fun showProgressBarAnimeSearch(onError: (String) -> Unit) {
        if (!isStartLoadingAnimeSearch && internetIsAvailable && responseInfoAnimeSearch?.has_next_page == true) {
            isStartLoadingAnimeSearch = true
            isLastVisibleItemForAnimeSearch = true
            loadMoreAnimeSearch(onError)
        }
    }


    private fun loadMoreAnimeSearch(onError: (String) -> Unit) {
        Log.d("testsssssssssssssssssssssssssss", "loadMoreManga: manga")
        ++numberOfRecompositionAnimeSearch
        responseInfoAnimeSearch?.let {
            Log.d(
                "testsssssssssssssssssssssssssss",
                "numberOfRecompositionManga: $numberOfRecompositionAnimeSearch"
            )
            Log.d(
                "testsssssssssssssssssssssssssss",
                "numberOfRecompositionManga: ${it.has_next_page}"
            )
            if (it.has_next_page == true) {
                viewModelScope.launch(Dispatchers.IO) {
                    val position = responseInfoAnimeSearch?.current_page ?: 1
                    val newData = repository.searchAnime(query ?: "", position + 1)
                    newData.collectLatest {
                        if (it.isSuccess) {
                            numberOfRecompositionAnimeSearch = 1
                            val nowData = it.data?.data ?: emptyList()
                            val oldList = (_searchAnime.value as ResponseState.Success).data
                            oldList.addAll(nowData)
                            var result = oldList.toMutableList()
                            _searchAnime.value = (ResponseState.Success(result))
                            responseInfoAnimeSearch = it.data?.pagination
                        } else {
                            val error = it.error ?: "No Internet"
                            onError(error)
                        }
                        isStartLoadingAnimeSearch = false
                        if (numberOfRecompositionAnimeSearch <= 2) {
                            isLastVisibleItemForAnimeSearch = false
                        }

                    }
                }
            }
        }
    }

    private var responseInfoMangaSearch: Pagination? = null
    var numberOfRecompositionMangaSearch = 1
    var isStartLoadingMangaSearch = false
    var isLastVisibleItemForMangaSearch by mutableStateOf(false)
    fun showProgressBarMangaSearch(onError: (String) -> Unit) {
        Log.d(
            "testsssssssssssssssssssssssssss",
            "showProgressBarMangaSearch: ${responseInfoMangaSearch?.has_next_page}"
        )
        if (!isStartLoadingMangaSearch && internetIsAvailable && responseInfoMangaSearch?.has_next_page == true) {
            Log.d(
                "testsssssssssssssssssssssssssss",
                "has_next_page: lllllllllllllllllllllllllllllllll"
            )
            isStartLoadingMangaSearch = true
            isLastVisibleItemForMangaSearch = true
            loadMoreMangaSearch(onError)
        }
    }

    private fun loadMoreMangaSearch(onError: (String) -> Unit) {
        Log.d("testsssssssssssssssssssssssssss", "loadMoreManga: manga")
        ++numberOfRecompositionMangaSearch
        responseInfoMangaSearch?.let {
            Log.d(
                "testsssssssssssssssssssssssssss",
                "numberOfRecompositionManga: $numberOfRecompositionMangaSearch"
            )
            Log.d(
                "testsssssssssssssssssssssssssss",
                "numberOfRecompositionManga: ${it.has_next_page}"
            )
            if (it.has_next_page == true) {
                viewModelScope.launch(Dispatchers.IO) {
                    val position = responseInfoMangaSearch?.current_page ?: 1
                    val newData = repository.searchManga(query ?: "", position + 1)
                    newData.collectLatest {
                        if (it.isSuccess) {
                            numberOfRecompositionMangaSearch = 1
                            val nowData = it.data?.data ?: emptyList()
                            val oldList = (_searchManga.value as ResponseState.Success).data
                            oldList.addAll(nowData)
                            responseInfoMangaSearch = it.data?.pagination
                            _searchManga.emit(ResponseState.Success(oldList.toMutableList()))
                        } else {
                            val error = it.error ?: "No Internet"
                            onError(error)
                        }
                        if (numberOfRecompositionMangaSearch <= 2) {
                            isLastVisibleItemForMangaSearch = false
                        }
                        isStartLoadingMangaSearch = false
                    }
                }
            }
        }
    }


    override var isCallApi: Boolean = false

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


    fun deleteMovie(movieLocal: MovieLocal) {
        viewModelScope.launch {
            repository.deleteMovie(movieLocal)
        }
    }

    val localMovies = repository.getAllMovies()

    private val _userImage = MutableLiveData<Bitmap?>()
    val userImage: LiveData<Bitmap?> get() = _userImage

    private val _userName = MutableLiveData<String?>()
    val userName: LiveData<String?> get() = _userName

    private val _userEmail = MutableLiveData<String?>()
    val userEmail: LiveData<String?> get() = _userEmail

    private val _userFollow = MutableLiveData<Int>()
    val userFollow: LiveData<Int> get() = _userFollow

    val userFavorites = repository.getNumberOfFavoret().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        0
    )

    var name = ""
    var email = ""


    fun changeName(text:String){
        _userName.value = text
    }
    fun changeEmail(text:String){
        _userEmail.value = text
    }

    var isCallGetUserFunction = false

    fun getUser() {
        if (isCallGetUserFunction){
            return
        }
        isCallGetUserFunction = true
        viewModelScope.launch {
            if (repository.isHaveUserInDataBase()) {
                val result = repository.getUser()
                _userEmail.value = result.email
                _userFollow.value = result.followrs
                _userImage.value = result.image
                _userName.value = result.name
                this@DataViewModel.name = result.name ?: ""
                this@DataViewModel.email = result.email ?: ""
            }else{
                insertUser(User())
            }
        }
    }

    fun upDateName(name: String) {
        viewModelScope.launch {
            if (repository.isHaveUserInDataBase()) {
                val result = repository.upDateName(name)
                this@DataViewModel.name = result.name.toString()
                _userName.value = result.name
            }
        }
    }

    fun upDateEmail(email: String) {
        viewModelScope.launch {
            if (repository.isHaveUserInDataBase()) {
                val result = repository.upDateEmail(email)
                this@DataViewModel.email = result.email.toString()
                _userEmail.value = result.email
            }
        }
    }

    fun upDateImage(image: Bitmap) {
        viewModelScope.launch {
            if (repository.isHaveUserInDataBase()) {
                val result = repository.upDateImage(image)
                _userImage.value = result.image
            }
        }
    }



    private fun insertUser(user: User) {
        viewModelScope.launch {
            if (!repository.isHaveUserInDataBase()) {
                val result = repository.insertUser(user)
                _userEmail.value = result.email
                _userFollow.value = result.followrs
                _userImage.value = result.image
                _userName.value = result.name
            } else {
                cancel()
            }
        }
    }

}

