
package com.projectbyzakaria.animes.ui.activitys

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.projectbyzakaria.animes.ui.component.*
import com.projectbyzakaria.animes.ui.screens.CharacterScreen
import com.projectbyzakaria.animes.ui.screens.DetaileSvreen
import com.projectbyzakaria.animes.ui.screens.PeopleScreen
import com.projectbyzakaria.animes.ui.theme.AnimesTheme
import com.projectbyzakaria.animes.ui.view_models.AnimeDtelesViewModel
import com.projectbyzakaria.animes.utilt.Constent.KEY_FOR_PASS_ID_BETWEEN_ACTIVITY
import com.projectbyzakaria.animes.utilt.Constent.KEY_FOR_PASS_TYPE_BETWEEN_ACTIVITY
import com.projectbyzakaria.animes.utilt.Screens
import com.projectbyzakaria.animes.utilt.TypeMovies
import com.projectbyzakaria.views.ui.activitys.ImagesActivity
import com.projectbyzakaria.views.ui.activitys.WebActivity
import com.projectbyzakaria.views.utlis.Constent
import com.projectbyzakaria.views.utlis.Constent.TITLE_KEY
import com.projectbyzakaria.views.utlis.Constent.URL_KEY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnimeDetailes : ComponentActivity() {

    val viewModel: AnimeDtelesViewModel by viewModels()
    lateinit var  coroutineScope: CoroutineScope
    lateinit var  navHostController: NavHostController
    lateinit var type:String
    @OptIn(ExperimentalMaterialApi::class)
    lateinit var bottomSheetState: BottomSheetState
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = intent.getIntExtra(KEY_FOR_PASS_ID_BETWEEN_ACTIVITY, -1)
         type = intent.getStringExtra(KEY_FOR_PASS_TYPE_BETWEEN_ACTIVITY) ?: TypeMovies.Anime.name
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            AnimesTheme {
                 coroutineScope = rememberCoroutineScope()
                 bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed, tween(1000))
                 navHostController = rememberNavController()
                NavHost(navController = navHostController, startDestination = "DetailFirst") {
                    composable("DetailFirst") {
                        Log.d("AnimeDetailes", "onCreate:       DetailFirst  ")
                        if (id == -1) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "No Data For the Movies")
                            }
                        } else {
                            DetaileSvreen(
                                id = id,
                                this@AnimeDetailes,
                                type,
                                viewModel,
                                navHostController = navHostController,coroutineScope,bottomSheetState
                            )
                        }
                    }
                    composable("DetailSuccend?idM={idM}", arguments = listOf(

                        navArgument("idM"){
                            this.type = NavType.IntType
                            this.defaultValue = -1
                        }
                    )) {
                        Log.d("AnimeDetailes", "onCreate:       DetailSuccend  ")
                        if (it.arguments?.getInt("idM") == -1 && id == -1) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "No Data For the Movies")
                            }
                        } else {
                            val idM = if (it.arguments?.getInt("idM") == -1) id else it.arguments?.getInt("idM") ?: id
                            DetaileSvreen(
                                id = idM,
                                this@AnimeDetailes,
                                type,
                                viewModel,
                                navHostController = navHostController,coroutineScope,bottomSheetState
                            )
                        }
                    }
                    composable(
                        route= Screens.CHARACTER.name+"?idCharacter={idCharacter}",
                        arguments = listOf(
                            navArgument("idCharacter"){
                                this.type = NavType.IntType
                            }
                        )) {
                        CharacterScreen(
                            it.arguments?.getInt("idCharacter"),
                            navHostController = navHostController,
                            viewModel,
                            Modifier.fillMaxSize(),this@AnimeDetailes,::gotoDetaile
                        )
                    }
                    composable(
                        Screens.People.name+"?idPeople={idPeople}",
                        arguments = listOf(
                            navArgument("idPeople"){
                                this.type = NavType.IntType
                            }
                        )) {
                        PeopleScreen(
                            it.arguments?.getInt("idPeople"),
                            navHostController = navHostController, viewModel,
                            Modifier.fillMaxSize(),this@AnimeDetailes,::gotoDetaile
                        )
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    private fun gotoDetaile(id: Int, type: String) {
        coroutineScope.launch {

            if (bottomSheetState.isExpanded){
                bottomSheetState.collapse()
            }
        }
        this.type = type
        viewModel.isLoadData = false
        navHostController.navigate("DetailSuccend?idM=${id}")
    }


     fun showImages(list: ArrayList<String>,position:Int){
        var intentImages = Intent(this,ImagesActivity::class.java)
        intentImages.putStringArrayListExtra(Constent.KEY_LIST_IMAGES, list)
        intentImages.putExtra(Constent.POSITION,position)
        startActivity(intentImages)
    }

    @OptIn(ExperimentalMaterialApi::class)
    override fun onBackPressed() {
        coroutineScope.launch {
            if (bottomSheetState.isExpanded){
                bottomSheetState.collapse()
            }else{
                super.onBackPressed()
            }
        }
    }


    fun gotoWebPage(url:String,title:String){
        var intentWeb = Intent(this,WebActivity::class.java)
        intentWeb.putExtra(TITLE_KEY,title)
        intentWeb.putExtra(URL_KEY,url)
        startActivity(intentWeb)
    }



}
