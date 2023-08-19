package com.projectbyzakaria.animes.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.projectbyzakaria.animes.ui.activitys.MainActivity
import com.projectbyzakaria.animes.ui.component.*
import com.projectbyzakaria.animes.ui.view_models.DataViewModel
import com.projectbyzakaria.animes.ui.view_models.StateUiViewModel
import com.projectbyzakaria.animes.utilt.*
import kotlinx.coroutines.*
var coroutineSearch:CoroutineScope? = null
@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModelUi: StateUiViewModel,
    dataViewModel: DataViewModel,
    goToDetailes: (Int, String) -> Unit,
    mainActivity: MainActivity
) {




    val bottomSheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed,
        animationSpec = tween(700)
    )
    val onScroll = object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
            viewModelUi.changePositionScroll(available.y)
            return super.onPreScroll(available, source)
        }
    }
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState)
    val coroutine = rememberCoroutineScope()
    BottomSheetScaffold(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(onScroll)
            .pointerInput(Unit) {
                detectTapGestures {
                    coroutine.launch {
                        if (bottomSheetState.isExpanded) {
                            bottomSheetState.collapse()
                            viewModelUi.showNavigation()
                        }
                        cancel()
                    }
                }
            },
        scaffoldState = scaffoldState,
        sheetContent = {
            BottomSheetComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 50.dp), viewModelUi
            ) {
                if (it == FilterContent.Anime){
                    dataViewModel.searchAnimeFirstFilter(query = dataViewModel.query?: "")
                }else if (it == FilterContent.Manga){
                    dataViewModel.searchMangaFirstFilter(query = dataViewModel.query?: "")
                }else if (it == FilterContent.Character){
                    dataViewModel.searchCharacterFirstFilter()
                }else if (it == FilterContent.Person){
                    dataViewModel.searchPeopleFirstFilter()
                }
                if (bottomSheetState.isExpanded) {
                    coroutine.launch {
                        bottomSheetState.collapse()
                        cancel()
                    }
                }
            }
        },
        backgroundColor = MaterialTheme.colors.background,
        sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        sheetPeekHeight = 0.dp,
        sheetGesturesEnabled = true,

        ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                SearchComponent(Modifier.weight(1f), {
                    dataViewModel.query = it
                    coroutineSearch?.cancel()
                    coroutineSearch = MainScope()
                    coroutineSearch?.launch {
                        delay(500)
                        when (viewModelUi.filter) {
                            FilterContent.Anime -> dataViewModel.searchAnimeFirstFilter(it)
                            FilterContent.Person -> {dataViewModel.searchPeopleFirstFilter()}
                            FilterContent.Manga -> dataViewModel.searchMangaFirstFilter(it)
                            FilterContent.Character -> { dataViewModel.searchCharacterFirstFilter()}
                        }
                    }

                }, {
                    dataViewModel.query = it
                    var activityManager = navController.context.getSystemService(InputMethodManager::class.java)
                    var focus =mainActivity.currentFocus

                    if (focus != null){
                        activityManager.hideSoftInputFromWindow(focus.windowToken,0)
                    }
                })
                FActionBottom{
                    coroutine.launch {
                        if (bottomSheetState.isExpanded) {
                            bottomSheetState.collapse()
                            viewModelUi.showNavigation()
                        } else {
                            viewModelUi.hideNavigation()
                            bottomSheetState.expand()
                        }
                        cancel()
                    }
                }
            }
            when (viewModelUi.filter) {
                FilterContent.Anime -> {
                    AnimePageSearch(modifier, dataViewModel,goToDetailes)
                }

                FilterContent.Person -> {
                    PersonSearch({
                        it?.let {id->
                            navController.navigate(Screens.People.name+"?idPeople=$id")
                        }
                    }, viewModelData = dataViewModel)
                }
                FilterContent.Manga -> {
                    MangaPageSearch( modifier, dataViewModel,goToDetailes)
                }
                FilterContent.Character -> {
                    CharacterSearch({
                        it.let {id->
                            navController.navigate(Screens.CHARACTER.name+"?idCharacter=$id")
                        }
                    }, viewModelData = dataViewModel)
                }
            }
        }
    }
}


