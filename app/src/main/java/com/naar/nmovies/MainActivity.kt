package com.naar.nmovies

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.applovin.sdk.AppLovinSdk
import com.facebook.ads.AdSettings
import com.naar.nmovies.ads.AdBanner
import com.naar.nmovies.navigation.BottomNavigationMenu
import com.naar.nmovies.navigation.NavigationGraph
import com.naar.nmovies.presentation.theme.BaseMoviesTheme
import com.naar.nmovies.utils.getContextActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlin.coroutines.coroutineContext


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private var isNightMode = mutableStateOf(false)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AdSettings.setDataProcessingOptions(arrayOf<String>())
        AppLovinSdk.getInstance(this).mediationProvider = "max"
        AppLovinSdk.initializeSdk(this) {
            // AppLovin SDK is initialized, start loading ads
        }

        lifecycleScope.launch(Dispatchers.IO) {
            this@MainActivity.dataStore.data.collect{
                isNightMode.value = it[booleanPreferencesKey("settings")] ?: false
                Log.d("NIGHTMODE", isNightMode.value.toString())
            }
        }

        setContent {
            BaseMoviesTheme(
                darkTheme = isNightMode.value
            ) {
                val scaffoldState = rememberScaffoldState()
                val navController = rememberNavController()
                Scaffold(
                    scaffoldState = scaffoldState,
                    bottomBar = {
                        BottomNavigationMenu(navController = navController)
                    },
                    topBar = {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            /*AdBanner(modifier = Modifier
                                .height(50.dp)
                                .weight(5f)
                                .padding(horizontal = 10.dp)
                            )*/
                            Column(modifier = Modifier.weight(5f)) {
                                //Same space as AdBanner
                            }
                            Column(modifier = Modifier
                                .weight(1f)
                                .background(color = Color(2), RoundedCornerShape(2.dp)),
                                horizontalAlignment = Alignment.End,
                                verticalArrangement = Arrangement.Center
                            )
                            {
                                val checkedState = remember {
                                    mutableStateOf(isNightMode.value)
                                }
                                IconButton(onClick = {
                                    checkedState.value = !checkedState.value
                                    lifecycleScope.launch {
                                        editDataStore(this@MainActivity, checkedState.value)
                                    }
                                    }
                                ) {
                                    when(checkedState.value){
                                     true -> Icon(
                                         Icons.Filled.LightMode,
                                         "Night Mode",
                                         tint = MaterialTheme.colors.onBackground
                                     )
                                     false ->  Icon(
                                         Icons.Filled.DarkMode,
                                         "Light Mode",
                                         tint = MaterialTheme.colors.onBackground
                                     )
                                    }

                                }
                                
                            }
                        }
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

@Composable
fun SwitchNightMode() {

}


private suspend fun editDataStore(context: Context, isNightMode: Boolean){
    val SETTINGS = booleanPreferencesKey(DARK_NIGHT_MODE_SETTING)
    context.dataStore.edit { settings ->
        settings[SETTINGS] = isNightMode
        }
}