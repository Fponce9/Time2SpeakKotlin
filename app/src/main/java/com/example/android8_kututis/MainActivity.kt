package com.example.android8_kututis

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android8_kututis.Network.KututisApi
import com.example.android8_kututis.Network.User
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

var usuario : User = User("","","",false,0,"",0,"")

class MainActivity : AppCompatActivity() {

    var RC_SIGN_IN = 120
    private lateinit var mAuth: FirebaseAuth
    private lateinit var  googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("748192856271-ur6lidgh4dderfovp177kmbqa0al8941.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient= GoogleSignIn.getClient(this, gso);

        mAuth = FirebaseAuth.getInstance()

        btnGoogle.setOnClickListener {
            val signInIntent: Intent = googleSignInClient.signInIntent
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

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if (task.isSuccessful){
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("SignInActivity", "firebaseAuthWithGoogle: "+ account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                }catch (e:ApiException){
                    Log.w("SignInActivity", "Google sign in Failed",e)
                }
            }else{
                Log.w("SignInActivity OnActivityResult", exception.toString())
            }
            //handleSignInResult(task)
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken,null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this){task ->
                if(task.isSuccessful){
                    Log.d("SignInActivity","Sign in success")
                    val user = mAuth.currentUser
                    val TerapiaIntent =Intent(this,LetrasTerapia::class.java)
                    startActivity(TerapiaIntent)
                }else{
                    Log.w("SignInActivity","Sign in Failure", task.exception)

                }
            }
    }


    private  fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            val TerapiaIntent =Intent(this,LetrasTerapia::class.java)
            startActivity(TerapiaIntent)
        } catch (e: ApiException) {
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
            startActivity(TerapiaIntent)
        }
    }
}
