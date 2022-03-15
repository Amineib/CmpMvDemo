package com.naar.nmovies.data

import android.util.Log

fun <T> Any.safeParse(block: (Any) -> T): T? {
    return try {
        block(this)
    } catch (ex: IllegalStateException) {
        Log.e("Parse Exception", ex.message, ex)
        null
    }
}

fun <T> requiredField(fieldName: String, value: T?): T {
    return checkNotNull(value) {
        "Required field $fieldName is missing"
    }
}