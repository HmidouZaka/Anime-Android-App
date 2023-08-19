package com.projectbyzakaria.animes.ui.screens

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.projectbyzakaria.animes.ui.view_models.AnimeDtelesViewModel
import com.projectbyzakaria.animes.R
import com.projectbyzakaria.animes.ui.activitys.AnimeDetailes
import com.projectbyzakaria.animes.ui.component.*
import com.projectbyzakaria.animes.utilt.ResponseState
import com.projectbyzakaria.animes.utilt.TypeMovies
import com.projectbyzakaria.animes.utilt.uniqueList
import com.projectbyzakaria.views.utlis.Constent.KEY_ID_VIDEO
import com.projectbyzakaria.views.ui.activitys.TrailerActivity

@Composable
fun MoreInfoScreen(
    viewModel: AnimeDtelesViewModel,
    type: String,
    id: Int,
    context: Context,
    modifier: Modifier = Modifier,
    activity :AnimeDetailes
) {
    val data = viewModel.data.collectAsState()
    if (data.value is ResponseState.Success) {
        val scrooleState = rememberScrollState()
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .verticalScroll(scrooleState)
        ) {
            val resultData = (data.value as ResponseState.Success)
            HeaderMoreInfo(data = resultData.data)
            val geners = resultData.data.genres.map { it.name }.filterNotNull().uniqueList()
            Shapes(geners)
            (resultData.data.synopsis ?: resultData.data.background)?.let {
                Text(
                    text = "OverView",
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                TextOverView(it, Modifier.padding(horizontal = 8.dp))
                ImagesView(viewModel, type, id,activity)
            }
            resultData.data.trailer?.youtube_id?.let {
                TrilerVideo(
                    image = resultData.data.trailer.images.image_url
                        ?: resultData.data.trailer.images.large_image_url
                        ?: resultData.data.trailer.images.medium_image_url ?: ""
                ) {
                    val intent = Intent(context, TrailerActivity::class.java)
                    intent.putExtra(KEY_ID_VIDEO, it)
                    context.startActivity(intent)
                }
            }
        }
    } else if (data.value is ResponseState.Error) {
        val error = (data.value as ResponseState.Error).message
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentAlignment = Alignment.Center
        ) {
            ErrorShow(
                error = error,
                onClick = {
                    if (type == TypeMovies.Anime.name) {
                        viewModel.loadImagesAnime(id)
                    } else {
                        viewModel.loadImagesManga(id)
                    }
                }, modifier = Modifier
            )
        }

    } else if (data.value is ResponseState.Loading) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentAlignment = Alignment.Center
        ) {
            LoadingProgress(modifier = Modifier.padding(50.dp))
        }
    }
}

@Composable
fun ImagesView(viewModel: AnimeDtelesViewModel, type: String, id: Int,activity: AnimeDetailes) {
    val images = viewModel.images.collectAsState()
    if (images.value is ResponseState.Success) {
        val resultImages = (images.value as ResponseState.Success)
        val listOfImages = resultImages.data.data?.map {
            it.jpg?.image_url ?: it.jpg?.large_image_url ?: it.jpg?.small_image_url ?: ""
        }?.uniqueList()
        listOfImages?.let {
            if (it.isNotEmpty()) {
                Text(
                    text = "Images",
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                ViewPagerComposeImages(list = it){
                    var list = ArrayList<String>()
                    listOfImages.forEach {
                        list.add(it)
                    }
                    activity.showImages(list,it)
                }
            }
        }
    } else if (images.value is ResponseState.Error) {
        var error = (images.value as ResponseState.Error).message
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentAlignment = Alignment.Center
        ) {
            ErrorShow(
                error = error,
                onClick = {
                    if (type == TypeMovies.Anime.name) {
                        viewModel.loadImagesAnime(id)
                    } else {
                        viewModel.loadImagesManga(id)
                    }
                }, modifier = Modifier
            )
        }

    } else if (images.value is ResponseState.Loading) {
        LoadingProgress(modifier = Modifier.padding(50.dp))
    }

}

@Composable
fun TrilerVideo(image: String, onClickStart: () -> Unit) {
    Box(
        Modifier
            .padding(8.dp)
            .padding(bottom = 20.dp)
            .fillMaxWidth()
            .height(250.dp)
            .clickable { onClickStart() }, contentAlignment = Alignment.Center
    ) {
        val imageRequest = ImageRequest.Builder(LocalContext.current)
            .data(image)
            .crossfade(200)
            .error(R.drawable.error)
            .placeholder(R.drawable.loading)
            .build()
        AsyncImage(
            model = imageRequest,
            contentDescription = null,
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(8.dp))
                .clickable { onClickStart() },
            contentScale = ContentScale.Crop
        )
        Spacer(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Black, Color.Unspecified,
                            Color.Unspecified
                        )
                    )
                )
                .clickable { onClickStart() }
        )
        Image(
            painter = painterResource(id = R.drawable.youtube),
            contentDescription = "Show Triler",
            modifier = Modifier
                .width(70.dp)
                .height(50.dp)
                .clickable { onClickStart() })
    }
}

