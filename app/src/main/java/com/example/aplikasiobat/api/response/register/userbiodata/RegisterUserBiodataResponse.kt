package com.example.aplikasiobat.api.response.register.userbiodata


import com.google.gson.annotations.SerializedName

data class RegisterUserBiodataResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)