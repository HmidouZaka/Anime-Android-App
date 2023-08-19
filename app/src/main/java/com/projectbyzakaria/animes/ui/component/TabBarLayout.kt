package com.projectbyzakaria.animes.ui.component

import android.util.Log
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.projectbyzakaria.animes.ui.theme.black
import com.projectbyzakaria.animes.ui.theme.blue
import com.projectbyzakaria.animes.ui.theme.white
import com.projectbyzakaria.animes.utilt.ScreensStart
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabBarLayout(
    pagerState: PagerState,
    list: List<ScreensStart>,
    coroutine: CoroutineScope,
    modifier: Modifier = Modifier
) {
    ScrollableTabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = MaterialTheme.colors.background,
        indicator = { myPositions ->
            val transient = updateTransition(targetState = pagerState, label = "Tab indicator")
            val indicatorLeft by transient.animateDp(
                label = "Indicator left",
                transitionSpec = {
                    spring(
                        Spring.DampingRatioHighBouncy,
                        Spring.StiffnessMedium
                    )
                }) {
                myPositions[it.currentPage].left
            }
            val indicatorRight by transient.animateDp(
                label = "Indicator Right",
                transitionSpec = {
                    spring(
                        Spring.DampingRatioHighBouncy,
                        Spring.StiffnessMedium
                    )
                }) {
                myPositions[it.currentPage].right
            }
            TabBarLayoutIndecetore(indicatorLeft, indicatorRight)
        },
        modifier = modifier.fillMaxWidth()
    ) {
        list.forEachIndexed { index, screensStart ->
            TabBarLayoutItem(screensStart, index, pagerState) {
                coroutine.launch {
                    val go = async { pagerState.scrollToPage(index) }
                    go.await()
                    if (go.isCompleted) {
                        cancel()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabBarLayoutItem(
    item: ScreensStart,
    pagePosition: Int,
    pagerState: PagerState,
    onClick: () -> Unit
) {
    Tab(
        selected = pagerState.currentPage == pagePosition,
        onClick = onClick,
        selectedContentColor = white,
        unselectedContentColor = black
    ) {
        Box(Modifier.padding(20.dp), contentAlignment = Alignment.Center) {
            Text(
                item.title,
                color = MaterialTheme.colors.onBackground,
                fontWeight = FontWeight.Light,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun TabBarLayoutIndecetore(indicatorLeft: Dp, indicatorRight: Dp) {
    Box(
        Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.BottomStart)
            .offset(x = indicatorLeft)
            .width(indicatorRight - indicatorLeft)
            .padding(2.dp)
            .fillMaxSize()
            .border(2.dp, blue, CircleShape)
    )
}
