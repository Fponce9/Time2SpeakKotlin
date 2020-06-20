package com.example.android8_kututis

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android8_kututis.Network.KututisApi
import com.example.android8_kututis.Network.Letra
import kotlinx.android.synthetic.main.activity_letras_terapia.*
import kotlinx.android.synthetic.main.letras_terapia_row.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LetrasTerapia : AppCompatActivity() {

    private var globalClass: GlobalClass? = null
    var contador: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_letras_terapia)
        val idPaciente = intent.getIntExtra("idPaciente",0)

        globalClass = applicationContext as GlobalClass

        contador = globalClass!!.contador_grabaciones!!

        /* val acct = GoogleSignIn.getLastSignedInAccount(this)
         if (acct != null) {
             val personName = acct.displayName
             val personEmail = acct.email
             val personId = acct.id
             val personPhoto: Uri? = acct.photoUrl
         }*/
        rv_LetrasTerapias.layoutManager = LinearLayoutManager(this)
        fetchLetras()
        updateBarra(contador!!)
        bt_mascota.setOnClickListener {
            val MascotaIntent = Intent(this,Mascota::class.java)
            MascotaIntent.putExtra("idPaciente",idPaciente)
            startActivity(MascotaIntent)
        }
        bt_medallas.setOnClickListener {
            val MedallasIntent = Intent(this,Medallas::class.java)
            MedallasIntent.putExtra("idPaciente",idPaciente)
            startActivity(MedallasIntent)
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

    fun updateBarra(contador: Int) {
        val barraSprites =
            BitmapFactory.decodeResource(resources, R.drawable.sprite_barra)
        val barra =
            Bitmap.createBitmap(barraSprites, 1046 * contador, 0, 1046, barraSprites.height)
        imageViewBarra.setImageBitmap(getResizedBitmap(barra, 400, 100))
    }


    fun getResizedBitmap(bm: Bitmap, newWidth: Int, newHeight: Int): Bitmap? {
        val width = bm.width
        val height = bm.height
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        // CREATE A MATRIX FOR THE MANIPULATION
        val matrix = Matrix()
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight)

        // "RECREATE" THE NEW BITMAP
        val resizedBitmap = Bitmap.createBitmap(
            bm, 0, 0, width, height, matrix, false
        )
        bm.recycle()
        return resizedBitmap
    }
}
