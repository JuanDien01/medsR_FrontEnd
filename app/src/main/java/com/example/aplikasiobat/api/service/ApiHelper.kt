package com.example.aplikasiobat.api.service

import com.example.aplikasiobat.api.request.RegisterRequest

class ApiHelper(private val apiService: ApiService) {
    suspend fun registerUser(registerRequest: RegisterRequest)= apiService.registerUser(registerRequest)
}