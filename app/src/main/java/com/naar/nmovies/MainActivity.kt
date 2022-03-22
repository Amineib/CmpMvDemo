package com.naar.nmovies

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.applovin.sdk.AppLovinSdk
import com.facebook.ads.AdSettings
import com.naar.nmovies.ads.AdBanner
import com.naar.nmovies.navigation.BottomNavigationMenu
import com.naar.nmovies.navigation.NavigationGraph
import com.naar.nmovies.presentation.theme.BaseMoviesTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AdSettings.setDataProcessingOptions(arrayOf<String>())
        AppLovinSdk.getInstance(this).mediationProvider = "max"
        AppLovinSdk.initializeSdk(this) {
            // AppLovin SDK is initialized, start loading ads
        }

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
                        AdBanner(modifier = Modifier.height(50.dp).fillMaxWidth())
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
