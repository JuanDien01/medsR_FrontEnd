package com.example.aplikasiobat.api.response.settings.UpdatePassword


import com.google.gson.annotations.SerializedName

data class UpdatePasswordResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)