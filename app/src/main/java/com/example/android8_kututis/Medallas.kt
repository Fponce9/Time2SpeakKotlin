package com.example.android8_kututis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.android8_kututis.Network.KututisApi
import com.example.android8_kututis.Network.Premio
import kotlinx.android.synthetic.main.activity_medallas.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Medallas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medallas)
        val id=2
        fetchMedalla(id)
    }

    fun fetchMedalla(idPaciente:Int){
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("http://time2speak-env.eba-mitec5md.us-east-1.elasticbeanstalk.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val KututisApi = retrofitBuilder.create(KututisApi::class.java)
        val request = KututisApi.getPremiosPaciente(idPaciente)

        request.enqueue(object : Callback<List<Premio>> {
            override fun onFailure(call: Call<List<Premio>>, t: Throwable) {
                Toast.makeText(applicationContext,"No se encontro premios.", Toast.LENGTH_LONG).show()
                Log.d("Premio",t.toString())
            }

            override fun onResponse(call: Call<List<Premio>>, response: Response<List<Premio>>) {
                val vacio:List<Premio>
                val lista=response.body()!!
                if(lista.isEmpty()){
                    reconocermedallas(lista)
                }

            }
        })
    }

    fun reconocermedallas(lista: List<Premio>){
        for (item:Premio in lista){
            if (item.nombrepremio== "uno"){
                im_uno.setImageResource(R.drawable.uno)
            }
            if (item.nombrepremio== "cinco"){
                im_cinco.setImageResource(R.drawable.cinco)

            }
            if (item.nombrepremio== "diez"){
                im_diez.setImageResource(R.drawable.diez)
            }
            if (item.nombrepremio== "boca"){
                im_boca.setImageResource(R.drawable.boca)
            }
            if (item.nombrepremio== "ojo"){
                im_ojo.setImageResource(R.drawable.ojo)
            }
            if (item.nombrepremio== "max"){
                im_max.setImageResource(R.drawable.max)
            }
        }
    }
}