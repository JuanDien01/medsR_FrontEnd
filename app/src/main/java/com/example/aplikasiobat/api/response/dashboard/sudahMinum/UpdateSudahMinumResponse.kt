package com.example.aplikasiobat.api.response.dashboard.sudahMinum


import com.google.gson.annotations.SerializedName

data class UpdateSudahMinumResponse(
    @SerializedName("data")
    val `data`: Any,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)