@SuppressLint("UnrememberedMutableState")
@Composable
fun AnimePageSearch(
    modifier: Modifier = Modifier,
    viewModelData: DataViewModel,
    goToDetailes: (Int, String) -> Unit
) {
    val topAnime = viewModelData.searchAnime.collectAsState()
    var context  = LocalContext.current
    if (topAnime.value is ResponseState.Loading) {
        LoadingProgress(modifier = Modifier.fillMaxSize())
    } else if (topAnime.value is ResponseState.Success) {
        val count = when (LocalConfiguration.current.orientation) {
            1 -> 3
            else -> 5
        }
        Column(
            modifier.fillMaxSize()
        ) {
            val gridState = rememberLazyGridState()
            val data = (topAnime.value as ResponseState.Success).data
            LazyVerticalGrid(
                columns = GridCells.Fixed(count),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(3.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                state = gridState
            ) {
                Log.d("testsssssssssssssssssssssssssss", "loadMoreManga: AnimePage ${data.size}")
                var listOfData = data.uniqueList()
                itemsIndexed(listOfData,key = {i,d-> d.mal_id ?: i}) { index, anime ->
                    CardMovie(data = anime) {
                        goToDetailes(anime.mal_id ?: -1, TypeMovies.Anime.name)
                    }
                    if (!viewModelData.isStartLoadingAnimeSearch && viewModelData.internetIsAvailable  && data.size -1 == index){
                        viewModelData.showProgressBarAnimeSearch {
                            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                if (viewModelData.isLastVisibleItemForAnimeSearch) {
                    item(span = { GridItemSpan(count) }) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

            }

        }
    } else {
        val error = (topAnime.value as ResponseState.Error).message
        Box(modifier  = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = error, textAlign = TextAlign.Center,modifier=Modifier.padding(top = 8.dp))
        }
    }

}


@SuppressLint("UnrememberedMutableState")
@Composable
fun MangaPageSearch(
    modifier: Modifier = Modifier,
    viewModelData: DataViewModel,
    goToDetailes: (Int, String) -> Unit
) {
    val topAnime = viewModelData.searchManga.collectAsState()

    if (topAnime.value is ResponseState.Loading) {
        LoadingProgress(modifier = Modifier.fillMaxSize())
    } else if (topAnime.value is ResponseState.Success) {
        val count = when (LocalConfiguration.current.orientation) {
            1 -> 3
            else -> 5
        }
        Column(
            modifier.fillMaxSize()
        ) {
            val gridState = rememberLazyGridState()
            val data = (topAnime.value as ResponseState.Success).data
            LazyVerticalGrid(
                columns = GridCells.Fixed(count),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(3.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                state = gridState
            ) {
                var listOfData = data.uniqueList()
                itemsIndexed(listOfData,key = {i,d-> d.mal_id?:i}) { index, anime ->
                    CardMovie(data = anime) {
                        goToDetailes(anime.mal_id ?: -1, TypeMovies.Manga.name)
                    }
                    if (!viewModelData.isStartLoadingMangaSearch && viewModelData.internetIsAvailable  && data.size -1 == index){
                        viewModelData.showProgressBarMangaSearch {

                        }
                    }
                }
                if (viewModelData.isLastVisibleItemForMangaSearch) {
                    item(span = { GridItemSpan(count) }) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }

        }
    } else {
        val error = (topAnime.value as ResponseState.Error).message
        Box(modifier  = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = error, textAlign = TextAlign.Center,modifier=Modifier.padding(top = 8.dp))
        }
    }

}


@SuppressLint("UnrememberedMutableState")
@Composable
fun PersonSearch(
    onClickItem: (Int?) -> Unit,
    modifier: Modifier = Modifier,
    viewModelData: DataViewModel
) {
    val SearchPeople = viewModelData.searchPeople.collectAsState()

    if (SearchPeople.value is ResponseState.Loading) {
        LoadingProgress(modifier = Modifier.fillMaxSize())
    } else if (SearchPeople.value is ResponseState.Success) {
        Column(
            modifier.fillMaxSize()
        ) {
            val data = (SearchPeople.value as ResponseState.Success).data
            val rememberLazyListState = rememberLazyListState()
            Log.d("testsssssssssssssssssssssssssss", "showProgressBarCharactersSearch: recomposetion 1 ")
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(3.dp),
                state = rememberLazyListState
            ) {
                var listOfData = data.uniqueList()
                itemsIndexed(items = listOfData,key = {i,d-> d.mal_id?:i}){index,people->
                    PersonCard(dataPerson = people) {
                        viewModelData.isCallApiPeople = false
                        onClickItem(people.mal_id)
                    }
                    if (!viewModelData.isStartLoadingPeopleSearch && index == data.size -1 && viewModelData.internetIsAvailable){
                        viewModelData.showProgressBarPeopleSearch{

                        }
                    }
                }
                if (viewModelData.isLastVisibleItemForPeopleSearch) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }

        }
    } else {
        val error = (SearchPeople.value as ResponseState.Error).message
        Box(modifier  = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = error, textAlign = TextAlign.Center,modifier=Modifier.padding(top = 8.dp))
        }
    }

}


@SuppressLint("UnrememberedMutableState")
@Composable
fun CharacterSearch(
    onClickItem: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModelData: DataViewModel
) {
    val characters = viewModelData.searchCharacters.collectAsState()

    if (characters.value is ResponseState.Loading) {
        LoadingProgress(modifier = Modifier.fillMaxSize())
    } else if (characters.value is ResponseState.Success) {
        val count = when (LocalConfiguration.current.orientation) {
            1 -> 2
            else -> 3
        }
        Column(
            modifier.fillMaxSize()
        ) {
            val gridState = rememberLazyGridState()
            val data = (characters.value as ResponseState.Success).data
            LazyVerticalGrid(
                columns = GridCells.Fixed(count),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(3.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                state = gridState
            ) {
                var listOfData = data.uniqueList()
                itemsIndexed(listOfData,key = {i,d-> d.mal_id ?: i}){index,item->
                    CharacterCard(data = item){
                        if (item.mal_id != null){
                            viewModelData.isCallApi = false
                            onClickItem(item.mal_id)
                        }
                    }
                    if (!viewModelData.isStartLoadingCharactersSearch && index == data.size -1 && viewModelData.internetIsAvailable){
                        viewModelData.showProgressBarCharactersSearch{

                        }
                    }
                }
                if (viewModelData.isLastVisibleItemForCharactersSearch) {
                    item(span = { GridItemSpan(count) }) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    } else {
        val error = (characters.value as ResponseState.Error).message
        Box(modifier  = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = error, textAlign = TextAlign.Center,modifier=Modifier.padding(top = 8.dp))
        }
    }

}




