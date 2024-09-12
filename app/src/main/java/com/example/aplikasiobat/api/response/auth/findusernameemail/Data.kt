package com.example.aplikasiobat.api.response.auth.findusernameemail


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("biodata")
    val biodata: Biodata,
    @SerializedName("userId")
    val userId: Int
)