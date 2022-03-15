package com.naar.nmovies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.naar.nmovies.navigation.BottomNavigationMenu
import com.naar.nmovies.navigation.NavigationGraph
import com.naar.nmovies.presentation.theme.BaseMoviesTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BaseMoviesTheme(
            ) {
                val scaffoldState = rememberScaffoldState()
                val navController = rememberNavController()
                Scaffold(
                    scaffoldState = scaffoldState,
                    bottomBar = {
                        BottomNavigationMenu(navController = navController)
                    },
                    topBar = {

                    }
                ) { paddingValues ->

                    NavigationGraph(navController = navController, scaffoldState = scaffoldState,
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}
