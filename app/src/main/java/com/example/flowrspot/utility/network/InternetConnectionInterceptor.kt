package com.example.flowrspot.utility.network

import android.content.Context
import com.example.flowrspot.utility.exception.InternetConnectivityException
import com.example.flowrspot.utility.extension.isConnectedToInternet
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InternetConnectionInterceptor @Inject constructor(
    @ApplicationContext private val context: Context
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!context.isConnectedToInternet()) {
            throw InternetConnectivityException()
        }

        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }
}