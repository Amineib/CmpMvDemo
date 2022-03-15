package com.naar.nmovies.navigation

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.naar.nmovies.presentation.screens.moviedetails.MovieDetailsScreen
import com.naar.nmovies.presentation.screens.movielist.MovieListScreen
import com.naar.nmovies.presentation.screens.search.SearchScreen
import com.naar.nmovies.presentation.screens.seriedetail.SerieDetailScreen
import com.naar.nmovies.presentation.screens.serielist.SerieListScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState
) {
    NavHost(navController = navController, startDestination = BottomNavigationItem.MoviesHome.route)
    {
        composable(route = BottomNavigationItem.MoviesHome.route)
        {
            MovieListScreen(modifier = modifier,
                navController = navController, onMovieClicked = {
                    navController.navigate(Route.MOVIES_DETAIL + "/$it")
                })
        }
        composable(
            route = Route.MOVIES_DETAIL + "/{movieId}",
            arguments = listOf(
                navArgument(name = "movieId") {
                    type = NavType.IntType
                }
            )
        )
        {
            val movieId by remember {
                mutableStateOf(it.arguments?.getInt("movieId"))
            }
            movieId?.let {
                MovieDetailsScreen(
                    navController,
                    it,
                    scaffoldState = scaffoldState,
                    onNavigationToRecommendedMovie = { movieId ->
                        navController.navigate(Route.MOVIES_DETAIL+"/"+movieId.toString())
                    },
                    modifier = modifier
                )
            }
        }


        //Series List
        composable(route = BottomNavigationItem.SeriesHome.route)
        {
            SerieListScreen(
                onItemClicked = { //n
                    navController.navigate(Route.SERIES_DETAIL + "/$it")
                })
        }
        //Serie Detail
        composable(
            route = Route.SERIES_DETAIL + "/{serieId}",
            arguments = listOf(
                navArgument(name = "serieId") {
                    type = NavType.IntType
                }
            )
        )
        {
            val movieId by remember {
                mutableStateOf(it.arguments?.getInt("serieId"))
            }
            movieId?.let {
                SerieDetailScreen(
                    navController = navController,
                    serieId = it,
                    modifier = modifier
                )
            }
        }


        //TODO
        composable(route = BottomNavigationItem.Search.route)
        {
            SearchScreen(
                modifier = modifier,
                navController = navController)
        }

    }

}