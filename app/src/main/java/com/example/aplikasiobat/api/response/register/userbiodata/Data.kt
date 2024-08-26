package com.example.aplikasiobat.api.response.register.userbiodata


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("idPengguna")
    val idPengguna: Int,
    @SerializedName("namaPengguna")
    val namaPengguna: String
)