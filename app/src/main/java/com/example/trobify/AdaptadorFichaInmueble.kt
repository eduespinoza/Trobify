package com.example.trobify

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class AdaptadorFichaInmueble(private val inmueble : Inmueble) : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ficha_inmueble)

        val buttonAtras = findViewById<Button>(R.id.buttonAtrasFicha)
        buttonAtras.setOnClickListener{
            val goMain = Intent(this, MainTrobify::class.java)
            startActivity(goMain)
        }

        val buttonLlamar = findViewById<Button>(R.id.buttonLlamar)
        buttonLlamar.setOnClickListener{
            llamadaMessage()
        }

        val buttonContactar = findViewById<Button>(R.id.buttonContactarFicha)
            buttonContactar.setOnClickListener {
            val goContactar = Intent(this, Chat::class.java)
            startActivity(goContactar)
        }

        val buttonFav = findViewById<Button>(R.id.buttonFav)
        buttonFav.setOnClickListener {
            addToFav()
        }

        rellenar()
    }



    private fun rellenar(){
        val direccion = inmueble.titulo
        findViewById<TextView>(R.id.textViewCalleFicha).text = direccion

        val precio = inmueble.precio.toString()
        findViewById<TextView>(R.id.textViewPrecioFicha).text = precio

        val numHabitaciones = inmueble.numHabitaciones.toString()
        findViewById<TextView>(R.id.textViewHabitacionesFicha).text = numHabitaciones

        val numBanos = inmueble.numBanos.toString()
        findViewById<TextView>(R.id.textViewBanosFicha).text = numBanos

        val superficie = inmueble.superficie.toString()
        findViewById<TextView>(R.id.textViewSuperficieFicha).text = superficie

        val descripcion = inmueble.descripcion.toString()
        findViewById<TextView>(R.id.textViewDescripcionFicha).text = descripcion

        val caracteristicas = inmueble.caracteristicas.toString()
        findViewById<TextView>(R.id.textViewCaracteristicasFicha).text = caracteristicas

        val name = inmueble.propietario?.getName().toString()
        findViewById<TextView>(R.id.textViewPropietarioFicha).text = name


    }

    private fun llamadaMessage() {
        val builder =  AlertDialog.Builder(this)
        val numero = inmueble.propietario?.getPhone()
        builder.setTitle("Contacto")
        builder.setMessage("El numero del propietarios es:  " + numero)
        builder.setIcon(android.R.drawable.ic_menu_call)
        builder.setNeutralButton("  Continuar  "){ _, _ -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun addToFav(){
        //mainUser.addInmuebleToFav(inmueble)
        val builder =  AlertDialog.Builder(this)
        builder.setTitle("Favoritos")
        builder.setMessage("Inmueble añadido a favoritos correctamente")
        builder.setIcon(android.R.drawable.star_on)
        builder.setNeutralButton("  Continuar  "){ _, _ -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

}