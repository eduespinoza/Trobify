package com.example.trobify

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDateTime

class OfertarInmueble : AppCompatActivity() {

    private var fotos : ArrayList<Int> = arrayListOf()
    private var fotosOrd : ArrayList<String> = arrayListOf()
    private var tipoInmueble : String? = null
    private var tipoVivienda : String? = null
    private var tipoAnuncio : String? = null
    private var precioDeVenta : Int? = null
    private var superficie : Int? = null
    private var direccion : String? = null
    private var direccionO : Direccion? = null
    private var numHabitaciones : Int? = null
    private var numBanos : Int? = null
    private var certificadoEnergetico : String? = null  //Desde la A hasta la G, hay que hacer que solo deje poner las letras posibles
    private var descripcion : String? = null
    private var fechaSubida : LocalDateTime? = null
    private var estado : String? = null

    private var parking : Boolean? = null
    private var ascensor : Boolean? = null
    private var amueblado : Boolean? = null
    private var calefaccion : Boolean? = null
    private var jardin : Boolean? = null
    private var piscina : Boolean? = null
    private var terraza : Boolean? = null
    private var trastero : Boolean? = null

    private var caracteristicas : String? = null

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ofertar_inmueble)



        }
    }
