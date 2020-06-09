package com.example.android8_kututis.Network

import com.google.gson.annotations.SerializedName

class Premio(
    @SerializedName("id_premio")
    val idpremio : String,
    val medalla : String,
    @SerializedName("nombre_premio")
    val descripcionterapia: String,
    @SerializedName("pacienteId")
    val pacienteid: Int
)