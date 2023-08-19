package com.projectbyzakaria.animes.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.projectbyzakaria.animes.R
import com.projectbyzakaria.animes.ui.theme.*
import com.projectbyzakaria.animes.ui.view_models.StateUiViewModel
import com.projectbyzakaria.animes.utilt.Screens
import kotlin.math.roundToInt

@Composable
fun NavigationBottomBar(
    modifier: Modifier = Modifier,
    viewModel: StateUiViewModel,
    onClickItem: (Screens) -> Unit
) {
    BottomNavigation(
        modifier = modifier.offset {IntOffset(x=0,-viewModel.positionScrolling.roundToInt())},
        backgroundColor = MaterialTheme.colors.background,
        elevation = 3.dp
    ) {
        val selectItemType = viewModel.currentPage
        BottomNavigationItem(
            selected = selectItemType == Screens.HOME.name,
            onClick = { onClickItem(Screens.HOME);viewModel.gotoPage( Screens.HOME.name) },
            {
                ItemContent(
                    R.drawable.home,
                    "home",
                    "home",
                    { if (selectItemType == Screens.HOME.name) lightPink else gray },
                    {
                        AnimatedVisibility(visible = selectItemType.uppercase() == it.uppercase()) {
                            Text(
                                text = it,
                                color = pink,
                                fontSize = 12.sp,
                                fontFamily = MaterialTheme.typography.h3.fontFamily
                            )
                        }
                    })
            }, modifier = Modifier.testTag("home")
        )
        BottomNavigationItem(
            selected = selectItemType == Screens.SEARCH.name,
            onClick = { onClickItem(Screens.SEARCH);viewModel.gotoPage( Screens.SEARCH.name) },
            {
                ItemContent(
                    R.drawable.search,
                    "Search",
                    "search",
                    { if (selectItemType == Screens.SEARCH.name) lightPink else gray },
                    {
                        AnimatedVisibility(visible = selectItemType.uppercase() == it.uppercase()) {
                            Text(
                                text = it,
                                color = pink,
                                fontSize = 12.sp,
                                fontFamily = MaterialTheme.typography.h3.fontFamily
                            )
                        }
                    })
            }, modifier = Modifier.testTag("search")
        )
        BottomNavigationItem(
            selected = selectItemType == Screens.FAVORITE.name,
            onClick = {
                onClickItem(Screens.FAVORITE);viewModel.gotoPage( Screens.FAVORITE.name)
            },
            {
                ItemContent(
                    R.drawable.favorite_save,
                    "Favorite",
                    "favorite",
                    { if (selectItemType == Screens.FAVORITE.name) lightPink else gray },
                    {
                        AnimatedVisibility(visible = selectItemType.uppercase() == it.uppercase()) {
                            Text(
                                text = it,
                                color = pink,
                                fontSize = 12.sp,
                                fontFamily = MaterialTheme.typography.h3.fontFamily
                            )
                        }
                    })
            }, modifier = Modifier.testTag("favorite")
        )
        BottomNavigationItem(
            selected = selectItemType == Screens.PROFILE.name,
            onClick = { onClickItem(Screens.PROFILE);viewModel.gotoPage(Screens.PROFILE.name)},
            {
                ItemContent(
                    R.drawable.profile,
                    "Profile",
                    "profile",
                    { if (selectItemType == Screens.PROFILE.name) lightPink else gray },
                    {
                        AnimatedVisibility(visible = selectItemType.uppercase() == it.uppercase()) {
                            Text(
                                text = it,
                                color = pink,
                                fontSize = 12.sp,
                                fontFamily = MaterialTheme.typography.h3.fontFamily
                            )
                        }
                    })
            }, modifier = Modifier.testTag("profile")
        )
    }
}


@Composable
fun ItemContent(
    icon: Int, sectionName: String, contentD: String? = null, color: () -> Color,
    contentText: @Composable (sectionName: String) -> Unit
) {
    Column(
        modifier = Modifier.padding(2.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .drawBehind { drawRect(color()) }
                .padding(horizontal = 16.dp, vertical = 3.dp),
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = contentD,
                tint = if (color() == gray) black else white
            )
        }
        contentText(sectionName)
    }
}

