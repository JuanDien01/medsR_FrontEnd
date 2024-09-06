package com.example.aplikasiobat.api.response.dashboard.ObatPasien


import com.google.gson.annotations.SerializedName

data class GetObatPasienResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)