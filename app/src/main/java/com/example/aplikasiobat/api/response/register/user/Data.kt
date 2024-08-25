package com.example.aplikasiobat.api.response.register.user

data class Data(
    val id: Int,
    val id_pengguna: Int,
    val password: String,
    val roleId: Int,
    val username: String
)