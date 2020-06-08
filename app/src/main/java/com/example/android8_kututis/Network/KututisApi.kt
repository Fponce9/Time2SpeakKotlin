package com.example.android8_kututis.Network

import com.example.android8_kututis.LetrasTerapia
import retrofit2.Call
import retrofit2.http.*


internal interface KututisApi {
    @GET("inicioSesion/{correo}/{contrasena}")
    fun getUserData(@Path("correo") correo: String, @Path("contrasena") contrasena: String): Call<User>


    @GET("obtenerTerapia/{paciente}/{letra}")
    fun getFeedbackData(@Path("paciente")paciente:Int, @Path("letra") letra:String): Call<Feedback>

    @GET("obtenerLetras")
    fun getLetData():Call<List<Letra>>

    @GET("obtenerPalabras/{letra}")
    fun getPalabrasData(@Path ("letra")letra:String): Call<List<Palabra>>

    @POST("registrarPaciente")
    fun createUser(@Body usuario:UserPost):Call<User>

    @GET("getMascotaPaciente/{id}")
    fun getMascotaPaciente(@Path ("id")idPaciente:Int):Call<Mascota>
}
