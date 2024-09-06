package com.example.aplikasiobat.api.service

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class RetryInterceptor(private val maxRetry: Int) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var attempt = 0
        var response: Response? = null
        var exception: IOException? = null

        while (attempt < maxRetry) {
            try {
                response = chain.proceed(chain.request())
                break // Request succeeded, break the loop
            } catch (e: IOException) {
                exception = e
                attempt++
                if (attempt == maxRetry) {
                    throw e // All retries failed, rethrow the exception
                }
            }
        }
        return response ?: throw exception!! // Either return the response or throw the last caught exception
    }
}
