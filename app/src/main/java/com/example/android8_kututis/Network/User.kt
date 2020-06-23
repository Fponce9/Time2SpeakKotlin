package com.example.android8_kututis.Network

import com.google.gson.annotations.SerializedName

class User(
    var apellido: String,
    val contrasena: String,
    var correo: String,
    val deshabilitado: Boolean,
    val doctorId: Int,
    @SerializedName("fecha_nacimiento")
    var fechaNacimiento : String,
    @SerializedName("id_paciente")
    var idPaciente: Int,
    var nombre: String
)