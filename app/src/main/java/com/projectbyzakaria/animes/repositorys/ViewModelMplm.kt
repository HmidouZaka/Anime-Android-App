package com.projectbyzakaria.animes.repositorys

import androidx.lifecycle.viewModelScope
import com.projectbyzakaria.animes.data.dao.relotion.character_info.CharacterInfo
import com.projectbyzakaria.animes.data.dao.relotion.image_people.DataImages
import com.projectbyzakaria.animes.data.dao.relotion.images_characters.ImagesData
import com.projectbyzakaria.animes.data.dao.relotion.peopel_all_info.PeopleInfo
import com.projectbyzakaria.animes.utilt.ResponseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

interface ViewModelMplm {



    var isCallApi: Boolean
    val imagesCharacters:StateFlow<ResponseState<List<ImagesData>>>
    val charactersInfo:StateFlow<ResponseState<CharacterInfo>>

    var isCallApiPeople: Boolean
    val imagesPeople:StateFlow<ResponseState<List<DataImages>>>
    val infoPeople:StateFlow<ResponseState<PeopleInfo>>

    fun getCharacterInfo(id:Int)

    fun getImagesCharacter(id:Int)


    fun getPeopleInfo(id:Int)

    fun getImagesPeople(id:Int)
}