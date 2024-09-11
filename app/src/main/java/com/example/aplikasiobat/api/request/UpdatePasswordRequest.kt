package com.example.aplikasiobat.api.request


import com.google.gson.annotations.SerializedName

data class UpdatePasswordRequest(
    @SerializedName("newPassword")
    val newPassword: String,
    @SerializedName("oldPassword")
    val oldPassword: String,
    @SerializedName("username")
    val username: String
)