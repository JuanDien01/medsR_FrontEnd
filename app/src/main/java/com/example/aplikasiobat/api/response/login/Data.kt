package com.example.aplikasiobat.api.response.login

data class Data(
    val role: Role,
    val token: String,
    val userId: Int,
    val idPengguna : Int,
    val userName: String,
    val fullName:String
)