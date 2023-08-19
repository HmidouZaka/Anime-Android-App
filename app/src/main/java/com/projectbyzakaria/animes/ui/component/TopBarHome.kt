package com.projectbyzakaria.animes.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projectbyzakaria.animes.ui.theme.blue
import com.projectbyzakaria.animes.ui.theme.pink
import com.projectbyzakaria.animes.ui.theme.white
import com.projectbyzakaria.animes.ui.view_models.StateUiViewModel
import com.projectbyzakaria.animes.utilt.TypeMovies
import kotlin.math.roundToInt

@Composable
fun TopBarHome(modifier:Modifier=Modifier,selectType:()->String,onChangeMovies:(type:TypeMovies)->Unit) {
    Row(
        modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 10.dp)
            .padding(top = 2.dp)
    ) {
        ItemType(TypeMovies.Anime,Modifier.weight(1f).testTag("topBarAnime"),{onChangeMovies(it)}) { selectType() == "Anime" }
        Spacer(modifier = Modifier.width(10.dp))
        ItemType(TypeMovies.Manga,Modifier.weight(1f).testTag("topBarManga"),{onChangeMovies(it)}) { selectType() == "Manga" }
    }
}
private val cutShape= CutCornerShape(10.dp)
@Composable
fun ItemType(type: TypeMovies,modifier: Modifier,onClick:(TypeMovies)->Unit, function: () -> Boolean) {
    val boeder = if (function() )0.dp else 1.5.dp
    val boedercolor = if (function() ) white else blue
    Box(
        modifier = modifier
            .clip(cutShape).clickable { onClick(type) }
            .border(width = boeder, boedercolor, cutShape)
            .drawBehind {
                drawRect(if (function()) blue else white)
            }
            .widthIn(min = 60.dp)
            .padding(horizontal = 10.dp, vertical = 10.dp), contentAlignment = Alignment.Center
    ) {
        Text(text = type.name, color = boedercolor, fontSize = 16.sp)
    }
}

