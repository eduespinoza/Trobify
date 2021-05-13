package com.example.trobify

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.trobify.models.TipoInmueble

class pantalla_inicial : AppCompatActivity() {

    var alquilerActivado = false
    var ventaActivado = false
    lateinit var colorDefaultText : ColorStateList
    
    @SuppressLint("StaticFieldLeak")
    object busquedaInicial{
        lateinit var desplegableTipoInmueble:Spinner
        lateinit var alquiler:TextView
        lateinit var venta:TextView
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.pantalla_inicio)
        super.onCreate(savedInstanceState)

        busquedaInicial.alquiler = findViewById(R.id.text_opcion_alquilar)
        busquedaInicial.venta = findViewById(R.id.text_opcion_comprar)
        colorDefaultText = busquedaInicial.venta.textColors
        busquedaInicial.alquiler.setOnClickListener {
            if(alquilerActivado)desactivarIntenciones(busquedaInicial.alquiler,busquedaInicial.venta)
            else activalAlquiler(busquedaInicial.alquiler, busquedaInicial.venta)
        }
        busquedaInicial.venta.setOnClickListener {
            if(ventaActivado)desactivarIntenciones(busquedaInicial.alquiler,busquedaInicial.venta)
            else activarVenta(busquedaInicial.alquiler, busquedaInicial.venta)
        }

        busquedaInicial.desplegableTipoInmueble = findViewById<Spinner>(R.id.spinner_tipoInmueble_inicio)
        var botonBuscar = findViewById<Button>(R.id.buttonBusquedaInicial)
        botonBuscar.setOnClickListener {
            busquedaInicial()
        }
    }
    private fun busquedaInicial(){

    }
    private fun addOptionsInmueble(){
        val options_inmueble = TipoInmueble.values()
        val adapter = ArrayAdapter<TipoInmueble>(this, android.R.layout.simple_spinner_item, options_inmueble)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        busquedaInicial.desplegableTipoInmueble.adapter = adapter
    }
    private fun desactivarIntenciones(alquiler : TextView, venta : TextView){
        alquilerActivado = false
        ventaActivado = false
        alquiler.setBackgroundColor(Color.WHITE)
        alquiler.setTextColor(colorDefaultText)
        venta.setTextColor(colorDefaultText)
        venta.setBackgroundColor(Color.WHITE)
        //mostrarInmuebles(inmuebles)
    }
    private fun activalAlquiler(alquiler : TextView, venta : TextView){
        alquilerActivado = true
        ventaActivado = false
        alquiler.setBackgroundColor(Color.BLUE)
        alquiler.setTextColor(Color.WHITE)
        venta.setTextColor(Color.BLACK)
        venta.setBackgroundColor(Color.GRAY)
        //alquilerOVenta("Alquiler")
    }
    private fun activarVenta(alquiler : TextView, venta : TextView){
        alquilerActivado = false
        ventaActivado = true
        venta.setBackgroundColor(Color.BLUE)
        venta.setTextColor(Color.WHITE)
        alquiler.setTextColor(Color.BLACK)
        alquiler.setBackgroundColor(Color.GRAY)
        //alquilerOVenta("Vender")
    }
}