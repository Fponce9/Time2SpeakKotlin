package com.example.android8_kututis.Network

import com.google.gson.annotations.SerializedName

class User(
    val apellido: String,
    val contrasena: String,
    val correo: String,
    val deshabilitado: Boolean,
    val doctorId: Int,
    @SerializedName("fecha_nacimiento")
    val fechaNacimiento : String,
    @SerializedName("id_paciente")
    var idPaciente: Int,
    var nombre: String
)