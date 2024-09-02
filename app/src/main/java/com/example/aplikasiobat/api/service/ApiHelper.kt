package com.example.aplikasiobat.api.service

import com.example.aplikasiobat.api.request.LoginRequest
import com.example.aplikasiobat.api.request.RegisterRequest
import com.example.aplikasiobat.api.request.RegisterUserBiodataRequest

class ApiHelper(private val apiService: ApiService) {
    suspend fun registerUser(registerRequest: RegisterRequest)= apiService.registerUser(registerRequest)
    suspend fun registerUserBiodata(registerUserBiodataRequest: RegisterUserBiodataRequest)= apiService.registerUserBiodata(registerUserBiodataRequest)
    suspend fun login(request: LoginRequest)= apiService.login(request)
    suspend fun getObatPasien(idUser:Int)= apiService.getObatPasienById(idUser)
}