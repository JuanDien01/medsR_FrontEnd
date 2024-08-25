package com.example.aplikasiobat.api.request

data class RegisterUserBiodataRequest(
    val alamat: String,
    val email: String,
    val jenisKelamin: String,
    val namaPengguna: String,
    val nomorTelfon: String,
    val tanggalLahir: String
)