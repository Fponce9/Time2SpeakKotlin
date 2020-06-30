package com.example.android8_kututis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.graphics.drawable.DrawableCompat
import com.example.android8_kututis.Network.KututisApi
import com.example.android8_kututis.Network.User
import kotlinx.android.synthetic.main.activity_editar_perfil.*
import kotlinx.android.synthetic.main.activity_registro_paciente.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EditarPerfil : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_perfil)
        filldata()
        et_ConfContraEdit.isEnabled=false
        et_ContraEditar.isEnabled=false
        etEmailEdit.isEnabled=false
        etNacimientoEdit.isEnabled=false
        etNombreEdit.isEnabled=false
        etApellidoEdit.isEnabled=false
        im_save.isClickable=false
        var save=false
        var edit=true
        im_edit.isClickable=true
        im_edit.setImageResource(R.drawable.editar)
        im_edit.setOnClickListener {
            if (save==false){
                im_save.isClickable=true
                im_edit.isClickable=false
                et_ConfContraEdit.isEnabled=true
                et_ContraEditar.isEnabled=true
                etEmailEdit.isEnabled=true
                save=true
                edit=false
                im_save.setImageResource(R.drawable.guardar)
                im_edit.setImageResource(R.drawable.editoff)
            }
         }
        im_save.setOnClickListener {
            if (edit==false){
                im_edit.isClickable=true
                et_ConfContraEdit.isEnabled=false
                et_ContraEditar.isEnabled=false
                etEmailEdit.isEnabled=false
                edit=true
                save=false
                update()
                im_save.setImageResource(R.drawable.saveoff)
                im_edit.setImageResource(R.drawable.editar)
            }
        }
    }
    fun filldata(){
        etNombreEdit.setText(usuario.nombre)
        etApellidoEdit.setText(usuario.apellido)
        etNacimientoEdit.setText(usuario.fechaNacimiento)
        etEmailEdit.setText(usuario.correo)

    }
    fun update(){
        val vacio = ""
        var userToUpdate= usuario
        userToUpdate.correo=etEmailEdit.text.toString().trim()
        if(et_ContraEditar.text.trim()==et_ConfContraEdit.text.trim()){
            if(et_ContraEditar.text.toString() != vacio )
            userToUpdate.contrasena=et_ContraEditar.text.toString().trim()
        }
        if(et_ContraEditar.text.trim() !=et_ConfContraEdit.text.trim()){
            Toast.makeText(applicationContext,"Las contraseñas no coinciden",Toast.LENGTH_LONG).show()
        }
        if (switch1.isChecked()){
            deshabilitar(userToUpdate.idPaciente)
        }

        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("http://time2speak-env.eba-mitec5md.us-east-1.elasticbeanstalk.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val KututisApi = retrofitBuilder.create(KututisApi::class.java)
        val request = KututisApi.putActualizarPaciente(userToUpdate)
        request.enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(applicationContext,"Ha ocurrido un error al crear el usuario",Toast.LENGTH_LONG).show()
                Log.d("RegistroPaciente",t.toString())
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                Toast.makeText(applicationContext,"Se ha actualizado correctamente.",Toast.LENGTH_LONG).show()

            }

        })

    }
    fun deshabilitar(id:Int){
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("http://time2speak-env.eba-mitec5md.us-east-1.elasticbeanstalk.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val KututisApi = retrofitBuilder.create(KututisApi::class.java)
        val request = KututisApi.createdDeshabilitar(id)
        request.enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(applicationContext,"Ha ocurrido un error deshabilitar usuario",Toast.LENGTH_LONG).show()
                Log.d("EditarPaciente",t.toString())
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                Toast.makeText(applicationContext,"Se ha deshabilitado correctamente.",Toast.LENGTH_LONG).show()

            }

        })
    }
}