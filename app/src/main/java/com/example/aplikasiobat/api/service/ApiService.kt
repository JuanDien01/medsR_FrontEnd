package com.example.aplikasiobat.api.service


import com.example.aplikasiobat.api.request.LoginRequest
import com.example.aplikasiobat.api.request.RegisterRequest
import com.example.aplikasiobat.api.request.RegisterUserBiodataRequest
import com.example.aplikasiobat.api.response.login.LoginResponse
import com.example.aplikasiobat.api.response.register.user.RegisterResponse
import com.example.aplikasiobat.api.response.register.userbiodata.RegisterUserBiodataResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/user/Login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("api/user/register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): RegisterResponse

    @POST("api/userBiodata/addUserBiodata")
    suspend fun registerUserBiodata(@Body registerUserBiodataRequest: RegisterUserBiodataRequest): RegisterUserBiodataResponse
}