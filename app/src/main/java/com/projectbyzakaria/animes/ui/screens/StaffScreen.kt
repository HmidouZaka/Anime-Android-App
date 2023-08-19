package com.projectbyzakaria.animes.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.projectbyzakaria.animes.R
import com.projectbyzakaria.animes.data.dao.characters_search.Images
import com.projectbyzakaria.animes.data.dao.characters_search.Jpg
import com.projectbyzakaria.animes.data.dao.characters_search.Webp
import com.projectbyzakaria.animes.ui.component.ErrorShow
import com.projectbyzakaria.animes.ui.component.LoadingProgress
import com.projectbyzakaria.animes.ui.component.StaffComponent
import com.projectbyzakaria.animes.ui.view_models.AnimeDtelesViewModel
import com.projectbyzakaria.animes.utilt.ResponseState
import com.projectbyzakaria.animes.utilt.Screens
import com.projectbyzakaria.animes.utilt.uniqueList


@Composable
fun StaffScreen(
    viewModel: AnimeDtelesViewModel,
    modifier: Modifier = Modifier,
    id: Int,
    navHostController: NavHostController
) {
    val characters = viewModel.staff.collectAsState()
    if (characters.value is ResponseState.Loading) {
        LoadingProgress(modifier = Modifier.fillMaxSize())
    } else if (characters.value is ResponseState.Success) {
        val count = when (LocalConfiguration.current.orientation) {
            1 -> 2
            else -> 3
        }
        val data = (characters.value as ResponseState.Success).data.data
        val dataMap = data.map {
            com.projectbyzakaria.animes.data.dao.characters_search.Data(
                name = it.person.name,
                mal_id = it.person.mal_id,
                images = Images(
                    Jpg(it.person.images.jpg.image_url),
                    Webp("", "")
                ),
                url=it.person.url, nicknames=listOf(),  name_kanji = "", about = "0" , favorites = -1
            )
        }.uniqueList()
        if (dataMap.isNotEmpty()){
            Column(
                modifier.fillMaxSize()
            ) {
                val gridState = rememberLazyGridState()

                LazyVerticalGrid(
                    columns = GridCells.Fixed(count),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(3.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    state = gridState
                ) {
                    itemsIndexed(dataMap,key={i,it->it.mal_id?:i}) {i,it->
                        StaffComponent(staff = it) {
                            viewModel.isCallApiPeople = false
                            navHostController.navigate(Screens.People.name+"?idPeople=${it.mal_id?:-1}")
                        }
                    }
                }
            }
        }else{
            ErrorShow(
                error = "No Stuff Found",
                onClick = {
                    viewModel.refrechStuff(id)
                }, modifier = Modifier.fillMaxSize()
            )
        }

    } else {
        val error = (characters.value as ResponseState.Error).message
        ErrorShow(
            error = error,
            onClick = {
                viewModel.refrechStuff(id)
            }, modifier = Modifier.fillMaxSize()
        )
    }
}


