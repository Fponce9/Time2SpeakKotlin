package com.example.android8_kututis.Network

import retrofit2.Call
import retrofit2.http.*


internal interface KututisApi {
    @GET("inicioSesion/{correo}/{contrasena}")
    fun getUserData(@Path("correo") correo: String, @Path("contrasena") contrasena: String): Call<User>


    @GET("obtenerTerapia/{paciente}/{letra}")
    fun getFeedbackData(@Path("paciente")paciente:Int, @Path("letra") letra:String): Call<Feedback>

    @POST("registrarPaciente")
    fun createUser(@Body usuario:UserPost):Call<User>
}
