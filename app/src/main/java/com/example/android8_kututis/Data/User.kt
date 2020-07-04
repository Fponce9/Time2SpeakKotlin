package com.example.android8_kututis.Data

import com.google.gson.annotations.SerializedName

class User(
    var apellido: String,
    var contrasena: String,
    var correo: String,
    var deshabilitado: Boolean,
    var doctorId: Int,
    @SerializedName("fecha_nacimiento")
    var fechaNacimiento : String,
    @SerializedName("id_paciente")
    var idPaciente: Int,
    var nombre: String
)