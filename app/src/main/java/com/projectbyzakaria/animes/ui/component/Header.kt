package com.projectbyzakaria.animes.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.projectbyzakaria.animes.model.HeaderData
import com.projectbyzakaria.animes.ui.theme.blue
import com.projectbyzakaria.animes.ui.theme.white

import com.projectbyzakaria.animes.R

private val height = 500.dp
@Composable
fun HeaderComponent(
    headerData: HeaderData,
    stateBottom: () -> Unit,
    getPosition: () -> Int
) {

    val color = Brush.verticalGradient(
        listOf(
            Color.Unspecified,
            Color.Unspecified,
            MaterialTheme.colors.background
        )
    )
    val imageRequest = ImageRequest.Builder(LocalContext.current)
        .data(headerData.image)
        .crossfade(900)
        .build()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(MaterialTheme.colors.background),
        contentAlignment = Alignment.BottomCenter
    ) {
        AsyncImage(
            model = imageRequest,
            contentDescription = "data",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.loading),
            error = painterResource(id = R.drawable.error)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color)
        )
        Column(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = headerData.title,
                color = white,
                fontFamily = MaterialTheme.typography.h1.fontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                maxLines = 2,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 5.dp)
                    .graphicsLayer {
                        translationX = -getPosition().toFloat()
                    }
            )
            Text(
                text = headerData.description,
                color = MaterialTheme.colors.onBackground,
                fontFamily = MaterialTheme.typography.h1.fontFamily,
                fontWeight = FontWeight.W500,
                fontSize = 16.sp,
                maxLines = 2,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .graphicsLayer {
                        translationX = getPosition().toFloat()
                    }
            )
            ExtendedFloatingActionButton(text = {
                Text(
                    text = "Start",
                    color = white,
                    fontFamily = MaterialTheme.typography.button.fontFamily,
                    fontWeight = FontWeight.Light,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
            }, onClick = { stateBottom() }, icon = {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "",
                    modifier = Modifier.size(24.dp),
                    tint = white
                )
            }, modifier = Modifier
                .padding(16.dp)
                .graphicsLayer {
                    translationX = -getPosition().toFloat()
                }, backgroundColor = blue)
        }

    }
}


