package com.example.moviesapp.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.moviesapp.view.screens.DetailsScreen
import com.example.moviesapp.view.screens.HomeScreen
import com.example.moviesapp.viewmodel.ShowsViewModel

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            val viewModel: ShowsViewModel = hiltViewModel()
            HomeScreen(viewModel, navController)
        }

        composable(
            route = Routes.DETAILS,
            arguments = listOf(navArgument("movieId") { type = NavType.StringType })
        ) { backStackEntry ->

            // Get the HomeScreen backStackEntry to share ViewModel
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Routes.HOME)
            }
            val viewModel: ShowsViewModel = hiltViewModel(parentEntry)

            val movieId = backStackEntry.arguments?.getString("movieId") ?: ""
            val show = viewModel.getShowById(movieId)
            DetailsScreen(
                movieId = movieId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() })
        }
    }
}