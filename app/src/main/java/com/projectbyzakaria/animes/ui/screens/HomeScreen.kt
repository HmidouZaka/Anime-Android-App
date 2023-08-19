@file:OptIn(ExperimentalAnimationApi::class, ExperimentalAnimationApi::class)

package com.projectbyzakaria.animes.ui.screens

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.IntOffset
import com.projectbyzakaria.animes.ui.component.TopBarHome
import com.projectbyzakaria.animes.ui.view_models.DataViewModel
import com.projectbyzakaria.animes.ui.view_models.StateUiViewModel
import kotlin.math.roundToInt

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: StateUiViewModel,
    viewModelData: DataViewModel,
    onClickItem:(Int,String)->Unit
) {
    val scrolling = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                viewModel.changePositionScroll(available.y)
                return super.onPreScroll(available, source)
            }
        }
    }
    val modifierForContent = Modifier.fillMaxSize()
    val selectType = viewModel.typeMovies
    Scaffold(modifier = modifier.nestedScroll(scrolling)) {
        AnimatedContent(targetState = selectType == "Anime", transitionSpec = {
            fadeIn() with fadeOut()
        }) {
            if (it) {
                AnimePage(
                    onClickItem,
                    modifierForContent,
                    viewModelData,
                )
            } else {
                MangaPage(
                    onClickItem,
                    modifierForContent,
                    viewModelData
                )
            }
        }
        TopBarHome(
            Modifier
                .fillMaxWidth()
                .offset { IntOffset(x = 0, viewModel.positionScrolling.roundToInt()) },
            { selectType }) {
            viewModel.goToAntherScreen(it)
        }


    }

}





