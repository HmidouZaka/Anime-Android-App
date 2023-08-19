package com.projectbyzakaria.animes.ui.screens

import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.viewpager2.widget.ViewPager2
import com.projectbyzakaria.animes.R
import com.projectbyzakaria.animes.data.dao.top_anime_object.*
import com.projectbyzakaria.animes.repositorys.ViewModelMplm
import com.projectbyzakaria.animes.ui.adapters.ImagesAdapter
import com.projectbyzakaria.animes.ui.component.CardMovie
import com.projectbyzakaria.animes.ui.component.ErrorShow
import com.projectbyzakaria.animes.ui.component.LoadingProgress
import com.projectbyzakaria.animes.ui.theme.black
import com.projectbyzakaria.animes.ui.theme.white
import com.projectbyzakaria.animes.utilt.*
import com.projectbyzakaria.views.ui.activitys.ImagesActivity
import com.projectbyzakaria.views.utlis.Constent

@Composable
fun CharacterScreen(
    id: Int?,
    navHostController: NavHostController,
    dataViewModel: ViewModelMplm,
    modifier: Modifier = Modifier,
    activity: ComponentActivity,
    kFunction2: (Int, String) -> Unit
) {
    IsIdNull(id) {
        val info = dataViewModel.charactersInfo.collectAsState()
        if (info.value is ResponseState.Loading) {
            LoadingProgress(modifier = Modifier.fillMaxSize())
        } else if (info.value is ResponseState.Success) {
            val data = (info.value as ResponseState.Success).data.data

            Content(data, dataViewModel, it,navHostController,activity,kFunction2)
            Icon(painter = painterResource(id = R.drawable.back_icon), contentDescription = null,
                Modifier
                    .padding(top = 35.dp, start = 15.dp)
                    .size(29.dp)

                    .clickable {
                        navHostController.popBackStack()
                    }, tint = white)

        } else {
            val error = (info.value as ResponseState.Error).message
            ErrorShow(error = error, onClick = {
                dataViewModel.getCharacterInfo(it)
            }, modifier = Modifier.fillMaxSize(), image = R.drawable.finish)
        }
        if (!dataViewModel.isCallApi) {
            LaunchedEffect(Unit) {
                Log.d("CharacterScreen", "CharacterScreen: SSSSSSSSSSSSSSSSSSSSS")
                dataViewModel.getCharacterInfo(it)
                dataViewModel.getImagesCharacter(it)
                dataViewModel.isCallApi = true
            }
        }
    }

}

@Composable
fun IsIdNull(id: Int?, content: @Composable (Int) -> Unit) {
    if (id == null) {
        Text(text = "Not Found", modifier = Modifier.fillMaxSize(), textAlign = TextAlign.Center)
    } else {
        content(id)
    }
}

@Composable
fun ImageComponent(image: String, viewModel: ViewModelMplm, id: Int, nikeName: String?,activity: ComponentActivity) {
    val info = viewModel.imagesCharacters.collectAsState()
    if (info.value is ResponseState.Loading) {
        LoadingProgress(
            modifier = Modifier
                .fillMaxSize()
                .heightIn(min = 350.dp)
        )
    } else if (info.value is ResponseState.Success) {
        val data =
            (info.value as ResponseState.Success).data.map { it.jpg.image_url ?: "" }.uniqueList().toMutableList()
        data.add(0, image)
        ImageHeader(data , nikeName,activity)
    } else {
        val error = (info.value as ResponseState.Error).message
        ErrorShow(error = error, onClick = {
            viewModel.getImagesCharacter(id)
        }, modifier = Modifier.fillMaxSize().padding(20.dp), isImageShown = false)
    }
}


