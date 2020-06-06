package com.example.android8_kututis

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android8_kututis.Network.Palabra
import kotlinx.android.synthetic.main.palabras_letra_row.view.*

class PalabrasLetraAdapter(val Palabras:List<Palabra>):RecyclerView.Adapter<PalabrasViewHolder>() {
    override fun getItemCount(): Int{
        return Palabras.count()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PalabrasViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow= layoutInflater.inflate(R.layout.palabras_letra_row,parent,false)
        return PalabrasViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: PalabrasViewHolder, position: Int) {
        val palabrasTitle=Palabras.get(position)
        holder.view.row_palabra_id.text=palabrasTitle.palabra
        holder.palabra=palabrasTitle.palabra

        val parseo:ByteArray = palabrasTitle.imagen.toByteArray()

        val btm:Bitmap = BitmapFactory.decodeByteArray(parseo,0, parseo.size)

        holder.view.ImagenPalabra.setImageBitmap(btm)
    }

}

class PalabrasViewHolder(val view: View,var palabra:String?= null):RecyclerView.ViewHolder(view){
    init{
        view.setOnClickListener {
            val GrabacionIntent = Intent(view.context,Grabacion::class.java)
            GrabacionIntent.putExtra("palabra",palabra)
            view.context.startActivity(GrabacionIntent)
        }
    }


}