package com.projectbyzakaria.animes.ui.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import com.projectbyzakaria.animes.R
import com.projectbyzakaria.animes.data.dao.top_anime_object.Data
import com.projectbyzakaria.animes.model.HeaderData
import com.projectbyzakaria.animes.model.MovieLocal
import com.projectbyzakaria.animes.ui.activitys.AnimeDetailes
import com.projectbyzakaria.animes.ui.component.*
import com.projectbyzakaria.animes.ui.view_models.AnimeDtelesViewModel
import com.projectbyzakaria.animes.utilt.ResponseState
import com.projectbyzakaria.animes.utilt.TypeMovies
import com.projectbyzakaria.animes.utilt.toBitmap
import com.projectbyzakaria.animes.utilt.whereType
import kotlinx.coroutines.*


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetaileSvreen(
    id: Int,
    activity: AnimeDetailes,
    type: String,
    viewModel: AnimeDtelesViewModel,
    navHostController: NavHostController,
    coroutineScope: CoroutineScope,
    stateBottom: BottomSheetState,
) {
    val data = viewModel.data.collectAsState()
    if (data.value is ResponseState.Loading) {
        LoadingProgress(modifier = Modifier.fillMaxSize())
    } else if (data.value is ResponseState.Success) {
        val dataSuccess = (data.value as ResponseState.Success).data
        val stateScaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = stateBottom)
        val typeMovie = when (type) {
            TypeMovies.Manga.name -> TypeMovies.Manga
            else -> TypeMovies.Anime
        }
        BottomSheetScaffold(
            sheetContent = {
                StartScreen(
                    viewModel = viewModel,
                    id = id,
                    type = typeMovie,
                    navHostController = navHostController,
                    activity = activity
                )
            },
            scaffoldState = stateScaffoldState,
            sheetPeekHeight = 0.dp,
            sheetGesturesEnabled = false
        ) {
            ContentScreen(activity, type, dataSuccess, navHostController, viewModel, id) {
                coroutineScope.launch {
                    if (stateBottom.isCollapsed) {
                        stateBottom.expand()
                    } else {
                        stateBottom.collapse()
                    }
                }
                viewModel.getAllDataStart(id, type)
            }
        }

    } else {
        val error = (data.value as ResponseState.Error).message
        ErrorShow(error = error, onClick = {
            viewModel.refresh(id, type)
        }, modifier = Modifier.fillMaxSize(), image = R.drawable.finish)
    }

    LaunchedEffect(key1 = true) {
        if (id != -1) {
            viewModel.getAllData(id, type)
        }
    }

}

var job: Job? = null

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ContentScreen(
    activity: AnimeDetailes,
    type: String,
    towData: Data,
    navHostController: NavHostController,
    viewModel: AnimeDtelesViewModel,
    id: Int,
    stateBottom: () -> Unit,
) {
    val headerData = HeaderData(
        towData.images?.jpg?.large_image_url ?: "",
        towData.title ?: "No Title",
        towData.synopsis ?: towData.background ?: "",
        towData.url,
        false,
    )
    val stateScrolle = rememberScrollState(0)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(stateScrolle)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            HeaderComponent(
                headerData = headerData, stateBottom
            ) { stateScrolle.value }
            Title(
                "synopsis", modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .offset(y = (-20).dp)
                    .fillMaxWidth()
            )
            TextOverView(
                headerData.description,
                Modifier
                    .padding(horizontal = 8.dp)
                    .offset(y = (-20).dp)
                    .fillMaxWidth()
            )
            Title(
                "Images", modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth()
            )
            ViewPagerComponent(viewModel, id, activity, type)
            if (viewModel.recommendations.value is ResponseState.Success) {
                Title(
                    "Recommendation", modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth()
                )
            }
            ListOfRecommendation(viewModel, {
                when (type) {
                    TypeMovies.Manga.name -> viewModel.loadRecommendationManga(id)
                    TypeMovies.Anime.name -> viewModel.loadRecommendationAnime(id)
                }
            }
            ) {
                viewModel.isLoadData = false
                viewModel.initM()
                navHostController.navigate("DetailSuccend?idM=$it") {
                    navHostController.currentDestination?.route?.let { it1 ->
                        popUpTo(it1) {
                            this.inclusive = true
                        }
                    }
                }
            }
        }
        ToolBar({ activity.finish() }, {
            job?.let {
                if (it.isActive) {
                    it.cancel()
                }
            }
            job = MainScope().launch {
                delay(600)
                if (!viewModel.isIdInDatabase(id)) {
                    var imageRequest = async { headerData.image.toBitmap(activity) }
                    var movieLocal = MovieLocal(
                        image = imageRequest.await(),
                        type = type.whereType(),
                        id = 0,
                        mel_id = id,
                        name = headerData.title,
                        description = headerData.description
                    )
                    viewModel.insertMovies(movieLocal)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(activity, "Saved Successfully", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(activity, "The $type already saved", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            }
        },
            Modifier
                .fillMaxWidth()
                .padding(top = 30.dp)
                .padding(2.dp)
        )
    }

}

