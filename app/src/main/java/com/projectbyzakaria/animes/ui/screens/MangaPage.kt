package com.projectbyzakaria.animes.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.projectbyzakaria.animes.R
import com.projectbyzakaria.animes.data.dao.top_anime_object.Data
import com.projectbyzakaria.animes.ui.component.CardMovie
import com.projectbyzakaria.animes.ui.component.ErrorShow
import com.projectbyzakaria.animes.ui.component.LoadingProgress
import com.projectbyzakaria.animes.ui.view_models.DataViewModel
import com.projectbyzakaria.animes.utilt.ResponseState
import com.projectbyzakaria.animes.utilt.TypeMovies


@Composable
fun MangaPage(
    onClickItem: (Int, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModelData: DataViewModel
) {
    LaunchedEffect(key1 = true ){
        viewModelData.loadTopManga()
    }
    val topManga = viewModelData.topManga.collectAsState()
    if (topManga.value is ResponseState.Loading){
        LoadingProgress(modifier = Modifier.fillMaxSize())
    }else if (topManga.value is ResponseState.Success){
        var count = when(LocalConfiguration.current.orientation) {
            1 -> 3
            else->5
        }
        val gridState = rememberLazyGridState()
        Column(
            modifier
        ) {
            val data = (topManga.value as ResponseState.Success).data

            LazyVerticalGrid(
                columns = GridCells.Fixed(count),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(3.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                state = gridState,
                modifier = Modifier.testTag("listOfManga")
            ) {
                itemsIndexed(data,key = { index: Int, item: Data -> item.mal_id ?: index }) { index,manga->
                    CardMovie(data = manga){
                        onClickItem(manga.mal_id ?: -1,TypeMovies.Manga.name)
                    }
                    if (!viewModelData.isStartLoadingManga  && data.size -1 == index &&viewModelData.internetIsAvailable){
                        viewModelData.showProgressBarManga {}
                    }
                }
                if (viewModelData.isLastVisibleItemForManga) {
                    item(span = { GridItemSpan(count) }) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                }
            }
        }
    }else{
        var error = (topManga.value as ResponseState.Error).message
        ErrorShow(error = error, onClick = {
            viewModelData.refresh(TypeMovies.Manga)
        }, modifier = Modifier.fillMaxSize())
    }

}
