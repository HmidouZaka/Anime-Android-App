package com.projectbyzakaria.animes.ui.activitys

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.imageLoader
import com.projectbyzakaria.animes.R
import com.projectbyzakaria.animes.model.User
import com.projectbyzakaria.animes.ui.component.NavigationBottomBar
import com.projectbyzakaria.animes.ui.component.ShowDialog
import com.projectbyzakaria.animes.ui.screens.*
import com.projectbyzakaria.animes.ui.theme.AnimesTheme
import com.projectbyzakaria.animes.ui.view_models.DataViewModel
import com.projectbyzakaria.animes.ui.view_models.StateUiViewModel
import com.projectbyzakaria.animes.utilt.Constent
import com.projectbyzakaria.animes.utilt.Screens
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalComposeUiApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel: StateUiViewModel by viewModels()
    val viewModelData: DataViewModel by viewModels()
    lateinit var navHostController: NavHostController

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter", "ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        viewModelData.observerInternet()
        val start = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val url = it.data?.data
            if (url != null) {
                val bitmap = url.let { it1 -> contentResolver.openInputStream(it1) }
                viewModelData.upDateImage(BitmapFactory.decodeStream(bitmap))
            }

        }
        setContent {
            AnimesTheme {
                navHostController = rememberNavController()
                navHostController.addOnDestinationChangedListener { controller, destination, arguments ->
                    viewModel.gotoPage(destination.route)
                }
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    scaffoldState = scaffoldState,
                    bottomBar = {
                        NavigationBottomBar(modifier = Modifier.fillMaxWidth(), viewModel) {
                            if (it.name != navHostController.currentDestination?.route) {
                                navHostController.apply {
                                    navigate(it.name) {
                                        if (currentDestination?.route != Screens.HOME.name) {
                                            popBackStack()
                                            launchSingleTop = true
                                        }
                                    }
                                }
                            }
                        }
                    }, modifier = Modifier.fillMaxSize().semantics {
                        this.testTagsAsResourceId = true
                    }
                ) {
                    NavHost(
                        navController = navHostController,
                        startDestination = Screens.HOME.name,
                        modifier = Modifier
                    ) {
                        composable(Screens.HOME.name) {
                            HomeScreen(
                                Modifier.fillMaxSize(),
                                viewModel,
                                viewModelData,
                                ::gotoDetaile
                            )
                        }
                        composable(Screens.SEARCH.name) {
                            viewModel.gotoInstialValue()
                            SearchScreen(
                                navHostController,
                                Modifier.fillMaxSize(),
                                viewModel,
                                viewModelData,
                                ::gotoDetaile,
                                this@MainActivity
                            )
                        }
                        composable(Screens.FAVORITE.name) {
                            viewModel.gotoInstialValue()
                            FavoriteScreen(
                                navHostController = navHostController,
                                Modifier.fillMaxSize(), viewModelData, ::gotoDetaile
                            )
                        }
                        composable(Screens.PROFILE.name) {
                            viewModel.gotoInstialValue()
                            ProfileScreen(
                                navHostController = navHostController,
                                Modifier.fillMaxSize(),
                                onClickChangeImage = {
                                    start.launch(Intent().apply {
                                        type = "image/*"
                                        action = Intent.ACTION_PICK
                                    })
                                }, viewModel = viewModelData
                            )
                        }
                        composable(
                            route = Screens.CHARACTER.name + "?idCharacter={idCharacter}",
                            arguments = listOf(
                                navArgument("idCharacter") {
                                    type = NavType.IntType
                                }
                            )) {
                            viewModel.hideNavigation()
                            CharacterScreen(
                                it.arguments?.getInt("idCharacter"),
                                navHostController = navHostController,
                                viewModelData,
                                Modifier.fillMaxSize(), this@MainActivity, ::gotoDetaile
                            )
                        }
                        composable(Screens.People.name + "?idPeople={idPeople}",
                            arguments = listOf(
                                navArgument("idPeople") {
                                    type = NavType.IntType
                                }
                            )) {
                            viewModel.hideNavigation()
                            PeopleScreen(
                                it.arguments?.getInt("idPeople"),
                                navHostController = navHostController, viewModelData,
                                Modifier.fillMaxSize(), this@MainActivity, ::gotoDetaile
                            )
                        }
                    }
                }
                ShowDialog(
                    title = "Attentions",
                    image = R.drawable.finish,
                    "Are you shore do you wont exit",
                    "Yes",
                    "No",
                    {
                        finish()
                    }, {
                        viewModel.hideDialog()
                    }, Modifier, viewModel
                )
            }


        }
    }


    override fun onBackPressed() {
        if (navHostController.backQueue.size <= 2) {
            viewModel.showDialog()
        } else {
            super.onBackPressed()
        }
    }

    private fun gotoDetaile(id: Int, type: String) {
        val intent = Intent(this, AnimeDetailes::class.java)
        intent.putExtra(Constent.KEY_FOR_PASS_TYPE_BETWEEN_ACTIVITY, type)
        intent.putExtra(Constent.KEY_FOR_PASS_ID_BETWEEN_ACTIVITY, id)
        startActivity(intent)
    }
}