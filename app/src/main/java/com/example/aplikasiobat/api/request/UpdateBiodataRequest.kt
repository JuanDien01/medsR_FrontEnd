package com.example.aplikasiobat.api.request


import com.google.gson.annotations.SerializedName

data class UpdateBiodataRequest(
    @SerializedName("id")
    val id: Int,
    @SerializedName("alamat")
    val alamat: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("jenisKelamin")
    val jenisKelamin: String,
    @SerializedName("namaPengguna")
    val namaPengguna: String,
    @SerializedName("nomorTelfon")
    val nomorTelfon: String,
    @SerializedName("tanggalLahir")
    val tanggalLahir: String
)