@Composable
fun Content(
    data: com.projectbyzakaria.animes.data.dao.relotion.character_info.Data,
    viewModel: ViewModelMplm,
    id: Int,
    navHostController: NavHostController,activity: ComponentActivity,
    kFunction2: (Int, String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        ImageComponent(
            data.images.jpg.image_url ?: data.images.webp.small_image_url
            ?: data.images.webp.image_url ?: "", viewModel, id, data.name_kanji,activity
        )
        val listOfAnime = data.anime.uniqueList().map {
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
                        it.anime.images.jpg.image_url,
                        it.anime.images.jpg.large_image_url,
                        it.anime.images.jpg.small_image_url,
                    ),
                    Webp(
                        it.anime.images.webp.image_url,
                        it.anime.images.webp.large_image_url,
                        it.anime.images.webp.small_image_url,
                    )
                ),
                listOf(),
                it.anime.mal_id,
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
                it.anime.title,
                null,
                null,
                listOf(),
                listOf(),
                Trailer(null, ImagesX(null, null, null, null, null), null, null),
                null,
                it.anime.url, null
            )
        }
        val listOfManga = data.manga.uniqueList().map {
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
                        it.manga.images.jpg.image_url,
                        it.manga.images.jpg.large_image_url,
                        it.manga.images.jpg.small_image_url,
                    ),
                    Webp(
                        it.manga.images.webp.image_url,
                        null,
                        it.manga.images.webp.small_image_url,
                    )
                ),
                listOf(),
                it.manga.mal_id,
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
                it.manga.title,
                null,
                null,
                listOf(),
                listOf(),
                Trailer(null, ImagesX(null, null, null, null, null), null, null),
                null,
                it.manga.url, null
            )
        }
        val listOfVoices = data.voices.uniqueList().map {
            Data(
                Aired(null, Prop(null, null), null, null),
                null,
                null,
                it.language,
                Broadcast(null, null, null, null),
                listOf(),
                null,
                null,
                listOf(),
                null,
                listOf(),
                Images(
                    Jpg(
                        it.person.images.jpg.image_url,
                        null,
                        null
                    ),
                    Webp(
                        null,
                        null,
                        null,
                    )
                ),
                listOf(),
                it.person.mal_id,
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
                it.language,
                listOf(),
                it.person.name,
                null,
                null,
                listOf(),
                listOf(),
                Trailer(null, ImagesX(null, null, null, null, null), null, null),
                null,
                it.person.url, null
            )
        }
        Box(modifier = Modifier.fillMaxWidth()) {
            data.name?.let {
                Column(
                    modifier = Modifier.align(Alignment.CenterStart),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Name Character",
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .padding(top = 8.dp),
                        fontSize = 20.sp,
                        style = MaterialTheme.typography.h2.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = data.name.take(15),
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                        fontSize = 18.sp,
                        style = MaterialTheme.typography.h2.copy(fontWeight = FontWeight.Medium),
                        maxLines = 1,
                    )
                }
            }
            data.favorites?.let {
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = null, tint = MaterialTheme.colors.onBackground, modifier = Modifier.padding(8.dp))
                    Text(
                        text = it.toString(),
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 3.dp),
                        fontSize = 14.sp,
                        style = MaterialTheme.typography.h2.copy(fontWeight = FontWeight.Medium)
                    )
                }

            }
        }

        data.about?.let {
            Text(
                text = "About",
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(top = 8.dp),
                fontSize = 20.sp,
                style = MaterialTheme.typography.h2.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = it,
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                fontSize = 18.sp,
                style = MaterialTheme.typography.h2.copy(fontWeight = FontWeight.Medium)
            )
        }
        if (listOfAnime.isNotEmpty()) {
            Text(
                text = "Anime",
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(top = 8.dp),
                fontSize = 20.sp,
                style = MaterialTheme.typography.h2.copy(fontWeight = FontWeight.Bold)
            )
            LazyRow(
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                itemsIndexed(listOfAnime, key = {i,it->it.mal_id?:i}) {index,it->
                    CardMovie(data = it) {
                        kFunction2(it.mal_id ?: -1,TypeMovies.Anime.name)
                    }
                }
            }
        }
        if (listOfManga.isNotEmpty()) {
            Text(
                text = "Manga",
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(top = 8.dp),
                fontSize = 20.sp,
                style = MaterialTheme.typography.h2.copy(fontWeight = FontWeight.Bold)
            )
            LazyRow(
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                itemsIndexed(listOfManga, key = {i,it->it.mal_id ?: i}) {index,it->
                    CardMovie(data = it) {
                        kFunction2(it.mal_id ?: -1,TypeMovies.Manga.name)
                    }
                }
            }
        }
        if (listOfVoices.isNotEmpty()) {
            Text(
                text = "Voices",
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(top = 8.dp),
                fontSize = 20.sp,
                style = MaterialTheme.typography.h2.copy(fontWeight = FontWeight.Bold)
            )
            LazyRow(
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                itemsIndexed(listOfVoices, key = {i,it->it.mal_id?:i}) {index,it->
                    CardMovie(data = it) {
                        viewModel.isCallApiPeople = false
                        navHostController.navigate(Screens.People.name + "?idPeople=${it.mal_id}")
                    }
                }
            }
        }

    }

}

@Composable
fun ImageHeader(list: List<String>, nakeName: String? = null,activity: ComponentActivity) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(450.dp)
    ) {
        AndroidView(
            factory = { ViewPager2(it) }, modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(bottomEnd = 50.dp, bottomStart = 50.dp))
        )
        {
            val adapter = ImagesAdapter(list.uniqueList()){position->
                var array = ArrayList<String>()
                list.forEach {
                    array.add(it)
                }
                var intentImages = Intent(activity, ImagesActivity::class.java)
                intentImages.putStringArrayListExtra(Constent.KEY_LIST_IMAGES, array)
                intentImages.putExtra(Constent.POSITION,position)
                activity.startActivity(intentImages)
            }
            it.adapter = adapter
        }
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(bottomEnd = 50.dp, bottomStart = 50.dp))
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Unspecified,
                            Color.Unspecified,
                            black
                        )
                    )
                )
        )
    }
}







