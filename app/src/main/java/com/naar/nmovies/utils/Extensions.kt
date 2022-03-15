package com.naar.nmovies.utils

import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity

/*fun Context.getActivity(): AppCompatActivity? {
    var currentContext = this
    while (currentContext is ContextWrapper) {
        if (currentContext is AppCompatActivity) {
            return currentContext
        }
        currentContext = currentContext.baseContext
    }
    return null
}*/


fun Context.getContextActivity(): AppCompatActivity? = when (this) {
    is AppCompatActivity -> this
    is ContextWrapper -> baseContext.getContextActivity()
    else -> null
}
