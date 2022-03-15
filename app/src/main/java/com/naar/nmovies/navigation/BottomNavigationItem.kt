package com.naar.nmovies.navigation

import com.naar.nmovies.R


sealed class BottomNavigationItem(var title: String, var icon: Int, var route: String){
    object MoviesHome : BottomNavigationItem("Movies", R.drawable.ic_baseline_movie_24, "movies_home")
    object SeriesHome : BottomNavigationItem("Series", R.drawable.ic_baseline_local_movies_24, "series_home")
    object Search : BottomNavigationItem("Search", R.drawable.ic_baseline_search_24, "search")
}

var navigationItems = listOf(
    BottomNavigationItem.MoviesHome,
    BottomNavigationItem.SeriesHome,
    BottomNavigationItem.Search
)