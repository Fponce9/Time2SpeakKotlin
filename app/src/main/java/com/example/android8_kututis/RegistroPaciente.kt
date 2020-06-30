package com.example.android8_kututis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.android8_kututis.Network.KututisApi
import com.example.android8_kututis.Network.Mascota
import com.example.android8_kututis.Network.MascotaPost
import com.example.android8_kututis.Network.User
import com.example.android8_kututis.Network.UserPost
import kotlinx.android.synthetic.main.activity_registro_paciente.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegistroPaciente : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_paciente)

        btnRegistrarme.setOnClickListener{
            Registrar()
        }
    }
    private fun Registrar(){
        val nombre = etNombreRegistro.text.toString().trim()
        val apellido = etApellidoRegistro.text.toString().trim()
        val email = etEmailRegistro.text.toString().trim()
        val contrasena = etContrasenaRegistro.text.toString().trim()
        val nacimiento = etNacimientoRegistro.text.toString().trim()

        val usuario = UserPost(apellido,contrasena,email,false,1,nacimiento,nombre)

        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("http://time2speak-env.eba-mitec5md.us-east-1.elasticbeanstalk.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val KututisApi = retrofitBuilder.create(KututisApi::class.java)
        val request = KututisApi.createUser(usuario)
        request.enqueue(object : Callback<User>{
            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(applicationContext,"Ha ocurrido un error al crear el usuario",Toast.LENGTH_LONG).show()
                Log.d("RegistroPaciente",t.toString())
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                val newPacienteId=response.body()!!.idPaciente
                Toast.makeText(applicationContext,"Se ha registrado correctamente.",Toast.LENGTH_LONG).show()
                AsignarMascota(newPacienteId)
                CrearMain()
            }

        })
    }

    private fun CrearMain(){
        val MainIntent = Intent(this, MainActivity::class.java)
        startActivity(MainIntent)
    }
    private fun AsignarMascota(pacienteId:Int){
        var mascota:MascotaPost = MascotaPost("0","Kutur",pacienteId)
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("http://time2speak-env.eba-mitec5md.us-east-1.elasticbeanstalk.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val KututisApi = retrofitBuilder.create(KututisApi::class.java)
        val request = KututisApi.createMascota(mascota)
        request.enqueue(object : Callback<Mascota>{
            override fun onFailure(call: Call<Mascota>, t: Throwable) {
                Toast.makeText(applicationContext,"Ha ocurrido un error al crear la mascota",Toast.LENGTH_LONG).show()
                Log.d("RegistroPaciente",t.toString())
            }

            override fun onResponse(call: Call<Mascota>, response: Response<Mascota>) {
                Toast.makeText(applicationContext,"Se ha registrado correctamente.",Toast.LENGTH_LONG).show()
            }

        })

    }
}


