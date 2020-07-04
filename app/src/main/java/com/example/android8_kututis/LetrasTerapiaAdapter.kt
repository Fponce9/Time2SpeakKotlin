package com.example.android8_kututis

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android8_kututis.Data.Letra
import kotlinx.android.synthetic.main.letras_terapia_row.view.*

class LetrasTerapiaAdapter(val Letras : List<Letra>): RecyclerView.Adapter<CustomViewHolder>() {
    override fun getItemCount(): Int {
        return Letras.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow= layoutInflater.inflate(R.layout.letras_terapia_row,parent,false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val letrasTitle=Letras.get(position)
        holder.view.row_letra_id.text=letrasTitle.nombreterapia
        holder.letra= letrasTitle.idletra
    }

}

class CustomViewHolder(val view: View,var letra:String?= null):RecyclerView.ViewHolder(view){
    init {
        view.setOnClickListener {
            val PalabraIntent = Intent(view.context, PalabrasLetra::class.java)
            PalabraIntent.putExtra("letra",letra)
            view.context.startActivity(PalabraIntent)
        }
    }

}