package com.example.android8_kututis

import android.content.Intent
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android8_kututis.Network.KututisApi
import com.example.android8_kututis.Network.User
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_registro_paciente.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

var usuario : User = User("","","",false,0,"",0,"")
class MainActivity : AppCompatActivity() {

    var RC_SIGN_IN = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
         val mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        btnGoogle.setOnClickListener {
            val signInIntent: Intent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        btnInciarSesion.setOnClickListener{
            Login()


        }
        textView.setOnClickListener {
            val SignUpIntent = Intent(this, RegistroPaciente::class.java)
            startActivity(SignUpIntent)
         }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private  fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            val TerapiaIntent =Intent(this,LetrasTerapia::class.java)
            startActivity(TerapiaIntent)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error Inicio de sesion:","signInResult:failed code=" + e.statusCode)
        }
    }

    private fun Login() {

        val correo=etCorreoMain.text.toString()
        val contrasena=etContrasenaMain.text.toString()
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("http://time2speak-env.eba-mitec5md.us-east-1.elasticbeanstalk.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val KututisApi = retrofitBuilder.create(KututisApi::class.java)
        val request = KututisApi.getUserData(correo, contrasena)

        request.enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(applicationContext,"Su correo o contraseña no son correctos.",Toast.LENGTH_LONG).show()
                Log.d("MainActivity",t.toString())
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                val nombre = response.body()!!.nombre
                usuario.nombre=nombre
                usuario.idPaciente=response.body()!!.idPaciente
                Logger()

            }
        })
    }

    private fun Logger(){
        if (usuario.idPaciente != 0){
            val TerapiaIntent = Intent(this, LetrasTerapia::class.java)
            intent.putExtra("idPacienteinit", "${usuario.idPaciente}")
            startActivity(TerapiaIntent)
        }
    }
}
