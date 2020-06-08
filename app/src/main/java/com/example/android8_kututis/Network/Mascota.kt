package com.example.android8_kututis.Network

import com.google.gson.annotations.SerializedName

class Mascota (
    @SerializedName("id_mascota")
    val idMascota: Int,
    val nombre: String,
    val imagen:String,
    @SerializedName("Paciente_id_paciente")
    val idPaciente:Int
)