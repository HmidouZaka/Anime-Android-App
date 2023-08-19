package com.projectbyzakaria.animes.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
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

@SuppressLint("UnrememberedMutableState")
@Composable
fun AnimePage(
    onClickItem: (Int, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModelData: DataViewModel
) {
    val topAnime = viewModelData.topAnime.collectAsState()
    if (topAnime.value is ResponseState.Loading) {
        LoadingProgress(modifier = Modifier.fillMaxSize())
    } else if (topAnime.value is ResponseState.Success) {
        val count = when (LocalConfiguration.current.orientation) {
            1 -> 3
            else -> 5
        }
        Column(
            modifier
        ) {
            val gridState = rememberLazyGridState()
            val data = (topAnime.value as ResponseState.Success).data
            LazyVerticalGrid(
                columns = GridCells.Fixed(count),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(3.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                state = gridState,
                modifier = Modifier.testTag("ListOfAnime")
            ) {
                Log.d("testsssssssssssssssssssssssssss", "loadMoreManga: AnimePage")
                itemsIndexed(data, key = { index: Int, item: Data -> item.mal_id ?: index }) { index, anime ->
                    CardMovie(data = anime){
                        onClickItem(anime.mal_id ?: -1,TypeMovies.Anime.name)
                    }
                    if (!viewModelData.isStartLoadingAnime && viewModelData.internetIsAvailable  && data.size -1 == index){
                        viewModelData.showProgressBarAnime {}
                    }
                }
                if (viewModelData.isLastVisibleItemForAnime) {
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
    } else {
        val error = (topAnime.value as ResponseState.Error).message
        ErrorShow(error = error, onClick = {
            viewModelData.refresh(TypeMovies.Anime)
        }, modifier = Modifier.fillMaxSize())
    }

}