package com.example.android8_kututis.Data

import com.google.gson.annotations.SerializedName

class UserPost (
    val apellido: String,
    val contrasena: String,
    val correo: String,
    val deshabilitado: Boolean,
    val doctorId: Int,
    @SerializedName("fecha_nacimiento")
    val fechaNacimiento : String,
    var nombre: String
)