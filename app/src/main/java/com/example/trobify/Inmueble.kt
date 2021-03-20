package com.example.trobify

import android.content.ContentValues.TAG
import android.content.ContextWrapper
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.grpc.Context


class Inmueble{
    var id : String? = null
    var propietario : User? = null
    var numHabitaciones : Int? = null
    var numBanos : Int? = null
    var superficie : Int? = null
    var direccion : String? = null
    var tipoInmueble : String? = null
    var intencion : String? = null //vender o alquilar
    var precio : Int? = null

    //var photos : MutableList<Int>  //cambiar tipo a img
    var certificadoEnergetico : String? = null  //Desde la A hasta la G, hay que hacer que solo deje poner las letras posibles
    var descripcion : String? = null
    var titulo : String? = null //se puede quitar

    var estado : String? = null  //hay que ver esto aun para que solo deje poner los estados posibles

    //EXTRAS 0 si 1 no se pueden añadir mas o quitar los que sobran
    var caractertisicasList = mutableListOf(true, false)
    var caracteristicas : String? = null
    constructor( title: String,   price: Int,  surface: Int,  photos: Array<Int>){
        this.titulo = "Inmueble en " + title
        this.precio = price
        this.superficie = surface
    }
    constructor( id : String?, propietario : User?,  numHabitaciones : Int?, superficie : Int?,
                 direccion: String?, tipoInmueble: String?, intencion: String?, precio : Int?,
                 certificadoEnergetico : String?, descripcion : String?, titulo : String? , estado: String?  ) {
        this.id = id
        this.propietario = propietario
        this.numHabitaciones = numHabitaciones
        this.superficie = superficie
        this.direccion = direccion
        this.tipoInmueble = tipoInmueble
        this.intencion = intencion
        this.precio = precio
        this.certificadoEnergetico = certificadoEnergetico
        this.descripcion = descripcion
        this.titulo = "Inmueble en " + direccion

        this.caracteristicas = caracteristicasToString(caractertisicasList)
        this.estado = estado

    }

    fun caracteristicasToString( list : MutableList<Boolean> ) : String?{
        var res = ""

        return "hola"
    }





}
//db.collection("inmuebles").document("tipo de inmueble")
//.set(docData)
//.addOnSuccessListener { Log.d(TAG, "Inmueble guardado correctamente!") }
//.addOnFailureListener { e -> Log.w(TAG, "Error al guardar inmueble", e) }