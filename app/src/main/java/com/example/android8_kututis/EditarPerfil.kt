package com.example.android8_kututis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_editar_perfil.*

class EditarPerfil : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_perfil)
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
        im_edit.setOnClickListener {
            if (save==false){
                im_save.isClickable=true
                im_edit.isClickable=false
                et_ConfContraEdit.isEnabled=true
                et_ContraEditar.isEnabled=true
                etEmailEdit.isEnabled=true
                save=true
                edit=false
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
            }
        }
    }
}