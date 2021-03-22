package com.example.trobify

import android.content.ContentValues.TAG
import android.content.ContextWrapper
import android.content.Intent
import android.util.Log
import android.widget.CheckBox
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.grpc.Context


class Inmueble{
    var titulo : String? = null
    var id : String? = null
    var propietario : User? = null
    var numHabitaciones : Int? = null
    var numBanos : Int? = null
    var superficie : Int? = null

    var direccion : Direccion? = null
    var tipoInmueble : String? = null
    var intencion : String? = null //vender o alquilar
    var precio : Int? = null

    //var photos : ArryList<>  //cambiar tipo a img
    var certificadoEnergetico : String? = null  //Desde la A hasta la G, hay que hacer que solo deje poner las letras posibles
    var descripcion : String? = null


    var estado : String? = null

    var parking : Boolean? = null
    var ascensor : Boolean? = null
    var amueblado : Boolean? = null
    var calefaccion : Boolean? = null
    var jardin : Boolean? = null
    var piscina : Boolean? = null
    var terraza : Boolean? = null
    var trastero : Boolean? = null

    var caracteristicas : String? = null

    constructor( title: String,   price: Int,  surface: Int,  photos: Array<Int>){
        this.titulo = "Inmueble en " + title
        this.precio = price
        this.superficie = surface
    }

    constructor(
        id : String?,
        propietario : User?,
        numHabitaciones : Int?,
        numBanos : Int?,
        superficie : Int?,

        direccion : Direccion?,
        tipoInmueble : String?,
        intencion : String?,
        precio : Int?,

        certificadoEnergetico : String?,
        descripcion : String?,

        estado : String?,

        parking : Boolean?,
        ascensor : Boolean?,
        amueblado : Boolean?,
        calefaccion : Boolean?,
        jardin : Boolean?,
        piscina : Boolean?,
        terraza : Boolean?,
        trastero : Boolean?
    ) {

        this.id = id
        this.propietario = propietario
        this.numHabitaciones = numHabitaciones
        this.numBanos = numBanos
        this.superficie = superficie
        this.direccion = direccion
        this.tipoInmueble = tipoInmueble
        this.intencion = intencion
        this.precio = precio
        this.certificadoEnergetico = certificadoEnergetico
        this.descripcion = descripcion
        this.estado = estado
        this.parking = parking
        this.ascensor = ascensor
        this.amueblado = amueblado
        this.calefaccion = calefaccion
        this.jardin = jardin
        this.piscina = piscina
        this.terraza = terraza
        this.trastero = trastero
        this.caracteristicas = caracteristicasToString()

        if (direccion != null) {
            this.titulo = "Inmueble en " + direccion.direccionToString()
        }
    }

    fun caracteristicasToString( ) : String {
        var res = "El piso dispone de "

        if(parking == true){ res += "-parking " }
        if(ascensor == true){ res += "-ascensor " }
        if(amueblado == true){ res += "-amueblado " }
        if(calefaccion == true){ res += "-calefaccion " }
        if(jardin == true){ res += "-jardin " }
        if(piscina == true){ res += "-piscina " }
        if(terraza == true){ res += "-terraza " }
        if(trastero == true){ res += "-trastero " }

        return res
    }





}
//db.collection("inmuebles").document("tipo de inmueble")
//.set(docData)
//.addOnSuccessListener { Log.d(TAG, "Inmueble guardado correctamente!") }
//.addOnFailureListener { e -> Log.w(TAG, "Error al guardar inmueble", e) }