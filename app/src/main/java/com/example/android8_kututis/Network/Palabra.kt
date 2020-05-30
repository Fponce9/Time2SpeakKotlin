package com.example.android8_kututis.Network

import com.google.gson.annotations.SerializedName

class Palabra (
    @SerializedName("id_palabra")
    val idPalabra: Int,
    val palabra: String,
    val imagen: String,
    val terapiaId : String
)