package com.example.aplikasiobat.api.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    //base url api
    const val BASE_URL = "http://203.194.114.72:5123/"

    private val logging: HttpLoggingInterceptor
        get() {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            return httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
        }

    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS) // Adjust the timeout as necessary
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(RetryInterceptor(maxRetry = 3))
        .addInterceptor(logging)
        .build()
    val instance: ApiService by lazy {
        val retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .client(
                    client
                ).build()
        retrofit.create(ApiService::class.java)
    }

}