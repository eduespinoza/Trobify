package com.example.trobify.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.trobify.models.DataInmueble
import com.example.trobify.R
import com.example.trobify.models.DataInmueble2
import com.example.trobify.models.Item
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference
import java.time.Duration
import java.time.LocalDateTime

class AdaptadorInmuebleBusqueda(private val fichainmueble : ArrayList<DataInmueble2>, private val favs : ArrayList<String>,
                                val itemClickListener : OnItemClickListener
)
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
        fun bind(inmueble : DataInmueble2, clickListener : OnItemClickListener){
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
        if(tipo.equals("Vivienda") || tipo.equals("Edificio")){tipo = fichainmueble[position].tipoVivienda}
        holder.ftitulo.text = tipo +" en: " + (fichainmueble[position].direccion?.titulo)

        //------------------------
        //FOTO DEL INMUEBLE EN MAIN
        val path = fichainmueble[position].fotos[0].substring(31)
        val storage = FirebaseStorage.getInstance().reference
        val one_mega = 1024*1024
        val ref = storage.child(path)
        ref.getBytes(one_mega.toLong()).addOnCompleteListener {
            var bitmap = BitmapFactory.decodeByteArray(it.result,0,it.result.size)
            holder.fimage.setImageBitmap(bitmap)
        }
        //FOTO DEL INMUEBLE EN MAIN
        //------------------------

        holder.fdescripcion.text = fichainmueble[position].descripcion
        holder.fprecio.text = fichainmueble[position].precio.toString() + "€"
        if(fichainmueble[position].numHabitaciones == 0){
            holder.fhabitaciones.visibility = View.GONE
        }else {holder.fhabitaciones.text = fichainmueble[position].numHabitaciones.toString()}
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
        fun onItemClicked(dataInmueble : DataInmueble2)
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