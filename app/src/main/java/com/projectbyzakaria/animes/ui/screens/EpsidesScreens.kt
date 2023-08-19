package com.projectbyzakaria.animes.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.projectbyzakaria.animes.R
import com.projectbyzakaria.animes.ui.component.EpsidesComponent
import com.projectbyzakaria.animes.ui.component.ErrorShow
import com.projectbyzakaria.animes.ui.component.LoadingProgress
import com.projectbyzakaria.animes.ui.view_models.AnimeDtelesViewModel
import com.projectbyzakaria.animes.utilt.ResponseState

@Composable
fun EpsidesScreen(
    viewModel:AnimeDtelesViewModel,
    modifier :Modifier= Modifier,
    id:Int,onClickEpsides:(url:String,title:String)->Unit
) {
    val result  = viewModel.episodes.collectAsState()

    if (result.value is ResponseState.Loading){
        LoadingProgress(modifier = Modifier)
    }else if (result.value is ResponseState.Success){
        val data = (result.value as ResponseState.Success).data.data
        if (data.isNotEmpty()){
            Column(modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
            ) {
                LazyColumn(contentPadding = PaddingValues(8.dp), verticalArrangement = Arrangement.spacedBy(4.dp)){
                    itemsIndexed(data, key = {i,it->it.mal_id ?: i}){index,it->
                        EpsidesComponent(it){u,t->
                            onClickEpsides(u,t)
                        }
                        if (!viewModel.isStartLoading && index == data.size - 1 && viewModel.isUserHaveInternet){
                            viewModel.getMoreEpsides(id)
                        }
                    }
                    if (viewModel.isLastVisibleIndexItem && viewModel.paginationEpisodes?.has_next_page == true){
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
        }else{
            ErrorShow(error = "No Episodes Found",
                onClick = null, image = R.drawable.finish, modifier = Modifier.fillMaxSize()
            )
        }
    }else{
        val error = result.value as ResponseState.Error
        ErrorShow(
            error = error.message,
            onClick = {
                 viewModel.refrechEpsides(id)
            }, modifier = Modifier.fillMaxSize()
        )
    }
}

