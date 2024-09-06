package com.example.aplikasiobat.api.response.dashboard.detailObatPasien


import com.google.gson.annotations.SerializedName

data class DetailObatPasienResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)