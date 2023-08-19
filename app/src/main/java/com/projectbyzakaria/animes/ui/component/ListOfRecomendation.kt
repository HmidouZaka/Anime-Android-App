package com.projectbyzakaria.animes.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.projectbyzakaria.animes.R
import com.projectbyzakaria.animes.data.dao.top_anime_object.*
import com.projectbyzakaria.animes.ui.view_models.AnimeDtelesViewModel
import com.projectbyzakaria.animes.utilt.ResponseState
import com.projectbyzakaria.animes.utilt.uniqueList

@Composable
fun ListOfRecommendation(
    viewModel:AnimeDtelesViewModel,
    onRefresh: () -> Unit,
    onClickItem: (Int) -> Unit,
) {
    val result = viewModel.recommendations.collectAsState()
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .fillMaxWidth()
    )
    {
        if (result.value is ResponseState.Loading) {
            LoadingProgress(modifier = Modifier)
        } else if (result.value is ResponseState.Success) {
            val data = (result.value as ResponseState.Success).data.data.uniqueList().map {
                Data(
                    Aired(null, Prop(null, null), null, null),
                    null,
                    null,
                    null,
                    Broadcast(null, null, null, null),
                    listOf(),
                    null,
                    null,
                    listOf(),
                    null,
                    listOf(),
                    Images(
                        Jpg(
                            it.entry.images.jpg.image_url,
                            it.entry.images.jpg.large_image_url,
                            it.entry.images.jpg.small_image_url
                        ),
                        Webp(
                            it.entry.images.webp.image_url,
                            it.entry.images.webp.large_image_url,
                            it.entry.images.webp.small_image_url
                        )
                    ),
                    listOf(),
                    it.entry.mal_id,
                    null,
                    null,
                    listOf(),
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    listOf(),
                    null,
                    listOf(),
                    it.entry.title,
                    null,
                    null,
                    listOf(),
                    listOf(),
                    Trailer(null, ImagesX(null, null, null, null, null), null, null),
                    null,
                    it.url, null
                )
            }

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                itemsIndexed(data, key = {index,it->it.mal_id?:index}) {index,it->
                    CardMovie(data =it) {
                        it.mal_id?.let { it1 -> onClickItem(it1) }
                    }
                }
            }


        } else if (result.value is ResponseState.Error) {
            val error = result.value as ResponseState.Error
            ErrorShow(
                error =error.message,
                isImageShown = false,
                onClick = onRefresh,
                modifier = Modifier.padding(30.dp),
                image = R.drawable.finish
            )
        }

    }
}