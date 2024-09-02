package com.example.aplikasiobat.api.response.dashboard

data class Data(
    val aturanPenggunaanObat: String,
    val dosisObat: String,
    val id: Int,
    val idObat: Int,
    val tanggalDiberikan: String,
    val userId: Int,
    val waktuMulaiMinumObat: String,
    val waktuSelesaiMinumObat: String
)