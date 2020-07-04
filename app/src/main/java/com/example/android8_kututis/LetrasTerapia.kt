package com.example.android8_kututis

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android8_kututis.Data.KututisApi
import com.example.android8_kututis.Data.Letra
import kotlinx.android.synthetic.main.activity_letras_terapia.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LetrasTerapia : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_letras_terapia)
        val idPaciente = intent.getIntExtra("idPaciente",0)

       /* val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            val personName = acct.displayName
            val personEmail = acct.email
            val personId = acct.id
            val personPhoto: Uri? = acct.photoUrl
        }*/
        rv_LetrasTerapias.layoutManager = LinearLayoutManager(this)
        fetchLetras()

        nav_bar.selectedItemId = R.id.home
        nav_bar.setOnNavigationItemSelectedListener { item ->
           when(item.itemId){

               R.id.pet->{
                   val MascotaIntent = Intent(this,Mascota::class.java)
                   MascotaIntent.putExtra("idPaciente",idPaciente)
                   startActivity(MascotaIntent)
               }

               R.id.profile->{
                   val EditarIntent=Intent(this,EditarPerfil::class.java )
                   EditarIntent.putExtra("idPaciente",idPaciente)
                   startActivity(EditarIntent)
               }

               R.id.medal->{
                   val MedallasIntent = Intent(this,Medallas::class.java)
                   MedallasIntent.putExtra("idPaciente",idPaciente)
                   startActivity(MedallasIntent)
               }
           }
           true
        }
    }

    fun fetchLetras(){
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("http://time2speak-env.eba-mitec5md.us-east-1.elasticbeanstalk.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val KututisApi = retrofitBuilder.create(KututisApi::class.java)
        val request = KututisApi.getLetData()
        request.enqueue(object : Callback<List<Letra>> {
            override fun onFailure(call: Call<List<Letra>>, t: Throwable) {
                Toast.makeText(applicationContext,"Error de conexion", Toast.LENGTH_LONG).show()
                Log.d("LetrasTerapia",t.toString())
            }

            override fun onResponse(call: Call<List<Letra>>, response: Response<List<Letra>>) {
                val Letras = response.body()!!
                runOnUiThread {
                    rv_LetrasTerapias.adapter= LetrasTerapiaAdapter(Letras)
                }
            }
        })


    }
}
