package com.example.android8_kututis

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_grabacion.*
import java.io.File
import java.io.IOException
import java.util.*

class Grabacion : AppCompatActivity() {

    lateinit var mTTS:TextToSpeech
    private var mRecorder: MediaRecorder? = null
    lateinit var pathSave: String
    val REQUEST_PERMISSION_CODE = 0
    lateinit var mStorage: StorageReference
    lateinit var mProgressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grabacion)
        val palabra=intent.getStringExtra("palabra")
        tvPalabra.text=palabra
        if(!checkPermissionFromDevice())
            requestPermission()

        mTTS = TextToSpeech(applicationContext,TextToSpeech.OnInitListener {status ->
            if(status != TextToSpeech.ERROR){
                mTTS.language = Locale.getDefault()
            }
        })

        ivSpeak.setOnClickListener{
            val palabra = tvPalabra.text.toString()
            if( palabra.isBlank()){
                Toast.makeText(this,"El texto a reproducir esta vacio",Toast.LENGTH_SHORT).show()
            }
            else{
                mTTS.speak(palabra,TextToSpeech.QUEUE_FLUSH,null)
            }
        }

        ivMicrofono.setOnClickListener{
            if(checkPermissionFromDevice()){

                pathSave = Environment.getExternalStorageDirectory()
                    .absolutePath+"/"+ UUID.randomUUID().toString()+"_audio_record.3gp"
                setupMediaRecorder()
            try{
                mRecorder?.prepare()
                mRecorder?.start()
            } catch (o :IOException){
                Log.e("GRABACION ERROR", o.toString())
            }
            Toast.makeText(this,"Grabando",Toast.LENGTH_SHORT).show()
            }else{
                requestPermission()
            }
        }
        ivMicrofonoOff.setOnClickListener{
            mRecorder?.stop()
            mRecorder?.release()
            mRecorder = null
            Log.w("PATH GRABACION",pathSave)
            Toast.makeText(this,"Grabacion Parada",Toast.LENGTH_SHORT).show()
            uploadAudio()
        }

    }

    private fun uploadAudio() {
        mProgressBar
        var filepath = mStorage.child("Audio").child(UUID.randomUUID().toString()+"_audio_record.3gp")
        var uri = Uri.fromFile(File(pathSave))
        filepath.putFile(uri)
    }

    private fun setupMediaRecorder() {
        mRecorder = MediaRecorder()
        mRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        mRecorder?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mRecorder?.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB)
        mRecorder?.setOutputFile(pathSave)
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO),REQUEST_PERMISSION_CODE)
    }

    private fun checkPermissionFromDevice(): Boolean {
        val writeExternalStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val recordAudioResult = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
        return writeExternalStorage.equals(PackageManager.PERMISSION_GRANTED) and recordAudioResult.equals(PackageManager.PERMISSION_GRANTED)
    }

    override fun onPause() {
        if(mTTS.isSpeaking){
            mTTS.stop()
        }
        super.onPause()
    }

}
