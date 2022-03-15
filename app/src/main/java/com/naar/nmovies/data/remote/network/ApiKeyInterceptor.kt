package com.naar.nmovies.data.remote.network

import com.naar.nmovies.utils.Constants
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()
        val url = original.url.newBuilder()
            //TODO : Api key should not be in constants
            .addQueryParameter("api_key", Constants.API_KEY)
            .build()
        original = original.newBuilder().url(url).build()
        return chain.proceed(original)
    }
}