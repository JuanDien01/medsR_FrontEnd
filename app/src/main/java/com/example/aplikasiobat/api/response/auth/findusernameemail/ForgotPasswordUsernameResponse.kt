package com.example.aplikasiobat.api.response.auth.findusernameemail


import com.google.gson.annotations.SerializedName

data class ForgotPasswordUsernameResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)