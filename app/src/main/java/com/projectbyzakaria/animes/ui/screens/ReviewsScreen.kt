package com.projectbyzakaria.animes.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.projectbyzakaria.animes.R
import com.projectbyzakaria.animes.ui.component.ErrorShow
import com.projectbyzakaria.animes.ui.component.LoadingProgress
import com.projectbyzakaria.animes.ui.component.ReviewComponent
import com.projectbyzakaria.animes.ui.view_models.AnimeDtelesViewModel
import com.projectbyzakaria.animes.utilt.ResponseState
import com.projectbyzakaria.animes.utilt.TypeMovies
import com.projectbyzakaria.animes.utilt.uniqueList


@Composable
fun ReviewsScreen(
    viewModel: AnimeDtelesViewModel,
    id:Int,
    typeMovies: TypeMovies,
    modifier : Modifier = Modifier
) {
    val characters = viewModel.review.collectAsState()
    if (characters.value is ResponseState.Loading) {
        LoadingProgress(modifier = Modifier.fillMaxSize())
    } else if (characters.value is ResponseState.Success) {
        val data = (characters.value as ResponseState.Success).data.data.uniqueList()
        if (data.isNotEmpty()) {
            Column(
                modifier.fillMaxSize()
            ) {
                val lazyState = rememberLazyListState()

                LazyColumn(
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(3.dp),
                    state = lazyState
                ) {
                    itemsIndexed(data,key = {i,it->it.mal_id ?: i}) {index,it->
                        ReviewComponent(review = it, Modifier, {}) {

                        }
                        if (!viewModel.isStartLoadingReviews && index == data.size - 1 && viewModel.isUserHaveInternet){
                            viewModel.getMoreReviews(id,typeMovies)
                        }
                    }
                    if (viewModel.isLastVisibleIndexItemReviews){
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(color = Color.Black)
                            }
                        }
                    }
                }
            }
        } else {
            ErrorShow(error = "No Reviews", onClick = null, modifier = Modifier.fillMaxSize())
        }

    } else {
        val error = (characters.value as ResponseState.Error).message
        ErrorShow(
            error = error,
            onClick = {
                when (typeMovies) {
                    TypeMovies.Anime -> viewModel.loadReviewsAnime(id)
                    TypeMovies.Manga -> viewModel.loadReviewsManga(id)
                }
            }, modifier = Modifier.fillMaxSize()
        )
    }
}

