package com.projectbyzakaria.animes.ui.view_models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.projectbyzakaria.animes.model.User
import com.projectbyzakaria.animes.utilt.FilterContent
import com.projectbyzakaria.animes.utilt.Screens
import com.projectbyzakaria.animes.utilt.TypeMovies

class StateUiViewModel : ViewModel() {

    var positionScrolling by mutableStateOf(0f)
        private set
    var typeMovies by mutableStateOf(TypeMovies.Anime.name)
        private set

    fun changePositionScroll(newPosition:Float){
        val newOffset = positionScrolling + newPosition
        positionScrolling = newOffset.coerceIn(-150f, 0f)
    }
    fun goToAntherScreen(typeMovies: TypeMovies){
        val typeName = typeMovies.name
        this.typeMovies = typeName
    }

    fun hideNavigation(){
        positionScrolling = -150f
    }
    fun showNavigation(){
        positionScrolling = 0f
    }


    fun gotoInstialValue(){
            positionScrolling = 0f
    }

    var dialogIsShowIt by mutableStateOf(false)
        private set

    fun showDialog(){
        dialogIsShowIt = true
    }

    fun hideDialog(){
        dialogIsShowIt = false
    }


    var currentPage by mutableStateOf(Screens.HOME.name)

    fun gotoPage(route: String?) {
        if (route != null) {
            currentPage = route
        }
    }

    var filter by mutableStateOf(FilterContent.Anime)
        private set
    fun changeFilter(filterContent: FilterContent){
        filter = filterContent
    }


}