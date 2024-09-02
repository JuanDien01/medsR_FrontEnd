package com.example.aplikasiobat.api.data

import com.example.aplikasiobat.api.request.LoginRequest
import com.example.aplikasiobat.api.request.RegisterRequest
import com.example.aplikasiobat.api.request.RegisterUserBiodataRequest
import com.example.aplikasiobat.api.service.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {
    suspend fun registerUser(registerRequest: RegisterRequest) = apiHelper.registerUser(registerRequest)
    suspend fun registerUserBiodata(registerUserBiodataRequest: RegisterUserBiodataRequest) = apiHelper.registerUserBiodata(registerUserBiodataRequest)
    suspend fun login(request: LoginRequest) = apiHelper.login(request)
    suspend fun getObatPasien(idUser:Int) = apiHelper.getObatPasien(idUser)
}