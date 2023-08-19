package com.projectbyzakaria.animes.ui.component

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.projectbyzakaria.animes.R
import com.projectbyzakaria.animes.data.dao.top_anime_object.Data
import com.projectbyzakaria.animes.ui.theme.black
import com.projectbyzakaria.animes.ui.theme.gray
import com.projectbyzakaria.animes.utilt.Constent

@Composable
fun HeaderMoreInfo(
    data: Data
) {
    val imageRequest = ImageRequest.Builder(LocalContext.current)
        .data(data.images.jpg.image_url)
        .crossfade(200)
        .error(R.drawable.error)
        .placeholder(R.drawable.loading)
        .build()
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = imageRequest,
            contentDescription = null,
            modifier = Modifier
                .height(220.dp)
                .width(150.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight()
        ) {
            Text(
                text = data.title ?: data.title_english ?: data.title_japanese ?: "Not Fount",
                modifier = Modifier.padding(horizontal = 5.dp, vertical = 8.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colors.onBackground
            )
            val map = Constent.getMapContent(data)
            map.forEach {
                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(it.key)
                        }
                        append(":  ")
                        append(it.value ?: "Not Found")
                    },
                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 0.5.dp),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colors.onBackground
                )
            }
        }
    }
}

@Composable
fun Shapes(listOfSheps: List<String>) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(listOfSheps) {
            Text(
                text = it,
                fontSize = 15.sp,
                color = black,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(gray)
                    .padding(vertical = 5.dp, horizontal = 10.dp),
                fontWeight = FontWeight.W500,
            )
        }
    }
}

@Preview
@Composable
fun ShapePreview() {
    Shapes(listOfSheps = listOf("Anime", "Drama", "Fantasy", "Manga", "Action"))
}
