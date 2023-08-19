package com.projectbyzakaria.animes.utilt

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.projectbyzakaria.animes.ui.activitys.AnimeDetailes
import com.projectbyzakaria.animes.ui.screens.*
import com.projectbyzakaria.animes.ui.view_models.AnimeDtelesViewModel


sealed class ScreensStart(var title: String, val content: @Composable () -> Unit) {
    data class MainScreen(
        val viewModel: AnimeDtelesViewModel,
        val id: Int,
        val type: TypeMovies,
        val context: Context,
        val activity: AnimeDetailes
    ) :
        ScreensStart(
            "Main",
            {
                MoreInfoScreen(
                    viewModel = viewModel,
                    id = id,
                    type = type.name,
                    context = context,
                    activity = activity
                )
            })

    data class StaffScreen(
        val viewModel: AnimeDtelesViewModel,
        val id: Int,
        val navHostController: NavHostController
    ) :
        ScreensStart(
            "Stuff",
            {
                com.projectbyzakaria.animes.ui.screens.StaffScreen(
                    viewModel = viewModel,
                    id = id,
                    navHostController = navHostController
                )
            })

    data class EpsidesScreen(
        val viewModel: AnimeDtelesViewModel,
        val id: Int,
        val navHostController: NavHostController,
        val onClickEpsides: (url: String, title: String) -> Unit
    ) :
        ScreensStart(
            "Episodes",
            { EpsidesScreen(viewModel = viewModel, id = id, onClickEpsides = onClickEpsides) })

    data class CharacterScreen(
        val viewModel: AnimeDtelesViewModel,
        val id: Int,
        val type: TypeMovies,
        val navHostController: NavHostController
    ) :
        ScreensStart(
            "Characters",
            {
                ShowCharactersScreens(
                    viewModel = viewModel,
                    id = id,
                    typeMovies = type,
                    navHostController = navHostController
                )
            })

    data class ReviewsScreens(
        val viewModel: AnimeDtelesViewModel,
        val id: Int,
        val type: TypeMovies
    ) :
        ScreensStart(
            "Reviews",
            { ReviewsScreen(viewModel = viewModel, id = id, typeMovies = type) })
}
