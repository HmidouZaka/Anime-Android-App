package com.projectbyzakaria.animes.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.projectbyzakaria.animes.R
import com.projectbyzakaria.animes.data.dao.top_anime_object.Data
import com.projectbyzakaria.animes.ui.previews.PreviewForAllDevices
import com.projectbyzakaria.animes.ui.theme.fontFamily
import com.projectbyzakaria.animes.utilt.getOrNull
import com.projectbyzakaria.animes.utilt.take

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardMovie(data: Data,modifier: Modifier = Modifier,onClick:()->Unit) {
    val imageRequest = ImageRequest.Builder(LocalContext.current)
    .data(data.images.jpg.image_url)
        .crossfade(200)
        .build()
    Card(
        modifier = modifier
            .width(140.dp).height( 230.dp), elevation = 2.dp,
        shape = RoundedCornerShape(5.dp),
        backgroundColor = MaterialTheme.colors.background,
        onClick = onClick
    ) {
        Column {
            AsyncImage(model =imageRequest,
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.size(140.dp, 200.dp),
                placeholder = painterResource(id = R.drawable.loading),
                error = painterResource(id = R.drawable.error)
            )

            (data.title ?: data.titles.getOrNull(0)?.title ?: data.title_english ?: data.title_japanese)?.let {title->
                Text(
                    text = title.take(),
                    style = MaterialTheme.typography.h2.copy(fontSize = 12.sp),
                    modifier = Modifier.padding(horizontal = 2.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colors.onBackground,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold
                )
            }

            data.background ?: data.status ?: data.type?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.subtitle2.copy(fontSize = 10.sp),
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                        .offset { IntOffset(0, -5) },
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colors.onBackground,
                    fontFamily = MaterialTheme.typography.subtitle1.fontFamily,
                    fontWeight = FontWeight.Light
                )
            }
            data.score?.toString()?.let {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.star),
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .size(15.dp, 10.dp)
                            .padding(start = 2.dp)
                            .offset(y = (-1).dp)
                    )
                    Text(
                        text =it ,
                        modifier = Modifier.padding(horizontal = 2.dp).offset(y = (-1).dp),
                        fontSize = 9.sp,
                        maxLines = 1,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Medium
                    )

                }
            }

        }

    }
}

