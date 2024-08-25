package com.example.aplikasiobat.api.response.register.userbiodata

data class RegisterUserBiodataResponse(
    val alamat: String,
    val email: String,
    val jenisKelamin: String,
    val namaPengguna: String,
    val nomorTelfon: String,
    val tanggalLahir: String
)