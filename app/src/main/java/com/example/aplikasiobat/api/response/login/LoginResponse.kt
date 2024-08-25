package com.example.aplikasiobat.api.response.login

import com.example.aplikasiobat.api.response.login.Data

data class LoginResponse(
    val `data`: Data,
    val message: String,
    val status: String
)