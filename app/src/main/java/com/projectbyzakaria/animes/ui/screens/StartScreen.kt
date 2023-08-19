package com.projectbyzakaria.animes.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.*
import com.projectbyzakaria.animes.ui.activitys.AnimeDetailes
import com.projectbyzakaria.animes.ui.component.TabBarLayout
import com.projectbyzakaria.animes.ui.view_models.AnimeDtelesViewModel
import com.projectbyzakaria.animes.utilt.ScreensStart
import com.projectbyzakaria.animes.utilt.TypeMovies

@OptIn(ExperimentalPagerApi::class)
@Composable
fun StartScreen(
    viewModel: AnimeDtelesViewModel,
    modifier: Modifier = Modifier,
    id:Int,
    type: TypeMovies = TypeMovies.Anime,
    navHostController: NavHostController,
    activity:AnimeDetailes
) {
    val context = LocalContext.current
    val list = when (type) {
        TypeMovies.Manga -> listOf(
            ScreensStart.MainScreen(viewModel,id,type,context,activity),
            ScreensStart.CharacterScreen(viewModel,id,type,navHostController),
            ScreensStart.ReviewsScreens(viewModel,id,type)
        )
        else -> listOf(
            ScreensStart.MainScreen(viewModel, id, type,context,activity),
            ScreensStart.CharacterScreen(viewModel,id,type,navHostController),
            ScreensStart.ReviewsScreens(viewModel,id,type),
            ScreensStart.EpsidesScreen(viewModel, id,navHostController){u,t->
                activity.gotoWebPage(u,t)
            },
            ScreensStart.StaffScreen(viewModel,id,navHostController),
        )
    }
    val pagerState = rememberPagerState()
    val coroutine = rememberCoroutineScope()
    Column(
        modifier
            .padding(top = 30.dp)
            .fillMaxSize()) {
        TabBarLayout(
            pagerState,list,coroutine,Modifier.height(70.dp)
        )
        HorizontalPager(count = list.size, state = pagerState, key = {page: Int -> page }) {
           list[it].content()
        }

    }
}