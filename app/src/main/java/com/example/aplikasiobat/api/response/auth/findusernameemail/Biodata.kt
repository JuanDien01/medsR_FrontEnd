package com.example.aplikasiobat.api.response.auth.findusernameemail


import com.google.gson.annotations.SerializedName

data class Biodata(
    @SerializedName("alamat")
    val alamat: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("idPengguna")
    val idPengguna: Int,
    @SerializedName("jenisKelamin")
    val jenisKelamin: String,
    @SerializedName("namaPengguna")
    val namaPengguna: String,
    @SerializedName("noTelfon")
    val noTelfon: String,
    @SerializedName("tanggalLahir")
    val tanggalLahir: String
)