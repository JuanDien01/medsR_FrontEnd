package com.example.aplikasiobat.api.response.dashboard.detailObatPasien


import com.google.gson.annotations.SerializedName


data class Data(
    @SerializedName("aturanPenggunaanObat")
    val aturanPenggunaanObat: String,
    @SerializedName("catatan")
    val catatan: String,
    @SerializedName("dosisObat")
    val dosisObat: String,
    @SerializedName("idObat")
    val idObat: Int,
    @SerializedName("idObatPasien")
    val idObatPasien: Int,
    @SerializedName("idUser")
    val idUser: Int,
    @SerializedName("namaObat")
    val namaObat: String,
    @SerializedName("sudahMinumObat")
    val sudahMinumObat: String,
    @SerializedName("tanggalDiberikan")
    val tanggalDiberikan: String,
    @SerializedName("waktuMulaiMinumObat")
    val waktuMulaiMinumObat: String,
    @SerializedName("waktuSelesaiMinumObat")
    val waktuSelesaiMinumObat: String
)