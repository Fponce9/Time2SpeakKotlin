package com.example.android8_kututis

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.android.synthetic.main.activity_letras_terapia.*


class LetrasTerapia : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_letras_terapia)

        val acct = GoogleSignIn.getLastSignedInAccount(this)
        /*if (acct != null) {
            val personName = acct.displayName
            val personEmail = acct.email
            val personId = acct.id
            val personPhoto: Uri? = acct.photoUrl
            tvNombre.text = personName
            tvEmail.text = personEmail
            /*tvId.text = personId */
        }*/
        val per = intent.getStringExtra("idPcienteinit")
        tvNombre.text=per

    }
}
