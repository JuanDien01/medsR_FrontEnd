package com.example.aplikasiobat.api.request


import com.google.gson.annotations.SerializedName

data class DetailObatPasienRequest(
    @SerializedName("idObat")
    val idObat: Int,
    @SerializedName("idUser")
    val idUser: Int
)