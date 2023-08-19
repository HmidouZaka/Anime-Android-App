package com.projectbyzakaria.animes.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.projectbyzakaria.animes.model.MovieLocal
import com.projectbyzakaria.animes.ui.component.ItemMovieSave
import com.projectbyzakaria.animes.ui.component.LoadingProgress
import com.projectbyzakaria.animes.ui.theme.lightPink
import com.projectbyzakaria.animes.ui.view_models.DataViewModel
import com.projectbyzakaria.animes.utilt.ResponseState
import com.projectbyzakaria.animes.utilt.TypeMovies
import kotlin.math.log

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoriteScreen(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: DataViewModel,
    onClick: (Int, String) -> Unit
) {
    var context = LocalContext.current
    var data = viewModel.localMovies.observeAsState(ResponseState.Loading())
    if (data.value is ResponseState.Loading){
        LoadingProgress(modifier = Modifier.fillMaxSize())
    }else if (data.value is ResponseState.Success){
        var targetList = (data.value as ResponseState.Success).data
        var listOfManga = targetList.filter { it.type == TypeMovies.Manga }
        var listOfAnime = targetList.filter { it.type == TypeMovies.Anime }

        LazyColumn(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start,
            contentPadding = PaddingValues(bottom = 58.dp, start = 8.dp, top = 8.dp, end = 8.dp)
        ) {

            if (listOfAnime.isNotEmpty()) {
                stickyHeader {
                    Text(
                        text = "Anime",
                        modifier = Modifier.padding(8.dp), color = lightPink)
                }
                items(listOfAnime, key = {it.id}) {
                    ItemMovieSave(local = it, onClickItem = {
                        if (viewModel.internetIsAvailable) {
                            onClick(it.mel_id, it.type.name)
                        } else {
                            Toast.makeText(context, "Net Work Not Available", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }){viewModel.deleteMovie(it)}
                }
            }
            if (listOfManga.isNotEmpty()) {
                stickyHeader { Text(text = "Manga", modifier = Modifier.padding(8.dp), color = lightPink) }
                items(listOfManga, key = {it.id}) {
                    ItemMovieSave(local = it,onClickItem= {
                        if (viewModel.internetIsAvailable) {
                            onClick(it.mel_id, it.type.name)
                        } else {
                            Toast.makeText(context, "Net Work Not Available", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }){viewModel.deleteMovie(it)}
                }
            }
        }
    }else{
        Box(modifier, contentAlignment = Alignment.Center) {
            Text(text = "No Movies Found")
        }
    }


}