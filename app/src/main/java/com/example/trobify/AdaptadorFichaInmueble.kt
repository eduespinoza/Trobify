package com.example.trobify

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.synnapps.carouselview.CarouselView

class AdaptadorFichaInmueble() : AppCompatActivity() {

    //Si se quiere pasar un inmueble hay que pasarlo con el putextra al intent de la siguiente forma
    //goFicha.putExtra("inmueble", elinmueble)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ficha_inmueble)
        actionBar?.hide()

        //var user = intent.extras?.get("usuario") as User
        var inmueble = intent.extras?.get("inmueble") as Inmueble
        var fotos = inmueble.getfotos()
        var fotosOrd = inmueble.getfotosord()

        val buttonAtras = findViewById<Button>(R.id.buttonAtrasFicha)
        buttonAtras.setOnClickListener{
            val goMain = Intent(this, MainTrobify::class.java)
            startActivity(goMain)
        }

        val buttonLlamar = findViewById<Button>(R.id.buttonLlamar)
        buttonLlamar.setOnClickListener{
            llamadaMessage(inmueble)
        }

        val buttonContactar = findViewById<Button>(R.id.buttonContactarFicha)
            buttonContactar.setOnClickListener {
            val goContactar = Intent(this, Chat::class.java)
            startActivity(goContactar)
        }

        val buttonFav = findViewById<Button>(R.id.buttonFav)
        buttonFav.setOnClickListener {
            //addToFav(user, inmueble)
        }



        rellenar(inmueble)

        val carouselView = findViewById<CarouselView>(R.id.carouselView)
        carouselView.setImageListener{ position, imageView ->
            if (fotos != null) {
                imageView.setImageResource(fotos.get(position))
            }
        }


        carouselView.setImageClickListener{ position ->
            Toast.makeText(applicationContext, fotosOrd?.get(position), Toast.LENGTH_SHORT).show()
        }
        carouselView.pageCount = fotosOrd.size


    }



    private fun rellenar(inmueble : Inmueble) {

        val direccion =  findViewById<TextView>(R.id.textViewCalleFicha)
        direccion.text = inmueble?.titulo
        direccion.setTextColor(Color.BLACK)
        direccion.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40F)

        val precio = findViewById<TextView>(R.id.textViewPrecioFicha)
        precio.text = inmueble?.precio.toString()+ " €"
        precio.setTextColor(Color.BLACK)
        precio.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40F)

        val numHabitaciones =  findViewById<TextView>(R.id.textViewHabitacionesFicha)
        numHabitaciones.text = inmueble?.numHabitaciones.toString() + " habitaciones"
        numHabitaciones.setTextColor(Color.BLACK)
        numHabitaciones.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40F)

        val numBanos = findViewById<TextView>(R.id.textViewBanosFicha)
        numBanos.text = inmueble?.numBanos.toString() + " baños"
        numBanos.setTextColor(Color.BLACK)
        numBanos.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40F)

        val superficie = findViewById<TextView>(R.id.textViewSuperficieFicha)
        superficie.text = inmueble?.superficie.toString() + "m2"
        superficie.setTextColor(Color.BLACK)
        superficie.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40F)

        val descripcion = findViewById<TextView>(R.id.textViewDescripcionFicha)
        descripcion.text = inmueble?.descripcion.toString()
        descripcion.setTextColor(Color.BLACK)
        descripcion.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40F)

        val caracteristicas = findViewById<TextView>(R.id.textViewCaracteristicasFicha)
        caracteristicas.text = inmueble?.caracteristicas
        caracteristicas.setTextColor(Color.BLACK)
        caracteristicas.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40F)

        val name = findViewById<TextView>(R.id.textViewPropietarioFicha)
        name .text = inmueble?.propietario?.getName().toString()
        name.setTextColor(Color.BLACK)
        name.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40F)




    }

    private fun llamadaMessage(inmueble : Inmueble) {
        val builder =  AlertDialog.Builder(this)
        val numero = inmueble?.propietario?.getPhone()
        builder.setTitle("Contacto")
        builder.setMessage("El numero del propietarios es:  " + numero)
        builder.setIcon(android.R.drawable.ic_menu_call)
        builder.setNeutralButton("  Continuar  "){ _, _ -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun addToFav(mainUser : User, inmueble : Inmueble){
        mainUser.addInmuebleToFav(inmueble)
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