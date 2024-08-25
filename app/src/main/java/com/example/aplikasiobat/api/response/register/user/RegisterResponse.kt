package com.example.aplikasiobat.api.response.register.user

import com.example.aplikasiobat.api.response.register.user.Data

data class RegisterResponse(
    val data: Data,
    val message: String,
    val status: String
)