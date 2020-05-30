package com.example.android8_kututis

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android8_kututis.Network.Palabra
import kotlinx.android.synthetic.main.letras_terapia_row.view.*
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
    }

}

class PalabrasViewHolder(val view: View):RecyclerView.ViewHolder(view){

}