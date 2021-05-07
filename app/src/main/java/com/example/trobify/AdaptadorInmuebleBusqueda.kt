package com.example.trobify

import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime

class AdaptadorInmuebleBusqueda(private val fichainmueble : ArrayList<DataInmueble>, private val favs : ArrayList<String>,
    val itemClickListener : OnItemClickListener)
    : RecyclerView.Adapter<AdaptadorInmuebleBusqueda.ViewHolder>() {

    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view){
        var fimage = view.findViewById<ImageView>(R.id.imageinmueble)
        var ftitulo = view.findViewById<TextView>(R.id.titulo)
        var fprecio = view.findViewById<TextView>(R.id.precio)
        var fsuperficie = view.findViewById<TextView>(R.id.superficie)
        var fhabitaciones = view.findViewById<TextView>(R.id.habitaciones)
        var fdescripcion = view.findViewById<TextView>(R.id.descripcion)
        var ftiempo = view.findViewById<TextView>(R.id.tiempo)
        var estrella = view.findViewById<Button>(R.id.estrellita_tarjetita)
        fun bind(inmueble : DataInmueble, clickListener : OnItemClickListener){
            view.setOnClickListener {
                clickListener.onItemClicked(inmueble)
            }
        }
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

        var tipo = fichainmueble[position].tipoInmueble
        holder.ftitulo.text = tipo +" en: " + (fichainmueble[position].direccion?.titulo)
        //holder.fimage.setImageResource(fichainmueble[position].photos.first())
        holder.fimage.setImageResource(R.drawable.piso4)
        holder.fdescripcion.text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."
        holder.fprecio.text = fichainmueble[position].precio.toString() + "€"
        holder.fhabitaciones.text = fichainmueble[position].numHabitaciones.toString()
        holder.fsuperficie.text = fichainmueble[position].superficie.toString()
        holder.ftiempo.text = getTiempoToString(fichainmueble[position].fechaSubida)
        holder.bind(fichainmueble[position],itemClickListener)
        if(favs.contains(fichainmueble[position].id)){
            holder.estrella.visibility = View.VISIBLE
        }
        else{
            holder.estrella.visibility = View.INVISIBLE
        }

    }
    interface OnItemClickListener{
        fun onItemClicked(dataInmueble : DataInmueble)
    }
    private fun getTiempoToString(fecha : String?):String{
        var date = LocalDateTime.parse(fecha)
        var hoy = LocalDateTime.now()
        if(date.toLocalDate().equals(hoy.toLocalDate())){
            return "Hace " + Duration.between(date,hoy).toHours().toString() + " h"
        }
        return "Hace " + Duration.between(date,hoy).toDays().toString() + " días"
    }
}