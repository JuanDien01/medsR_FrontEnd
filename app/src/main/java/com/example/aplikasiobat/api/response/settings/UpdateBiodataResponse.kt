package com.example.aplikasiobat.api.response.settings


import com.google.gson.annotations.SerializedName

data class UpdateBiodataResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)