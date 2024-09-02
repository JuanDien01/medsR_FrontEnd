package com.example.aplikasiobat.api.response.dashboard

data class GetObatPasienResponse(
    val `data`: List<Data>,
    val message: String,
    val status: String
)