package com.example.android8_kututis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.android8_kututis.Data.KututisApi
import com.example.android8_kututis.Data.Mascota
import kotlinx.android.synthetic.main.activity_mascota.*
import kotlinx.android.synthetic.main.activity_medallas.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
var estado =1
var alimento=7

class Mascota : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mascota)
        val idPaciente = intent.getIntExtra("idPaciente",0)
        fecthMascota(idPaciente)
        tvalimento.text= estado.toString()+"/10"
        tv_alimentos.text="Te quedan "+ alimento.toString()+" alimentos"

        btn_alimentar.setOnClickListener {
            if(estado<10){
                if(alimento>0){
                    estado= estado+1
                    alimento=alimento-1
                    tvalimento.text= estado.toString()+"/10"
                    tv_alimentos.text="Te quedan "+ alimento.toString()+" alimentos"
                    mensaje()
                }
            }
        }



    }

    fun fecthMascota(idPaciente:Int){
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("http://time2speak-env.eba-mitec5md.us-east-1.elasticbeanstalk.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val KututisApi = retrofitBuilder.create(KututisApi::class.java)
        val request = KututisApi.getMascotaPaciente(idPaciente)

        request.enqueue(object : Callback<Mascota> {
            override fun onFailure(call: Call<Mascota>, t: Throwable) {
                Toast.makeText(applicationContext,"No se encontro mascota.", Toast.LENGTH_LONG).show()
                Log.d("Mascota",t.toString())
            }

            override fun onResponse(call: Call<Mascota>, response: Response<Mascota>) {
                tvNombreMascota.text=response.body()!!.nombre
                mensaje()

            }
        })
    }

    fun mensaje(){
        if(estado >5&& estado<10){
            imMascota.setImageResource(R.drawable.a_f)
            val m = getString(R.string.mensajef)
            tvMensajeMascota.text= tvNombreMascota.text.toString()+" "+getString(R.string.mensajef)
            tvalimento.setTextColor(getColor(R.color.saludbuena))
        }
        if(estado >3&& estado<6){
            imMascota.setImageResource(R.drawable.a_t)
            tvMensajeMascota.text= tvNombreMascota.text.toString() +" "+ getString(R.string.mensajet)
            tvalimento.setTextColor(getColor(R.color.saludmedia))
        }
        if(estado >2&& estado<4){
            imMascota.setImageResource(R.drawable.a_l)
            tvMensajeMascota.text= tvNombreMascota.text.toString() + " "+getString(R.string.mensajel)
            tvalimento.setTextColor(getColor(R.color.saludmedia))
        }
        if(estado <=1){
            imMascota.setImageResource(R.drawable.a_m)
            tvMensajeMascota.text= tvNombreMascota.text.toString() + " "+ getString(R.string.mensajem)
            tvalimento.setTextColor(getColor(R.color.saludmala))
        }
    }

}