package com.projectbyzakaria.animes.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projectbyzakaria.animes.ui.theme.blue
import com.projectbyzakaria.animes.ui.theme.white
import com.projectbyzakaria.animes.ui.view_models.StateUiViewModel
import com.projectbyzakaria.animes.utilt.FilterContent


@Composable
fun BottomSheetComponent(
    modifier: Modifier = Modifier,
    viewModelUi: StateUiViewModel,
    onClickClick:(FilterContent)->Unit
) {
    val filter = rememberSaveable{
        mutableStateOf(FilterContent.Anime)
    }


    Column(
        modifier
            .fillMaxWidth()
            .wrapContentHeight()
    )
    {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ItemBottomSheetComponent(FilterContent.Anime,
                {filter.value == FilterContent.Anime }) {
                filter.value = it
            }
            ItemBottomSheetComponent(FilterContent.Manga,
                { filter.value == FilterContent.Manga }) {
                filter.value = it
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ItemBottomSheetComponent(FilterContent.Character,
                { filter.value== FilterContent.Character }) {
                filter.value = it
            }
            ItemBottomSheetComponent(FilterContent.Person,
                { filter.value == FilterContent.Person }) {
                filter.value = it
            }
        }


        Button(
            onClick = {
                viewModelUi.changeFilter(filter.value)
                onClickClick(viewModelUi.filter)
            },
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
                .padding(horizontal = 30.dp), shape = RoundedCornerShape(50.dp)
        ) {
            Text(
                text = "Search",
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
            )
        }


    }
}


@Composable
fun ItemBottomSheetComponent(
    type: FilterContent, isSelect: () -> Boolean,
    onSelect: (FilterContent) -> Unit,

    ) {
    val animateColor = animateColorAsState(targetValue = if (isSelect()) blue else white)
    Row(
        modifier = Modifier
            .wrapContentSize()
            .animateContentSize()
            .border(0.2.dp, color = blue, CircleShape)
            .background(animateColor.value, CircleShape)
            .clickable {
                onSelect(type)
            }
            .padding(vertical = 2.dp, horizontal = 8.dp)

    ) {
        if (isSelect()) {
            Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = white)
        }
        Text(text = type.name, fontSize = 22.sp, color = if (isSelect()) white else blue)
    }
}













