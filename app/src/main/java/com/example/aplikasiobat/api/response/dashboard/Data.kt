package com.example.aplikasiobat.api.response.dashboard


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("dosisObat")
    val dosisObat: String,
    @SerializedName("namaObat")
    val namaObat: String,
    @SerializedName("waktuMinumObat")
    val waktuMinumObat: String,
    @SerializedName("catatan")
    val catatan:String,
    @SerializedName("sudahMinumObat")
    val sudahMinumObat:String?=null

)