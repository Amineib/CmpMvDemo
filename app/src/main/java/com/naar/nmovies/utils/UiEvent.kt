package com.naar.nmovies.utils

import android.content.Context

sealed class UiEvent {
    object NavigateUp: UiEvent()
    data class NavigateToMovie(val movieId: Int) : UiEvent()
    data class NavigateToSerie(val serieId: Int) : UiEvent()
    data class ShowSnackBar(val message: UiText): UiEvent()
    data class ShowInterstitial(val context: Context): UiEvent()

}

sealed class UiText {

    data class DynamicString(val message: String) : UiText()
    data class StringResource(val resId: Int): UiText()
    fun asString(context: Context): String {
        return when(this){
            is DynamicString -> message
            is StringResource -> context.getString(resId)
        }
    }
}
