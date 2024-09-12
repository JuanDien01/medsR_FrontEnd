package com.example.aplikasiobat.api.request


import com.google.gson.annotations.SerializedName

data class ForgotPasswordRequest(
    @SerializedName("newPassword")
    val newPassword: String,
    @SerializedName("userId")
    val userId: Int
)