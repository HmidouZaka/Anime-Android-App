package com.projectbyzakaria.animes.ui.component

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.viewpager2.widget.ViewPager2
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.projectbyzakaria.animes.R
import com.projectbyzakaria.animes.ui.activitys.AnimeDetailes
import com.projectbyzakaria.animes.ui.adapters.ImagesAdapter
import com.projectbyzakaria.animes.ui.view_models.AnimeDtelesViewModel
import com.projectbyzakaria.animes.utilt.ResponseState
import com.projectbyzakaria.animes.utilt.TypeMovies
import com.projectbyzakaria.animes.utilt.uniqueList

@Composable
fun ViewPagerComponent(viewModel: AnimeDtelesViewModel, id: Int,activity:AnimeDetailes, type: String = "Anime") {
    val images = viewModel.images.collectAsState()
    Log.d("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh", "ViewPagerComponent: $images")
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        if (images.value is ResponseState.Loading) {
            LoadingProgress(modifier = Modifier)
        } else if (images.value is ResponseState.Success) {
            val data =
                (images.value as ResponseState.Success).data.data?.map { it?.jpg?.large_image_url }?.uniqueList()
            AndroidView(
                factory = {
                    ViewPager2(it)
                }, modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                it.adapter = data?.let { it1 ->
                    ImagesAdapter(it1){
                        var list  = ArrayList<String>()
                        data.filterNotNull().forEach {
                            list.add(it)
                        }
                        activity.showImages(list,it)
                    }
                }
            }
        } else if (images.value is ResponseState.Error) {
            val error = images.value as ResponseState.Error
            ErrorShow(error = error.message, isImageShown = false, onClick = {
                when (type) {
                    TypeMovies.Manga.name -> viewModel.loadImagesManga(id)
                    TypeMovies.Anime.name -> viewModel.loadImagesAnime(id)
                }

            }, modifier = Modifier.padding(30.dp), image = R.drawable.finish)
        }

    }
}


@Composable
fun ViewPagerComposeImages(
    list: List<String>,
    modifier: Modifier = Modifier,
    spaceBetween: Dp = 8.dp,
    onClick:(Int)->Unit ={}
) {

    LazyRow(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(spaceBetween), horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        itemsIndexed(list,key = {i,it->it}){index,content->
            val imageRequest = ImageRequest.Builder(LocalContext.current)
                .data(content)
                .crossfade(200)
                .error(R.drawable.error)
                .placeholder(R.drawable.loading)
                .build()
            AsyncImage(
                model = imageRequest,
                contentDescription = null,
                modifier = Modifier
                    .height(220.dp)
                    .width(150.dp)
                    .clip(RoundedCornerShape(8.dp)).clickable { onClick(index) },
                contentScale = ContentScale.Crop
            )
        }

    }
}