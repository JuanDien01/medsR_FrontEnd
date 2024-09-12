package com.example.aplikasiobat.api.response.auth.forgotpassword


import com.google.gson.annotations.SerializedName

data class ForgotPasswordResponse(
    @SerializedName("data")
    val `data`: Any,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)