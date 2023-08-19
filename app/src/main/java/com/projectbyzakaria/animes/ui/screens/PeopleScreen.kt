package com.projectbyzakaria.animes.ui.screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.projectbyzakaria.animes.R
import com.projectbyzakaria.animes.data.dao.top_anime_object.*
import com.projectbyzakaria.animes.repositorys.ViewModelMplm
import com.projectbyzakaria.animes.ui.component.CardMovie
import com.projectbyzakaria.animes.ui.component.ErrorShow
import com.projectbyzakaria.animes.ui.component.LoadingProgress
import com.projectbyzakaria.animes.ui.theme.white
import com.projectbyzakaria.animes.utilt.*

@Composable
fun PeopleScreen(
    id: Int?,
    navHostController: NavHostController,
    dataViewModel: ViewModelMplm,
    modifier: Modifier = Modifier,
    activity:ComponentActivity,
    kFunction2: (Int, String) -> Unit
)
{
    IsIdNull(id = id) {
        val info = dataViewModel.infoPeople.collectAsState()
        if (info.value is ResponseState.Loading) {
            LoadingProgress(modifier = Modifier.fillMaxSize())
        } else if (info.value is ResponseState.Success) {
            val data = (info.value as ResponseState.Success).data.data
            PeopleContent(data = data, viewModel =dataViewModel , id =it ,navHostController,activity,kFunction2)

            Icon(painter = painterResource(id = R.drawable.back_icon), contentDescription = null,
                Modifier
                    .padding(10.dp)
                    .size(29.dp)
                    .clickable {
                        navHostController.popBackStack()
                    }, tint = white
            )

        } else {
            val error = (info.value as ResponseState.Error).message
            ErrorShow(error = error, onClick = {
                dataViewModel.getPeopleInfo(it)
                dataViewModel.getImagesPeople(it)
            }, modifier = Modifier.fillMaxSize(), image = R.drawable.finish)
        }
        if (!dataViewModel.isCallApiPeople) {
            LaunchedEffect(Unit) {
                dataViewModel.getPeopleInfo(it)
                dataViewModel.getImagesPeople(it)
                dataViewModel.isCallApiPeople = true
            }
        }
    }
}
@Composable
fun ImageComponentPeople(image: String, viewModel: ViewModelMplm, id: Int, nikeName: String?,activity:ComponentActivity) {
    val info = viewModel.imagesPeople.collectAsState()
    if (info.value is ResponseState.Loading) {
        LoadingProgress(
            modifier = Modifier
                .fillMaxSize()
                .heightIn(min = 350.dp)
        )
    } else if (info.value is ResponseState.Success) {
        val data =
            (info.value as ResponseState.Success).data.map { it.jpg.image_url ?: "" }.toMutableList()
        data.add(0, image)
        ImageHeader(data , nikeName,activity)
    } else {
        val error = (info.value as ResponseState.Error).message
        ErrorShow(error = error, onClick = {
            viewModel.getImagesPeople(id)
        }, modifier = Modifier.fillMaxSize().heightIn(min = 350.dp), isImageShown = false)
    }
}
@Composable
fun PeopleContent(
    data: com.projectbyzakaria.animes.data.dao.relotion.peopel_all_info.Data,
    viewModel: ViewModelMplm,
    id: Int,
    navHostController: NavHostController,activity:ComponentActivity,
    kFunction2: (Int, String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        ImageComponentPeople(
            data.images.jpg.image_url ?: "",viewModel,id,data.name,activity
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
                        it.character.images.jpg.image_url,
                        null,
                        null,
                    ),
                    Webp(
                        it.character.images.webp.image_url,
                        null,
                        null,
                    )
                ),
                listOf(),
                it.character.mal_id,
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
                it.character.name,
                null,
                null,
                listOf(),
                listOf(),
                Trailer(null, ImagesX(null, null, null, null, null), null, null),
                null,
                null, null
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
                        text = "Name Person",
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
                itemsIndexed(listOfAnime, key = {i,it->it.mal_id ?: i}) {index,content->
                    CardMovie(data = content) {
                        kFunction2(content.mal_id ?: -1,TypeMovies.Anime.name)
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
                itemsIndexed(listOfManga, key = {i,it->it.mal_id?:i}) {index,content->
                    CardMovie(data = content) {   kFunction2(content.mal_id ?: -1,TypeMovies.Manga.name)}
                }
            }
        }
        if (listOfVoices.isNotEmpty()) {
            Text(
                text = "Characters",
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
                            viewModel.isCallApi = false
                            navHostController.navigate(Screens.CHARACTER.name + "?idCharacter=${it.mal_id}")
                        }
                    }

                }
            }
        }

    }

