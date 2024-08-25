package com.example.aplikasiobat.api.request

data class RegisterRequest(
    val id_pengguna: Int,
    val password: String,
    val roleId: Int,
    val username: String
)