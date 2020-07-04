package com.example.android8_kututis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.android8_kututis.Data.Feedback
import com.example.android8_kututis.Data.KututisApi
import kotlinx.android.synthetic.main.activity_visualizar_feedback.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

var idUser = 0
var idLetra = ""
class VisualizarFeedback : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualizar_feedback)
        /*idUser=intent.getIntExtra("idUser",2)*/
        /*idLetra=intent.getStringExtra("idLetra")*/
        idLetra= "ts"
        idUser = 2
        GetFeedback()

    }


    private fun GetFeedback(){
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("http://time2speak-env.eba-mitec5md.us-east-1.elasticbeanstalk.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val KututisApi = retrofitBuilder.create(KututisApi::class.java)
        val request = KututisApi.getFeedbackData(idUser, idLetra)
        request.enqueue(object : Callback<Feedback> {
            override fun onFailure(call: Call<Feedback>, t: Throwable) {
                Toast.makeText(applicationContext,"Error de conexion", Toast.LENGTH_LONG).show()
                Log.d("VisualizarFeedback",t.toString())
            }

            override fun onResponse(call: Call<Feedback>, response: Response<Feedback>) {
                val coment = response.body()!!.comentarios
                val idl=response.body()!!.terapiaIdLetra
                tvComentario.text=coment
                textView3.text= idl.toString()
                val aprobado = response.body()!!.resultado
                if (aprobado) imageView3.visibility = View.VISIBLE
                if(!aprobado) imageView2.visibility =View.VISIBLE


            }
        })
    }
}
