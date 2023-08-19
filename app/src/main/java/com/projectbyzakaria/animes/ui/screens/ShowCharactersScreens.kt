package com.projectbyzakaria.animes.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.projectbyzakaria.animes.R
import com.projectbyzakaria.animes.data.dao.characters_search.Data
import com.projectbyzakaria.animes.data.dao.characters_search.Images
import com.projectbyzakaria.animes.data.dao.characters_search.Jpg
import com.projectbyzakaria.animes.data.dao.characters_search.Webp
import com.projectbyzakaria.animes.ui.component.CharacterCard
import com.projectbyzakaria.animes.ui.component.ErrorShow
import com.projectbyzakaria.animes.ui.component.LoadingProgress
import com.projectbyzakaria.animes.ui.view_models.AnimeDtelesViewModel
import com.projectbyzakaria.animes.utilt.ResponseState
import com.projectbyzakaria.animes.utilt.Screens
import com.projectbyzakaria.animes.utilt.TypeMovies
import com.projectbyzakaria.animes.utilt.uniqueList

@Composable
fun ShowCharactersScreens(
    viewModel: AnimeDtelesViewModel,
    modifier: Modifier = Modifier,
    id: Int,
    typeMovies: TypeMovies,
    navHostController: NavHostController
) {
    var characters = viewModel.characters.collectAsState()
    if (characters.value is ResponseState.Loading) {
        LoadingProgress(modifier = Modifier.fillMaxSize())
    } else if (characters.value is ResponseState.Success) {
        val count = when (LocalConfiguration.current.orientation) {
            1 -> 2
            else -> 3
        }
        Column(
            modifier.fillMaxSize()
        ) {
            val gridState = rememberLazyGridState()
            val data = (characters.value as ResponseState.Success).data.data.uniqueList().map {
                Data(
                    it.character.name,
                    it.favorites,
                    Images(
                        Jpg(it.character.images.jpg.image_url),
                        Webp(it.character.images.jpg.image_url, "")
                    ), mal_id = it.character.mal_id, it.character.name, it.character.name, listOf(),
                    it.character.url
                )
            }
            if (data.isNotEmpty()) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(count),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(3.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    state = gridState
                ) {
                    itemsIndexed(data,key={i,it->it.mal_id?:i}) {index,it->
                        CharacterCard(data = it) {
                            viewModel.isCallApi = false
                            navHostController.navigate(Screens.CHARACTER.name+"?idCharacter=${it.mal_id ?: -1}")
                        }
                    }
                }
            } else {
                ErrorShow(
                    error = "No Character Found",
                    onClick = null,
                    image = R.drawable.finish, modifier = Modifier.fillMaxSize()
                )
            }

        }
    } else {
        val error = (characters.value as ResponseState.Error).message
        ErrorShow(
            error = error,
            onClick = {
                when (typeMovies) {
                    TypeMovies.Anime -> viewModel.loadCharactersAnime(id)
                    TypeMovies.Manga -> viewModel.loadCharactersManga(id)
                }
            },
            image = R.drawable.finish, modifier = Modifier.fillMaxSize()
        )
    }
}

