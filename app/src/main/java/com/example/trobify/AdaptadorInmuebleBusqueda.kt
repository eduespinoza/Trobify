package com.example.trobify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
class AdaptadorInmuebleBusqueda (private val fichainmueble : ArrayList<InmuebleTest>)
    : RecyclerView.Adapter<AdaptadorInmuebleBusqueda.ViewHolder>() {

    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view){
        var fimage = view.findViewById<ImageView>(R.id.imageinmueble)
        var ftitulo = view.findViewById<TextView>(R.id.titulo)
        var fprecio = view.findViewById<TextView>(R.id.precio)
        var fsuperficie = view.findViewById<TextView>(R.id.superficie)
        var fhabitaciones = view.findViewById<TextView>(R.id.habitaciones)
        var fdescripcion = view.findViewById<TextView>(R.id.descripcion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewcard = LayoutInflater.from(parent.context)
            .inflate(R.layout.inmueble_card_busqueda, parent, false)
        return ViewHolder(viewcard)
    }

    override fun getItemCount(): Int {
        return fichainmueble.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ftitulo.text = fichainmueble[position].title
        holder.fimage.setImageResource(fichainmueble[position].photos.first())
        holder.fdescripcion.text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."
        holder.fprecio.text = fichainmueble[position].price.toString() + "€"
        holder.fhabitaciones.text = "5"
        holder.fsuperficie.text = fichainmueble[position].surface.toString()
        holder.itemView.setOnClickListener { v:View ->
            Toast.makeText(v.context, "Hola hermano", Toast.LENGTH_SHORT).show()
        }
    }
}