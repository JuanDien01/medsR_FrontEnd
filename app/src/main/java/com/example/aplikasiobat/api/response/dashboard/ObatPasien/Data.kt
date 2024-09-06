package com.example.aplikasiobat.api.response.dashboard.ObatPasien


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    @SerializedName("idObatPasien")
    val idObatPasien:Int,
    @SerializedName("idObat")
    val idObat:Int,
    @SerializedName("idUser")
    val idUser:Int,
    @SerializedName("dosisObat")
    val dosisObat: String,
    @SerializedName("namaObat")
    val namaObat: String,
    @SerializedName("aturanPenggunaanObat")
    val aturanPenggunaanObat: String,
    @SerializedName("waktuMulaiMinumObat")
    val waktuMulaiMinumObat: String,
    @SerializedName("waktuSelesaiMinumObat")
    val waktuSelesaiMinumObat: String,
    @SerializedName("tanggalDiberikan")
    val tanggalDiberikan: String,
    @SerializedName("catatan")
    val catatan:String,
    @SerializedName("sudahMinumObat")
    val sudahMinumObat:String?=null
): Parcelable