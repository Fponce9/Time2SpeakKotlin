package com.example.android8_kututis.Network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


internal interface KututisApi {
    @GET("inicioSesion/{correo}/{contrasena}")
    fun getUserData(@Path("correo") correo: String, @Path("contrasena") contrasena: String): Call<User>
}
