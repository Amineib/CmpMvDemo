package com.naar.nmovies

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.HiltAndroidApp
import androidx.datastore.preferences.core.Preferences


const val DARK_NIGHT_MODE_SETTING = "settings"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DARK_NIGHT_MODE_SETTING)

@HiltAndroidApp
class MyApplication  : Application() {

}

