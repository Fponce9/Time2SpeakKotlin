package com.example.android8_kututis.Data

import com.google.gson.annotations.SerializedName

class Letra (
    @SerializedName("id_letra")
    val idletra : String,
    @SerializedName("nombre_terapia")
    val nombreterapia : String,
    @SerializedName("descripcion_terapia")
    val descripcionterapia: String
)