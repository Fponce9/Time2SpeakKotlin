package com.example.android8_kututis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android8_kututis.Network.KututisApi
import com.example.android8_kututis.Network.Letra
import com.example.android8_kututis.Network.Palabra
import kotlinx.android.synthetic.main.activity_letras_terapia.*
import kotlinx.android.synthetic.main.activity_palabras_letra.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PalabrasLetra : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_palabras_letra)
        rv_PalabrasLetras.layoutManager = LinearLayoutManager(this)
        val letra =intent.getStringExtra("letra")
        fetchPalabras(letra)
    }

    fun fetchPalabras( letra :String){
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("http://time2speak-env.eba-mitec5md.us-east-1.elasticbeanstalk.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val KututisApi = retrofitBuilder.create(KututisApi::class.java)
        val request = KututisApi.getPalabrasData(letra)
        request.enqueue(object : Callback<List<Palabra>> {
            override fun onFailure(call: Call<List<Palabra>>, t: Throwable) {
                Toast.makeText(applicationContext,"Error de conexion", Toast.LENGTH_LONG).show()
                Log.d("PalabrasLetra",t.toString())
            }

            override fun onResponse(call: Call<List<Palabra>>, response: Response<List<Palabra>>) {
                val Palabras = response.body()!!
                runOnUiThread {
                    rv_PalabrasLetras.adapter=PalabrasLetraAdapter(Palabras)

                }
            }
        })
    }


}
