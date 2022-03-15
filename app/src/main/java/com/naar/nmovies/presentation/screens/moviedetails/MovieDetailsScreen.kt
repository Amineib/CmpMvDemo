package com.naar.nmovies.presentation.screens.moviedetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.naar.nmovies.presentation.screens.moviedetails.component.MovieDetailScreen


@Composable
fun MovieDetailsScreen(
    navController: NavController,
    movieId: Int,
    scaffoldState: ScaffoldState,
    onNavigationToRecommendedMovie: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    Scaffold(
    ) {
        Box(modifier = modifier.background(MaterialTheme.colors.background)){
            MovieDetailScreen(
                movieId = movieId,
                onNavigateUp = {
                    navController.navigateUp()
                },
                scaffoldState = scaffoldState,
                onNavigateToMovie = { onNavigationToRecommendedMovie(it) }
            )
        }
    }
}



