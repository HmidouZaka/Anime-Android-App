package com.projectbyzakaria.animes.ui.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.projectbyzakaria.animes.R
import com.projectbyzakaria.animes.data.dao.characters_search.Data
import com.projectbyzakaria.animes.ui.theme.black
import com.projectbyzakaria.animes.ui.theme.white
import com.projectbyzakaria.animes.utilt.take

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
fun CharacterCard(
    data: Data,
    onClickItem:()->Unit
) {

    val characterImage: ImageRequest = ImageRequest.Builder(LocalContext.current)
        .data(data.images.jpg.image_url)
        .crossfade(true)
        .error(R.drawable.error)
        .placeholder(R.drawable.loading)
        .build()
    Card(
        elevation = 3.dp,
        backgroundColor = MaterialTheme.colors.background,
        shape = RoundedCornerShape(8.dp),
        onClick = onClickItem
    ) {
        Box(Modifier.height(250.dp)) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = characterImage,
                contentDescription = "null",
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.Unspecified,
                                Color.Unspecified,    black
                            )
                        )
                    )
            )
            data.favorites?.toString()?.let {
                Row(
                    Modifier
                        .padding(start = 5.dp, top = 5.dp)
                        .align(Alignment.TopEnd)
                ) {
                    Text(
                        text = it,
                        modifier = Modifier,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                    )
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = null, tint = white, modifier = Modifier.size(18.dp))
                }
            }

            data.name?.take()?.let {
                Text(
                    text = it ,
                    modifier = Modifier
                        .padding(start = 5.dp, top = 5.dp)
                        .align(Alignment.BottomStart),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = white
                )
            }

        }
    }


}