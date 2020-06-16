package com.example.android8_kututis.Network

import com.google.gson.annotations.SerializedName

class Premio(
    @SerializedName("id_premio")
    val idpremio : String,
    @SerializedName("nombre_premio")
    val nombrepremio: String,
    val medalla : String,
    val tiempo: String,
    @SerializedName("Paciente_id_paciente")
    val pacienteid: Int
)