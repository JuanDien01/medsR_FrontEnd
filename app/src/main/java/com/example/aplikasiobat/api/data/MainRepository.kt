package com.example.aplikasiobat.api.data

import com.example.aplikasiobat.api.request.RegisterRequest
import com.example.aplikasiobat.api.service.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {
    suspend fun registerUser(registerRequest: RegisterRequest) = apiHelper.registerUser(registerRequest)
}