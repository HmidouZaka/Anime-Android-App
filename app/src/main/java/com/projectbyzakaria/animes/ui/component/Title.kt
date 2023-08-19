package com.projectbyzakaria.animes.ui.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projectbyzakaria.animes.ui.theme.black
import com.projectbyzakaria.animes.ui.theme.gray
import com.projectbyzakaria.animes.ui.theme.white

@Composable
fun Title(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        color = MaterialTheme.colors.onBackground,
        fontFamily = MaterialTheme.typography.h1.fontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        maxLines = 2,
        textAlign = TextAlign.Start,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
    )
}

@Composable
fun TextOverView(text: String, modifier: Modifier = Modifier,fontSize:TextUnit=16.sp) {
    val showAllText = rememberSaveable {
        mutableStateOf(false)
    }
    Box(modifier = modifier
        .clickable(
            onClick = { showAllText.value = !showAllText.value },
        ).animateContentSize(tween(1000))
    ) {
        Text(
            text = text,
            color = MaterialTheme.colors.onBackground,
            fontFamily = MaterialTheme.typography.h4.fontFamily,
            fontWeight = FontWeight.W500,
            fontSize = fontSize,
            maxLines = if (!showAllText.value) 8 else Int.MAX_VALUE,
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxSize()
        )
    }

}
